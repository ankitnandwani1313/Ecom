package com.anandwani.ecom.process

import com.anandwani.ecom.model._
import com.anandwani.ecom.util._
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.errors.InterruptException
import org.apache.kafka.common.errors.AuthenticationException
import java.util.Properties



class KafkaPushProducer(kafkaDetails: KafkaDetails) {

  val MODULE_NAME = "KAFKA_PUSH"

  val producer: KafkaProducer[String, String] = initializeProducer

  def pushRecordsToKafkaTopic(records: List[String]) = {

    try {
      var recordsCount = 0
      Logger.logInfo(MODULE_NAME,"Sending Records to : " + kafkaDetails.topicName)
      for (record <- records) {

        pushToTopic(record)
        recordsCount = recordsCount + 1

      }
      Logger.logInfo(MODULE_NAME, "Total Number or Records Sends to Kafka Topic : " + recordsCount)
    }
    catch {
      case exception: Exception => {
        Logger.logError(MODULE_NAME,"Some exception occurred " + exception )
      }
    }
  }

  def initializeProducer(): KafkaProducer[String,String] = {

   Logger.logInfo(MODULE_NAME,"Initializing producer with clientID : " + Constant.PRODUCER_NAME)

    val props = new Properties()
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,kafkaDetails.serverDetail)
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,Constant.STRING_SERIALIZER)
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,Constant.STRING_SERIALIZER)
    props.put(ProducerConfig.CLIENT_ID_CONFIG,Constant.PRODUCER_NAME)

    new KafkaProducer[String,String](props)

  }

  def pushToTopic(record: String): Unit= {
    try {
      val kafkaRecord  = new ProducerRecord[String, String](kafkaDetails.topicName, record)
      producer.send(kafkaRecord)
    }

    catch {

      case interruptException: InterruptException => {

        Logger.logError(MODULE_NAME,"InterruptException occurred" + interruptException)
      }
      case authenticationException: AuthenticationException => {

        Logger.logError(MODULE_NAME,"AuthenticationException occurred" + authenticationException)
      }
      case exception: Exception => {

        Logger.logError(MODULE_NAME,"Some Exception occurred while sending records to Topic" + exception)
      }

    }
  }


}
