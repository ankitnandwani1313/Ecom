package com.anandwani.ecom.util

class Logger(name: String) extends org.apache.log4j.Logger(name) {

}

object Logger {

  val logger = org.apache.log4j.Logger.getRootLogger
  val MESSAGE_PREFIX = "[ "
  val MESSAGE_CONCATENATOR = " ] : "

  def logError(moduleName: String, message: String) = {

    logger.error( MESSAGE_PREFIX + moduleName + MESSAGE_CONCATENATOR +  message )
  }

  def logInfo(moduleName: String, message: String) = {

    logger.info( MESSAGE_PREFIX + moduleName + MESSAGE_CONCATENATOR +  message )
  }

  def logWarn(moduleName: String, message: String) = {

    logger.warn( MESSAGE_PREFIX + moduleName + MESSAGE_CONCATENATOR +  message )
  }

  def logDebug(moduleName: String, message: String) = {

    logger.debug( MESSAGE_PREFIX + moduleName + MESSAGE_CONCATENATOR +  message )
  }

}
