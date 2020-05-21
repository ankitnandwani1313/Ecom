package com.anandwani.ecom
import com.anandwani.ecom.config._
import com.anandwani.ecom.process._
import com.anandwani.ecom.util._
import com.anandwani.ecom.model._


object Main {


  val MODULE_NAME = "MAIN"

  def main(args: Array[String]) = {

    var records: List[String] = null
    Logger.logInfo("MODULE_NAME","Starting Execution of Ecom Application")
    val configValidator = new ConfigValidator


    if(args(0) != null && !args(0).isEmpty){
      var config: Config = null

      config = ConfigReader.readConfigurationFile(args(0) + "/" + Constant.CONFIG_FILE)
      configValidator.validateConfig(config)


      if(config != null) {

        val mysqlPull = new MysqlPull(config.mysqlDetails)

         records = mysqlPull.FetchRecordsFromTable()

        if(records.size > 0 ) {

          val kafkaPushProducer = new KafkaPushProducer(config.kafkaDetails)
          kafkaPushProducer.pushRecordsToKafkaTopic(records)

          val kafkaSparkExecution = new KafkaSparkExecution(config)

          kafkaSparkExecution.sparkExecution()

        }
      }
    }
  }
}
