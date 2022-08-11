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
    val sampleItem = new Item(5, "Egg", 0.2, 6, List("UK"))
    val sampleItem2 = new Item( 0, "Cheese", price=1.2, quantity = 8, List("US"))
    "fetchAll" which {
      "returns all the items" in {
        val mockDB = mock[DbAdapterBase]
        val subject = new ItemController(mockDB)
        (mockDB.getItems _)
          .expects()
          .returning(ArrayBuffer(sampleItem, sampleItem2))
        subject.fetchAll shouldEqual ArrayBuffer(sampleItem, sampleItem2)
      }
    }
    "create" which {
      "adds an item to the database" in  {
        val mockDB = mock[DbAdapterBase]
        val mockItemFactory = mock[ItemFactoryBase]
        val subject = new ItemController(mockDB, mockItemFactory)
        (mockDB.getItems _)
          .expects()
          .returns(ArrayBuffer())
        (mockItemFactory.create _)
          .expects(0, "Cheese", 1.2, 8, List("UK"))
          .returning(sampleItem2)
        (mockDB.createItem _)
          .expects(sampleItem2)
          .returns()
        subject.create("Cheese", 1.2, 8, List("UK"))

      }
    }
    "getItemById" which {
      "finds an Item" in {
        val mockDB = mock[DbAdapterBase]
        val subject = new ItemController(mockDB)
        val mockFetchAll = ArrayBuffer(sampleItem)
        (mockDB.getItems _).expects().returns(mockFetchAll)
        subject.getItemById(5) shouldEqual sampleItem
      }
    }
    "updateItemById" which {
      "changes following database fields" which {
        val mockDB = mock[DbAdapterBase]
        val mockItemFactory = mock[ItemFactoryBase]
        val subject = new ItemController(mockDB, mockItemFactory)
        val mockFetchAll = ArrayBuffer(sampleItem)
        (mockDB.getItems _)
          .expects()
          .returns(mockFetchAll)

        val updatedItem = new Item(5, "Boiled Egg", 0.5, 3, List("US"))
        (mockItemFactory.create _)
          .expects(5, "Boiled Egg", 0.5, 3, List("US"))
          .returning(updatedItem)
        (mockDB.updateItem _).expects(5, updatedItem).returns()

        val result = subject.updateItemById(5)(
          name = Option("Boiled Egg"),
          price = Option(0.5),
          quantity = Option(3),
          availableLocales = Option(List("US"))
        )
        "name" in {result.name shouldEqual "Boiled Egg"}
        "price" in {result.price shouldEqual 0.5}
        "quantity" in {result.quantity shouldEqual 3}
        "availableLocales" in {result.availableLocales shouldEqual List("US")}
      }
    }
  }
}
