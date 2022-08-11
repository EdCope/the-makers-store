package factory
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import org.scalamock.scalatest.MockFactory

class UUIDFactoryTest extends AnyWordSpec with Matchers with MockFactory {
  "UUID Factory" should {
    "create" which {
      "supplies a uuid from a library" in {
        UUIDFactory.create() shouldBe a[String]
      }
    }
  }
}
