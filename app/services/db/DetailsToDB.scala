package services.db

import models.Tables.{TesttakenRow, UsersRow}
import java.util.Calendar

import play.api.libs.json._
import slick.dbio
import play.api.db.slick.DatabaseConfigProvider
import slick.backend.DatabaseConfig
import slick.dbio.Effect
import slick.lifted.Query
import slick.dbio
import slick.driver.PostgresDriver.api._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.impl.Future

object DetailsToDB {
  def createUser(userRow: UsersRow) = {
    val insert = dbio.DBIO.seq(userTable += userRow)
    dbConfig.db.run(insert)
  }




  def insertTest(testTakenRow: TesttakenRow) = {
    val insert = dbio.DBIO.seq(testTakenTable += testTakenRow)
    dbConfig.db.run(insert)
  }

}