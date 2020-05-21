package com.anandwani.ecom.model

case class Connections(connections: Array[Connection]) {

  override def toString: String = {

    "Connections[connections = " + connections.mkString +"]"
  }



}
