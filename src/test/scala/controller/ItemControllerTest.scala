package controller

import main.db.{DbAdapter, DbAdapterBase}
import main.model.Item
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalamock.scalatest.MockFactory

import scala.collection.mutable.ArrayBuffer

class ItemControllerTest extends AnyWordSpec with Matchers with MockFactory {
  "An Item Controller" should {
    DbAdapter.dropAndReset
//    val mockDB = mock[DbAdapterBase]
//    val subject = new ItemController(mockDB)
    "fetchAll" which {
      "returns all the items" in {
        val mockDB = mock[DbAdapterBase]
        val subject = new ItemController(mockDB)
        (mockDB.getItems _).expects().returning(ArrayBuffer())
        subject.fetchAll shouldBe a[ArrayBuffer[_]]
      }
    }
    "create" which  {
      "adds an item to the database" in  {
        pending
        //val mockItem = mock[Item]
        val mockDB = mock[DbAdapterBase]
        val subject = new ItemController(mockDB)
        val item = new Item(5, "Egg", 0.2, 6, List("UK"))
        (mockDB.createItem _).expects(item).returns(*)
        subject.create("test", 0.5, 4, List("EU")) shouldEqual ""
        //still writes to json(?)
      }
    }
    "getItemById" which {
      "finds an Item" in {
        val mockDB = mock[DbAdapterBase]
        val subject = new ItemController(mockDB)
        val item = new Item(5, "Egg", 0.2, 6, List("UK"))
        val mockFetchAll = ArrayBuffer(item)
        (mockDB.getItems _).expects().returns(mockFetchAll)
        subject.getItemById(5) shouldEqual(item)
      }
    }
  }
}
