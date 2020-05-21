package com.anandwani.ecom.model

case class Connection(name: String,properties: Array[Props]) {

  override def toString: String = {

    "Connection[name = " + name + ",properties = " + properties.mkString +"]"
  }

}
