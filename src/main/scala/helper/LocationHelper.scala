package helper

import main.model.Location

import scala.collection.mutable.LinkedHashMap

case class FlatLocation(id: Int, name: String, continent: String, region: String)

object LocationHelper {

  def flattenLocations(locations: LinkedHashMap[String, LinkedHashMap[String, Seq[Location]]]): List[FlatLocation] = {
    locations.foldLeft(List[FlatLocation]()) { (outputList, hMapOfRegion) => {
        val (continent,regionMap) = hMapOfRegion
        outputList ++ regionMap.foldLeft(List[FlatLocation]()){(sublist, hMapOfLocation) => {
            val (region, locationSeq) = hMapOfLocation
            sublist ++ locationSeq.map(l => {
              FlatLocation(l.id, l.name, continent, region)
            }).toList
        }}
    }}
  }

  def getContinentFromLocation(location: Location, locations: LinkedHashMap[String, LinkedHashMap[String, Seq[Location]]]): Unit = {

  }
}
