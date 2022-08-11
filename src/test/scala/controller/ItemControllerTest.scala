package controller

import Factory.ItemFactoryBase
import main.db.{DbAdapter, DbAdapterBase}
import main.model.{Item, Location}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalamock.scalatest.MockFactory

import scala.collection.mutable.{ArrayBuffer, LinkedHashMap}

class ItemControllerTest extends AnyWordSpec with Matchers with MockFactory {
  "An Item Controller" should {
    DbAdapter.dropAndReset
    val sampleItem = new Item(5, "Egg", 0.2, 6, List("EU"))
    val sampleItem2 = new Item( 0, "Cheese", price=1.2, quantity = 8, List("NA"))
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
          .expects(0, "Cheese", 1.2, 8, List("NA"))
          .returning(sampleItem2)
        (mockDB.createItem _)
          .expects(sampleItem2)
          .returns()
        subject.create("Cheese", 1.2, 8, List("NA"))

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
    "getItemByLocation" which {
      "finds a list of all available items at a location" in {
        val mockDB = mock[DbAdapterBase]
        val mockFetchAll = ArrayBuffer(sampleItem, sampleItem2)
        val mockLocation1 = new Location(0, "Testville")
        val mockLocation2 = new Location(1, "Test Town")
        val mockLocation3 = new Location(2, "Testington")
        val mockLocation4 = new Location(3, "Der Test")
        val mockLocation5 = new Location(4, "Das Test")
        val mockLocations = LinkedHashMap(
          "NA" -> LinkedHashMap(
            "US" -> Seq(mockLocation1),
            "CA" -> Seq(mockLocation2)
          ),
          "EU" -> LinkedHashMap(
            "UK" -> Seq(mockLocation3),
            "DE" -> Seq(mockLocation4, mockLocation5)
          )
        )
        (mockDB.getItems _).expects().returns(mockFetchAll).anyNumberOfTimes()
        (mockDB.getLocations _).expects().returns(mockLocations).anyNumberOfTimes()
        val subject = new ItemController(mockDB)
        subject.getItemsByLocation("Testington") shouldEqual Array(sampleItem)
        subject.getItemsByLocation("Testville") shouldEqual Array(sampleItem2)
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

        val updatedItem = new Item(5, "Boiled Egg", 0.5, 3, List("NA"))
        (mockItemFactory.create _)
          .expects(5, "Boiled Egg", 0.5, 3, List("NA"))
          .returning(updatedItem)
        (mockDB.updateItem _).expects(5, updatedItem).returns()

        val result = subject.updateItemById(5)(
          name = Option("Boiled Egg"),
          price = Option(0.5),
          quantity = Option(3),
          availableLocales = Option(List("NA"))
        )
        "name" in {result.name shouldEqual "Boiled Egg"}
        "price" in {result.price shouldEqual 0.5}
        "quantity" in {result.quantity shouldEqual 3}
        "availableLocales" in {result.availableLocales shouldEqual List("NA")}
      }
    }
  }
}
