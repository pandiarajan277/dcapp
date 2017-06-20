package services.db

import models.Tables
import slick.driver.PostgresDriver.api._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object DetailsFromDB {

  def getUser(emailId: String) = {

    val userQuery = for {
      query <- userTable.filter(x => (x.email === emailId))
    } yield query

    db.run(userQuery.result)
  }

  def getUser(emailId: String, password: String) = {

    val userQuery = for {
      query <- userTable.filter(x => (x.email === emailId) && (x.password === password))
    } yield query

    db.run(userQuery.result)
  }

  def getTopRecord[T <: AnyRef](filterFuture: Future[Seq[T]]): Future[Option[T]] = {
    filterFuture.map {
      case head +: tails =>
        Some(head)
      case Nil =>
        None
    }
  }

  def getAllLabTest() = {
    val testQuery = for {
      query <- testTable
    } yield query

    db.run(testQuery.result)
  }

  def getTestDetails(id: Int) = {
    val testQuery = for {
      query <- testTable.filter(x => x.id === id)
    } yield query

    getTopRecord(db.run(testQuery.result))
  }

  def getLabsFromLoc(city: String, zipCode: String) = {
    val testQuery = for {
      query <- labsTable.filter(x => x.city === city && (x.zipcode === zipCode))
    } yield query

    db.run(testQuery.result)
  }

  def getLab(id: Int) = {
    val testQuery = for {
      query <- labsTable.filter(x => x.id === id)
    } yield query

    db.run(testQuery.result)

  }

}