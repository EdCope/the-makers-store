package controller

import main.db.DbAdapterBase
import main.model.Item
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
      "adds an item to the database" in {
        val mockDB = mock[DbAdapterBase]
        val mockItem = mock[Item]
        subject.create("test", 0.5, 4, List("EU"))
        (mockDB.createItem _).expects(mockItem).anyNumberOfTimes()
        //still writes to json(?)
      }
    }
  }
}
