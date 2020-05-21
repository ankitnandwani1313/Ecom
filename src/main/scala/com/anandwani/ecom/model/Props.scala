package com.anandwani.ecom.model

case class Props(propKey: String,propValue: String ) {

  override def toString: String = {

    "Props[propKey =  "+ propKey +",propValue= " + propValue +"]"
  }

}
