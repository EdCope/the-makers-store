package model
import controller.ItemController
import factory.UUIDFactoryBase
import main.db.DbAdapterBase
import main.model.{Item, Location}
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import org.scalamock.scalatest.MockFactory

import scala.collection.mutable.ArrayBuffer

class CartTest extends AnyWordSpec with Matchers with MockFactory {
  "Cart Model" should {
    val cartLocation = new Location(10, "Test Town")
    val mockDb = mock[DbAdapterBase]
    val mockUUIDFactory = mock[UUIDFactoryBase]
    val mockItemController = mock[ItemController]
    "CartAttributes are" which {
      "have a uuid" in  {
        (mockUUIDFactory.create _).expects().returning("123e4567-e89b-12d3-a456-426655440000")
        val cart = new Cart(cartLocation, mockItemController, mockDb, mockUUIDFactory)
        cart.uuid shouldEqual "123e4567-e89b-12d3-a456-426655440000"
      }
      "has a collection of items" in {
        val cart = new Cart(cartLocation)
        cart.contents shouldBe a [ArrayBuffer[_]]
      }
      "a cart should be at a location" in {
        val cart = new Cart(cartLocation)
        cart.location shouldBe cartLocation
      }
    }
    "add Item" which {
      val sampleItem = new Item(5, "Egg", 0.2, 6, List("EU"))
      "can add an Item" in {
        (mockItemController.getItemsByLocation _).expects(cartLocation.name).returning(Array(sampleItem))
        val cart = new Cart(cartLocation, mockItemController)
        cart.addItem("Egg", 1)
        cart.contents shouldEqual ArrayBuffer(sampleItem)
      }
      "cannot add if not at location" in {
        (mockItemController.getItemsByLocation _).expects(cartLocation.name).returning(Array())
        val cart = new Cart(cartLocation, mockItemController)
        val error = the[Exception] thrownBy {cart.addItem("Ham", 1)}
        error.getMessage shouldEqual("Item not at location")
      }
      "there is not enough stock at the location" in {
        (mockItemController.getItemsByLocation _).expects(cartLocation.name).returning(Array(sampleItem))
        val cart = new Cart(cartLocation, mockItemController)
        val error = the[Exception] thrownBy {cart.addItem("Egg", 7)}
        error.getMessage shouldEqual("Not enough in stock")
      }
    }
    "Clear Cart contents" which {
      val sampleItem = new Item(5, "Egg", 0.2, 6, List("EU"))
      val sampleItem2 = new Item(3, "Ham", 0.5, 8, List("EU"))
      "fully clear" in {
        val cart = new Cart(cartLocation, contents = ArrayBuffer(sampleItem, sampleItem2))
        cart.clearCart()
        cart.contents.length shouldEqual 0
      }
      "clear by id" in {
        val cart = new Cart(cartLocation, contents = ArrayBuffer(sampleItem, sampleItem2))
        cart.removeItemFromCartById(5)
        cart.contents shouldEqual ArrayBuffer(sampleItem2)
      }
    }

  }
}
