package helper
import main.model.Location
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import org.scalamock.scalatest.MockFactory

import scala.collection.mutable.LinkedHashMap

class LocationHelperTest extends AnyWordSpec with Matchers with MockFactory {
  "A Location Helper" should {
    val mockLocation1 = new Location(0, "Testville")
    val mockLocation2 = new Location(1, "Test Town")
    val mockLocation3 = new Location(2, "Testington")
    val mockLocation4 = new Location(3, "Der Test")
    val mockLocation5 = new Location(4, "Das Test")

    val mockFlatLocation1 = FlatLocation(0, "Testville", "NA", "US")
    val mockFlatLocation2 = FlatLocation(1, "Test Town", "NA", "CA")
    val mockFlatLocation3 = FlatLocation(2, "Testington", "EU", "UK")
    val mockFlatLocation4 = FlatLocation(3, "Der Test", "EU", "DE")
    val mockFlatLocation5 = FlatLocation(4, "Das Test", "EU", "DE")

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
    "locationFlattener" which {
      "flatten a hashmap of a locations into a list of FlatLocation" in {
        LocationHelper.flattenLocations(mockLocations) should contain allOf(
          mockFlatLocation1,
          mockFlatLocation2,
          mockFlatLocation3,
          mockFlatLocation4,
          mockFlatLocation5
        )
      }
    }
    "getContinentFromLocation" which {
      "returns the continent of a given location" in {
        LocationHelper.getContinentFromLocation(
          "Testington",
          mockLocations
        ) shouldEqual "EU"
      }
    }
  }
}
