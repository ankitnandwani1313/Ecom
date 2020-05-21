package com.anandwani.ecom.process

import com.anandwani.ecom.model._
import com.anandwani.ecom.util._
import java.sql.DriverManager
import java.sql.Connection
import java.sql.ResultSet
import java.sql.SQLDataException
import java.sql.SQLException

class MysqlPull(mysqlDetails: MysqlDetails) {

  val MODULE_NAME = "MYSQL_PULL"

  def FetchRecordsFromTable(): List[String] = {


      Logger.logInfo(MODULE_NAME, "Setting up connection with Mysql server  : " + mysqlDetails.url )
      Class.forName(mysqlDetails.driver)

      var sqlConnection: Connection = null
      var orderRecords: List[String] = List()

    try{
      sqlConnection = DriverManager.getConnection(mysqlDetails.url, mysqlDetails.user, mysqlDetails.password)


      val statement = "select o.order_id,o.order_date,o.order_status,oi.order_item_id,oi.order_item_quantity," +
        "oi.order_item_subtotal,order_item_product_price from orders o join order_items oi on o.order_id = oi.order_item_order_id " +
        "where o.updateTime < timestamp(now())  order by o.order_id asc "

      val preparedStatement = sqlConnection.prepareStatement(statement)

      val res: ResultSet = preparedStatement.executeQuery()

      while (res.next()) {
        val message: String = res.getInt(1) + "," + res.getString(2) + "," + res.getString(3) + "," + res.getInt(4) + "," +
        res.getInt(5) + "," + res.getFloat(6) +  "," + res.getFloat(7)
        orderRecords = message :: orderRecords

      }
    }

    catch {

      case sqlDataException: SQLDataException => {
        Logger.logError(MODULE_NAME, "sqlDataException" + sqlDataException)
      }
      case sqlException: SQLException => {
        Logger.logError(MODULE_NAME,"sqlDataException" + sqlException)
      }
      case exception: Exception =>{
        Logger.logError(MODULE_NAME,"some error occurred" + exception)
      }
    }

    orderRecords

  }

}
