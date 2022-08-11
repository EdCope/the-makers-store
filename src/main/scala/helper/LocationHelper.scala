package helper

import main.model.Location

import scala.collection.mutable.LinkedHashMap

case class FlatLocation(id: Int, name: String, continent: String, region: String)

object LocationHelper {

  def flattenLocations(locations: LinkedHashMap[String, LinkedHashMap[String, Seq[Location]]]): List[FlatLocation] = {
    locations.foldLeft(List[FlatLocation]()) { (outputList, hMapOfRegion) => {
        val (continent,regionMap) = hMapOfRegion
        outputList ++ findLocationsInRegionMap(regionMap, continent)
    }}
  }

  private def findLocationsInRegionMap(regionMap: LinkedHashMap[String, Seq[Location]], continent: String): List[FlatLocation] = {
    regionMap.foldLeft(List[FlatLocation]()){(outputList, hMapOfLocation) => {
      val (region, locationSeq) = hMapOfLocation
      outputList ++ convertLocationsToFlatLocations(locationSeq, continent, region)
    }}
  }

  private def convertLocationsToFlatLocations(locations: Seq[Location], continent: String, region: String): List[FlatLocation] ={
    locations.map(location => {
      FlatLocation(location.id, location.name, continent, region)
    }).toList
  }

  def getContinentFromLocation(location: String, locations: LinkedHashMap[String, LinkedHashMap[String, Seq[Location]]]): String = {
    val flattened = LocationHelper.flattenLocations(locations)
    flattened.find(_.name == location) match {
      case Some(foundLocation) => foundLocation.continent
      case None => throw new Exception("That city doesn't exist")
    }
  }
}
