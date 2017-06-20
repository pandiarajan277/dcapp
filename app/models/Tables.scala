package models
// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends {
  val profile = slick.driver.PostgresDriver
} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: slick.driver.JdbcProfile
  import profile.api._
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema: profile.SchemaDescription = Labs.schema ++ Test.schema ++ Testtaken.schema ++ Users.schema
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table Labs
   *  @param labname Database column labname SqlType(varchar), Default(None)
   *  @param address Database column address SqlType(varchar), Default(None)
   *  @param city Database column city SqlType(varchar), Default(None)
   *  @param zipcode Database column zipcode SqlType(varchar), Default(None)
   *  @param id Database column id SqlType(int4), Default(None) */
  case class LabsRow(labname: Option[String] = None, address: Option[String] = None, city: Option[String] = None, zipcode: Option[String] = None, id: Option[Int] = None)
  /** GetResult implicit for fetching LabsRow objects using plain SQL queries */
  implicit def GetResultLabsRow(implicit e0: GR[Option[String]], e1: GR[Option[Int]]): GR[LabsRow] = GR{
    prs => import prs._
    LabsRow.tupled((<<?[String], <<?[String], <<?[String], <<?[String], <<?[Int]))
  }
  /** Table description of table labs. Objects of this class serve as prototypes for rows in queries. */
  class Labs(_tableTag: Tag) extends Table[LabsRow](_tableTag, "labs") {
    def * = (labname, address, city, zipcode, id) <> (LabsRow.tupled, LabsRow.unapply)

    /** Database column labname SqlType(varchar), Default(None) */
    val labname: Rep[Option[String]] = column[Option[String]]("labname", O.Default(None))
    /** Database column address SqlType(varchar), Default(None) */
    val address: Rep[Option[String]] = column[Option[String]]("address", O.Default(None))
    /** Database column city SqlType(varchar), Default(None) */
    val city: Rep[Option[String]] = column[Option[String]]("city", O.Default(None))
    /** Database column zipcode SqlType(varchar), Default(None) */
    val zipcode: Rep[Option[String]] = column[Option[String]]("zipcode", O.Default(None))
    /** Database column id SqlType(int4), Default(None) */
    val id: Rep[Option[Int]] = column[Option[Int]]("id", O.Default(None))
  }
  /** Collection-like TableQuery object for table Labs */
  lazy val Labs = new TableQuery(tag => new Labs(tag))

  /** Entity class storing rows of table Test
   *  @param test Database column test SqlType(varchar)
   *  @param cost Database column cost SqlType(int4), Default(None)
   *  @param id Database column id SqlType(int4) */
  case class TestRow(test: String, cost: Option[Int] = None, id: Int)
  /** GetResult implicit for fetching TestRow objects using plain SQL queries */
  implicit def GetResultTestRow(implicit e0: GR[String], e1: GR[Option[Int]], e2: GR[Int]): GR[TestRow] = GR{
    prs => import prs._
    TestRow.tupled((<<[String], <<?[Int], <<[Int]))
  }
  /** Table description of table test. Objects of this class serve as prototypes for rows in queries. */
  class Test(_tableTag: Tag) extends Table[TestRow](_tableTag, "test") {
    def * = (test, cost, id) <> (TestRow.tupled, TestRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(test), cost, Rep.Some(id)).shaped.<>({r=>import r._; _1.map(_=> TestRow.tupled((_1.get, _2, _3.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column test SqlType(varchar) */
    val test: Rep[String] = column[String]("test")
    /** Database column cost SqlType(int4), Default(None) */
    val cost: Rep[Option[Int]] = column[Option[Int]]("cost", O.Default(None))
    /** Database column id SqlType(int4) */
    val id: Rep[Int] = column[Int]("id")
  }
  /** Collection-like TableQuery object for table Test */
  lazy val Test = new TableQuery(tag => new Test(tag))

  /** Entity class storing rows of table Testtaken
   *  @param email Database column email SqlType(varchar), Default(None)
   *  @param testid Database column testid SqlType(int4)
   *  @param takendate Database column takendate SqlType(date)
   *  @param labid Database column labid SqlType(int4), Default(None)
   *  @param useraddress Database column useraddress SqlType(varchar) */
  case class TesttakenRow(email: Option[String] = None, testid: Int, takendate: java.sql.Date, labid: Option[Int] = None, useraddress: String)
  /** GetResult implicit for fetching TesttakenRow objects using plain SQL queries */
  implicit def GetResultTesttakenRow(implicit e0: GR[Option[String]], e1: GR[Int], e2: GR[java.sql.Date], e3: GR[Option[Int]], e4: GR[String]): GR[TesttakenRow] = GR{
    prs => import prs._
    TesttakenRow.tupled((<<?[String], <<[Int], <<[java.sql.Date], <<?[Int], <<[String]))
  }
  /** Table description of table testtaken. Objects of this class serve as prototypes for rows in queries. */
  class Testtaken(_tableTag: Tag) extends Table[TesttakenRow](_tableTag, "testtaken") {
    def * = (email, testid, takendate, labid, useraddress) <> (TesttakenRow.tupled, TesttakenRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (email, Rep.Some(testid), Rep.Some(takendate), labid, Rep.Some(useraddress)).shaped.<>({r=>import r._; _2.map(_=> TesttakenRow.tupled((_1, _2.get, _3.get, _4, _5.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column email SqlType(varchar), Default(None) */
    val email: Rep[Option[String]] = column[Option[String]]("email", O.Default(None))
    /** Database column testid SqlType(int4) */
    val testid: Rep[Int] = column[Int]("testid")
    /** Database column takendate SqlType(date) */
    val takendate: Rep[java.sql.Date] = column[java.sql.Date]("takendate")
    /** Database column labid SqlType(int4), Default(None) */
    val labid: Rep[Option[Int]] = column[Option[Int]]("labid", O.Default(None))
    /** Database column useraddress SqlType(varchar) */
    val useraddress: Rep[String] = column[String]("useraddress")
  }
  /** Collection-like TableQuery object for table Testtaken */
  lazy val Testtaken = new TableQuery(tag => new Testtaken(tag))

  /** Entity class storing rows of table Users
   *  @param firstname Database column firstname SqlType(varchar)
   *  @param lastname Database column lastname SqlType(varchar), Default(None)
   *  @param dob Database column DOB SqlType(date)
   *  @param email Database column email SqlType(varchar)
   *  @param password Database column password SqlType(varchar)
   *  @param createddate Database column createddate SqlType(date) */
  case class UsersRow(firstname: String, lastname: Option[String] = None, dob: java.sql.Date, email: String, password: String, createddate: java.sql.Date)
  /** GetResult implicit for fetching UsersRow objects using plain SQL queries */
  implicit def GetResultUsersRow(implicit e0: GR[String], e1: GR[Option[String]], e2: GR[java.sql.Date]): GR[UsersRow] = GR{
    prs => import prs._
    UsersRow.tupled((<<[String], <<?[String], <<[java.sql.Date], <<[String], <<[String], <<[java.sql.Date]))
  }
  /** Table description of table users. Objects of this class serve as prototypes for rows in queries. */
  class Users(_tableTag: Tag) extends Table[UsersRow](_tableTag, "users") {
    def * = (firstname, lastname, dob, email, password, createddate) <> (UsersRow.tupled, UsersRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(firstname), lastname, Rep.Some(dob), Rep.Some(email), Rep.Some(password), Rep.Some(createddate)).shaped.<>({r=>import r._; _1.map(_=> UsersRow.tupled((_1.get, _2, _3.get, _4.get, _5.get, _6.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column firstname SqlType(varchar) */
    val firstname: Rep[String] = column[String]("firstname")
    /** Database column lastname SqlType(varchar), Default(None) */
    val lastname: Rep[Option[String]] = column[Option[String]]("lastname", O.Default(None))
    /** Database column DOB SqlType(date) */
    val dob: Rep[java.sql.Date] = column[java.sql.Date]("DOB")
    /** Database column email SqlType(varchar) */
    val email: Rep[String] = column[String]("email")
    /** Database column password SqlType(varchar) */
    val password: Rep[String] = column[String]("password")
    /** Database column createddate SqlType(date) */
    val createddate: Rep[java.sql.Date] = column[java.sql.Date]("createddate")
  }
  /** Collection-like TableQuery object for table Users */
  lazy val Users = new TableQuery(tag => new Users(tag))
}
