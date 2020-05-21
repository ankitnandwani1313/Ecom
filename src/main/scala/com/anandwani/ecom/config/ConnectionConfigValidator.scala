package com.anandwani.ecom.config
import com.anandwani.ecom.model._
import com.anandwani.ecom.util._

class ConnectionConfigValidator extends ConfigValidatorInitials {

  val MODULE_NAME = "CONNECTION_CONFIG"



def validateConnectionConfig(connectionModel:Connections): Boolean ={
  var isValid = false
  Logger.logInfo(MODULE_NAME,"Validating connection configuration")

  if(connectionModel.connections != null) {
    isValid = isNotEmpty(connectionModel.connections)
    if (!isValid) {
      Logger.logError(MODULE_NAME, "Connection is not set up in connection configuration")
      return isValid
    }


    for (connection <- connectionModel.connections) {
      isValid = validateConnection(connection)
      if (!isValid) {
       Logger.logError(MODULE_NAME,"Connection parameters are not found in configuration")
        return isValid
      }
    }
  }

  else{
    isValid = false
    Logger.logError(MODULE_NAME,"Connections must be configured and cannot be empty in connections.json")
  }
  isValid
}







private def validateConnection(connection: Connection): Boolean = {

  var isValid = false

  isValid = isNotEmpty(connection.name)
  if(!isValid){
    Logger.logError(MODULE_NAME,"name cannot be empty in connections.json file")
    return  isValid
  }
  if(connection.properties != null) {
    for (props <- connection.properties) {
      isValid = validateConnectionKeyValue(props)
    }
  }
    else{
    isValid = false
    Logger.logError( MODULE_NAME,"Properties must be configured and cannot be empty in connections.json" )
    }

  isValid

}


  private def validateConnectionKeyValue(props: Props): Boolean = {
   var isValid = false

  isValid = isNotEmpty(props.propKey)
  if(!isValid){
    Logger.logError(MODULE_NAME,"propKey cannot be empty in connection.json file")
    return  isValid
  }
  isValid = isNotEmpty(props.propValue)
  if(!isValid){
    Logger.logError(MODULE_NAME,"propValue cannot be empty in connection.json file")
    return isValid
  }
isValid

}











}
