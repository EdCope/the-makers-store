package controller

import Factory.ItemFactoryBase
import main.db.{DbAdapter, DbAdapterBase}
import main.model.Item
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalamock.scalatest.MockFactory

import scala.collection.mutable.ArrayBuffer

class ItemControllerTest extends AnyWordSpec with Matchers with MockFactory {
  "An Item Controller" should {
    DbAdapter.dropAndReset
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
        val mockDB = mock[DbAdapterBase]
        val item = new Item(0, "Egg", 0.2, 6, List("UK"))
        val mockItemFactory = mock[ItemFactoryBase]
        val subject = new ItemController(mockDB, mockItemFactory)
        (mockDB.getItems _).expects().returns(ArrayBuffer())
        (mockItemFactory.create _).expects(0, "Egg", 0.2, 6, List("UK")).returning(item)
        (mockDB.createItem _).expects(item).returns()
        subject.create("Egg", 0.2, 6, List("UK")) shouldEqual item

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
    "updateItemById" which {
      "changes following database fields" which {
        val mockDB = mock[DbAdapterBase]
        val mockItemFactory = mock[ItemFactoryBase]
        val subject = new ItemController(mockDB, mockItemFactory)
        val item = new Item(1, "Egg", 0.2, 6, List("UK"))
        val mockFetchAll = ArrayBuffer(item)
        (mockDB.getItems _).expects().returns(mockFetchAll)

        val updatedItem = new Item(1, "Boiled Egg", 0.5, 3, List("US"))
        (mockItemFactory.create _).expects(1, "Boiled Egg", 0.5, 3, List("US")).returning(updatedItem)
        (mockDB.updateItem _).expects(1, updatedItem).returns()

        val result = subject.updateItemById(1)(
          name = Option("Boiled Egg"),
          price = Option(0.5),
          quantity = Option(3),
          availableLocales = Option(List("US"))
        )
        "name" in {result.name shouldEqual("Boiled Egg")}
        "price" in {result.price shouldEqual(0.5)}
        "quantity" in {result.quantity shouldEqual(3)}
        "availableLocales" in {result.availableLocales shouldEqual(List("US"))}

      }
    }
  }
}
