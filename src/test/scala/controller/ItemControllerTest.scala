package controller

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalamock.scalatest.MockFactory

import scala.collection.mutable.ArrayBuffer

class ItemControllerTest extends AnyWordSpec with Matchers with MockFactory {
  "An Item Controller" should {
    main.db.DbAdapter.dropAndReset
    val subject = new ItemController
    "have a fetchAll method" which{
      "returns all the items" in {
        subject.fetchAll shouldBe a[ArrayBuffer[_]]
      }
    }
    "has a create method" which {
      "adds an item to the end" in {
        val obj = subject.fetchAll()
        val id = obj.last.id + 1
        subject.create("Egg", 0.5, 4, List("EU"))
        obj.last.id shouldBe id
        obj.last.name shouldBe "Egg"
        obj.last.price shouldBe 0.5
        obj.last.quantity shouldBe 4
        obj.last.availableLocales shouldEqual List("EU")
      }
    }
  }
}
