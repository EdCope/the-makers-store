package model
import factory.UUIDFactoryBase
import main.model.{Item, Location}
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import org.scalamock.scalatest.MockFactory

import scala.collection.mutable.ArrayBuffer

class CartTest extends AnyWordSpec with Matchers with MockFactory {
  "Cart Model" should {
    val cartLocation = new Location(10, "Test Town")
    "CartAttributes are" which {
      "have a uuid" in  {
        val mockUUIDFactory = mock[UUIDFactoryBase]
        (mockUUIDFactory.create _).expects().returning("123e4567-e89b-12d3-a456-426655440000")
        val cart = new Cart(cartLocation, mockUUIDFactory)
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

    "can add an Item" in {
      val cart = new Cart(cartLocation)
      val sampleItem = new Item(5, "Egg", 0.2, 6, List("EU"))
      cart.addItem(sampleItem)
      cart.contents shouldEqual ArrayBuffer(sampleItem)
    }

  }
}
