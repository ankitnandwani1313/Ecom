package com.anandwani.ecom.config
import com.anandwani.ecom.model._
import java.io.File

class ConfigValidatorInitials {

   def isNotEmpty(value:String): Boolean = {

    var isValid = false

    if(value != null && value.trim.length > 0){

      isValid = true
    }

    isValid
  }

   def isNotEmpty(connections: Array[Connection]): Boolean = {

    var isValid = false

    if(connections != null && connections.size > 0){

      isValid = true
    }
    isValid
  }

   def isDir(path: String): Boolean = {
    var isvalid = false
    val file = new File(path)

    if(file != null && file.exists() && file.isDirectory){

      isvalid = true
    }

    isvalid
  }

}
