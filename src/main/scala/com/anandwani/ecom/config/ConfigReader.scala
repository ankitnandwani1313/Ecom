package com.anandwani.ecom.config
import com.google.gson.Gson
import com.anandwani.ecom.util._
import com.anandwani.ecom.model._
import com.google.gson.JsonParseException
import java.io.FileNotFoundException
import java.nio.file.AccessDeniedException

object ConfigReader {

  val ModuleName = "CONFIG_READER"

  def readConnectionFile(path: String): Connections= {

    var connections: Connections = null
    val gson = new Gson()

    try {
      connections = gson.fromJson(scala.io.Source.fromFile(path).getLines().mkString, classOf[Connections])
    }

    catch {
      case jsonParseException: JsonParseException => {
        Logger.logError(ModuleName, "JsonParseException" + jsonParseException)
      }
      case fileNotFoundException:FileNotFoundException => {
        Logger.logError(ModuleName, "FileNotFoundException" + fileNotFoundException)
      }
      case accessDeniedException: AccessDeniedException => {
        Logger.logError(ModuleName, "AccessDeniedException" + accessDeniedException)
      }

      case exception: Exception => {
        Logger.logError(ModuleName, "Exception" + exception)
      }
    }
    connections
  }


  def readConfigurationFile(path:String): Config = {
    var config: Config = null
    val gson = new Gson()

    try {
      config = gson.fromJson(scala.io.Source.fromFile(path).getLines().mkString, classOf[Config])
    }

    catch {
      case jsonParseException: JsonParseException => {
        Logger.logError(ModuleName, "JsonParseException" + jsonParseException)
      }
      case fileNotFoundException:FileNotFoundException => {
        Logger.logError(ModuleName, "FileNotFoundException" + fileNotFoundException)
      }
      case accessDeniedException: AccessDeniedException => {
        Logger.logError(ModuleName, "AccessDeniedException" + accessDeniedException)
      }

      case exception: Exception => {
        Logger.logError(ModuleName, "Exception" + exception)
      }
    }
    config
  }



}
