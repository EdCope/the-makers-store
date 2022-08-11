package controller

import factory.{ItemFactory, ItemFactoryBase}
import main.db.{DbAdapter, DbAdapterBase}
import main.model.Item

import scala.collection.mutable.ArrayBuffer

class ItemController(val db: DbAdapterBase = DbAdapter, val itemFactory: ItemFactoryBase = ItemFactory) {

  def fetchAll(): ArrayBuffer[Item] = {
    db.getItems()
  }

  def getItemById(id: Int): Item = {
    val items = db.getItems()
    items.filter(_.id == id)(0)
  }

  def getItemsByLocation(location: String): Array[Item] = {
    val items = db.getItems()
    val locations = db.getLocations()
    val continent = helper.LocationHelper.getContinentFromLocation(location, locations)
    items.filter(_.availableLocales.contains(continent)).toArray
  }

  def updateItemById(id: Int)(name: Option[String] = None, price: Option[Double] = None, quantity: Option[Int] = None, availableLocales: Option[List[String]] = None): Item = {
    val prevItem = getItemById(id)

    val nName = name.getOrElse(prevItem.name)
    val nPrice = price.getOrElse(prevItem.price)
    val nQuantity = quantity.getOrElse(prevItem.quantity)
    val nAvailableLocales = availableLocales.getOrElse(prevItem.availableLocales)

    val newItem = itemFactory.create(id, nName, nPrice, nQuantity, nAvailableLocales)
    db.updateItem(id, newItem)
    newItem
  }

  def create(name: String, price: Double, quantity: Integer, availableLocales: List[String]): Unit = {
    val item = itemFactory.create(next_Id, name, price, quantity, availableLocales)
    db.createItem(item)
  }

  private def next_Id:Int = {
    val itemArray = db.getItems()
    if (itemArray.length <= 0) 0 else itemArray.last.id + 1
  }
}
