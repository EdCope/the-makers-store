package model
import Factory.UUIDFactoryBase
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import org.scalamock.scalatest.MockFactory

class CartTest extends AnyWordSpec with Matchers with MockFactory {
  "Cart Model" should{
     "Cart should have a uuid" in  {
       val mockUUIDFactory = mock[UUIDFactoryBase]
       (mockUUIDFactory.create _).expects().returning("123e4567-e89b-12d3-a456-426655440000")
      val cart = new Cart(mockUUIDFactory)
      cart.uuid shouldEqual "123e4567-e89b-12d3-a456-426655440000"
    }
  }
}
