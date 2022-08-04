package controller

import main.model.Item
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import scala.collection.mutable.ArrayBuffer

class ItemControllerTest extends AnyWordSpec with Matchers {
  "An Item Controller" should {
    "have a fetchAll method" which{
      "returns all the items" in {
        val item = new ItemController
        item.fetchAll shouldBe a[ArrayBuffer[Item]]
      }
    }
  }
}
