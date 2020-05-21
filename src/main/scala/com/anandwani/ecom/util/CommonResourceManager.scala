package com.anandwani.ecom.util

import com.anandwani.ecom.model._
import org.apache.hadoop.fs.FileSystem
import org.apache.hadoop.conf.Configuration


object CommonResourceManager {

  val MODULE_NAME = "COMMONRESOURCE"

  var _connectionModel: Connections = null

  def connectionModel = _connectionModel

  def connectionModel_=(connectionModel: Connections): Unit = {

    if (_connectionModel == null) {

      _connectionModel = connectionModel
    }

  }

  var _configModel: Config = null

  def configModel = _configModel

  def configModel_=(configModel: Config): Unit = {

    if (_configModel == null) {

      _configModel = configModel
    }

  }


  def getConnection(connectionName: String): Connection = {

    var connection: Array[Connection] = null
    connection = connectionModel.connections.filter(con => con.name.equalsIgnoreCase(connectionName))

    if (connection.size > 0) {
      connection(0)
    }
    else
      null
  }

  def getFileSystem(inputType: String,connectionName: String):FileSystem = {

    inputType.toLowerCase match {

      case "localfs" => {
        getLocalFileSystem()
      }
      case "hdfs" => {

        getHdfsConnection(connectionName)
      }
    }
  }


  def getHdfsConnection(connectionName: String): FileSystem = {

    var filesystem:FileSystem = null
    val connection = getConnection(connectionName)

    if(connection != null) {

      val default_fs = connection.properties.filter(prop => prop.propKey.equalsIgnoreCase(Constant.HDFS_DEFAULT))
      if(default_fs.size > 0) {
        val conf = new Configuration()
        conf.set(Constant.HDFS_DEFAULT,default_fs(0).propValue.toString)
        Logger.logInfo(MODULE_NAME, "DEFAULT FS for HDFS Connection" + connectionName + "is" +  default_fs(0).propValue.toString )
        filesystem = FileSystem.get(conf)
      }
    }

    filesystem
  }

  def getLocalFileSystem(): FileSystem = {
    var filesystem: FileSystem = null
    val conf = new Configuration()

    filesystem = FileSystem.getLocal(conf).getRaw

    filesystem
  }


}

