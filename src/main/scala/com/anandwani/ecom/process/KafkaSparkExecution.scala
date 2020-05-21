package com.anandwani.ecom.process

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.streaming.{OutputMode}
import com.anandwani.ecom.util._
import com.anandwani.ecom.model._


class KafkaSparkExecution(config: Config) {

  val MODULE = "KAFKA_SPARK_EXECUTION"
  val kafkaDetails = config.kafkaDetails
  val spark: SparkSession = initializeSparkSession()

  def sparkExecution(): Unit = {


    if(spark != null) {
      Logger.logInfo(MODULE, "Reading Ecom Records from kafka Topic : " + kafkaDetails.topicName)

      val kafkaLoad = spark.readStream.format("kafka")
        .option("kafka.bootstrap.servers", kafkaDetails.serverDetail)
        .option("subscribe", kafkaDetails.topicName).load()

      import spark.implicits._

      if(kafkaLoad != null) {

        val castBinary = kafkaLoad.selectExpr("CAST(value AS STRING)").as[(String)]

        val mappedDF = castBinary.map(o => (o.split(",")(0).toInt, o.split(",")(1), o.split(",")(2),
          o.split(",")(3).toInt, o.split(",")(4).toInt, o.split(",")(5).toFloat, o.split(",")(6).toFloat)).
          toDF("order_id", "order_date", "order_status", "order_item_id", "order_item_quantity", "order_item_subtotal", "order_item_product_price")


        val timestampAddedDF = mappedDF.withColumn("TimeStamp", current_timestamp())


        val aggregatedDF = timestampAddedDF.withWatermark("TimeStamp", "1 second").
          groupBy(window(col("TimeStamp"), "2 seconds", "1 second"),
            from_unixtime(unix_timestamp(col("order_date")), "yyyy-MM-dd").alias("order_formatted_date"), col("order_status"))
          .agg(round(sum("order_item_subtotal"), 2).alias("total_amount"),
            approx_count_distinct("order_id").alias("total_orders"))

        Logger.logInfo(MODULE,"Setting up the checkpointLocation : " + config.checkPointPath)

        spark.conf.set("spark.sql.streaming.checkpointLocation", config.checkPointPath)


          Logger.logInfo(MODULE, "Staring the Execution of File Formation in HDFS path : " + config.outputPath )
          aggregatedDF.writeStream.outputMode(OutputMode.Append).
          format(config.source).option("checkPointLocation", config.checkPointPath).
          option("path", config.outputPath).start().awaitTermination








      }
    }
  }

    def initializeSparkSession(): SparkSession = {

      Logger.logInfo(MODULE,"Initializing SparkSession")

      var sparkSession: SparkSession = null

      try{
        sparkSession = SparkSession.builder().appName(Constant.APP_NAME).master(Constant.MASTER).getOrCreate()
      } catch{
        case exception: Exception =>
          {
            Logger.logError(MODULE,"Exception occurred while initializing SparkSession: " + exception)
          }
      }
      sparkSession


  }



}
