package com.anandwani.ecom.config
import com.anandwani.ecom.model._
import com.anandwani.ecom.util._


class ConfigValidator extends ConfigValidatorInitials {


  val MODULE_NAME = "CONFIG_VALIDATOR"



  def validateConfig(config: Config): Boolean = {

    Logger.logInfo(MODULE_NAME,"validating config configuration")

    var isValid = false

    isValid = isNotEmpty(config.outputPath)
    if (!isValid) {
      Logger.logError(MODULE_NAME, "output path cannot be empty in configuration file")
      return isValid
    }

    isValid = isNotEmpty(config.checkPointPath)
    if (!isValid) {
      Logger.logError(MODULE_NAME, "checkPoint path cannot be empty in configuration file")
      return isValid
    }

    isValid = isNotEmpty(config.source)
    if (!isValid) {
      Logger.logError(MODULE_NAME, " Output source cannot be empty in configuration file")
      return isValid
    }

    /*isValid = isDir(config.outputPath)
    if (!isValid) {
      Logger.logError(MODULE_NAME, "outputPath doesn't not exists")
      return isValid
    }*/

    isValid = validateKafkaConfig(config.kafkaDetails)
    if (!isValid) {
      return isValid
    }
    isValid = validateMysqlConfig(config.mysqlDetails)
    if (!isValid) {
      return isValid
    }
    isValid

  }


  private def validateMysqlConfig(mysqlDetails: MysqlDetails): Boolean = {

    var isValid = false

    isValid = isNotEmpty(mysqlDetails.driver)
    if (!isValid) {
      Logger.logError(MODULE_NAME, "mysql driver class cannot be empty in configuration file")
      return isValid
    }
    isValid = isNotEmpty(mysqlDetails.password)
    if (!isValid) {
      Logger.logError(MODULE_NAME, "mysql password cannot be empty in configuration file")
      return isValid
    }
    isValid = isNotEmpty(mysqlDetails.url)
    if (!isValid) {
      Logger.logError(MODULE_NAME, "mysql url cannot be empty in configuration file")
      return isValid
    }
    isValid = isNotEmpty(mysqlDetails.user)
    if (!isValid) {
      Logger.logError(MODULE_NAME, "mysql user cannot be empty in configuration file")
      return isValid
    }

    isValid
  }

  private def validateKafkaConfig(kafkaDetails: KafkaDetails): Boolean = {

    var isValid = false

    isValid = isNotEmpty(kafkaDetails.topicName)
    if (!isValid) {
      Logger.logError(MODULE_NAME, "kafka TopicName cannot be empty in configuration file")
      return isValid
    }

    isValid = isNotEmpty(kafkaDetails.interval.toString)
    if (!isValid) {
      Logger.logError(MODULE_NAME, "kafka Interval cannot be empty in configuration file")
      return isValid
    }

    isValid = isNotEmpty(kafkaDetails.serverDetail)
    if (!isValid) {
      Logger.logError(MODULE_NAME, "kafka serverDetail cannot be empty in configuration file")
      return isValid
    }

    isValid

  }


}