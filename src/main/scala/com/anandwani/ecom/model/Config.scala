package com.anandwani.ecom.model

case class Config(`type`: String,outputPath: String,checkPointPath: String,kafkaDetails: KafkaDetails,mysqlDetails: MysqlDetails,source: String) {

}

case class KafkaDetails(topicName: String, interval: Int, serverDetail: String){

}


case class MysqlDetails(url: String,user:String,password: String,driver: String){

}