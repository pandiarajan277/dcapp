package services

import play.api.Play
import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile

/**
  * Created by sweethome on 26-Feb-17.
  */
package object db {

  val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)
  val db = dbConfig.db
  db.createSession().conn.isValid(10000)

  val userTable = models.Tables.Users
  val labsTable = models.Tables.Labs
  val testTable = models.Tables.Test
  val testTakenTable = models.Tables.Testtaken
  
  

}


