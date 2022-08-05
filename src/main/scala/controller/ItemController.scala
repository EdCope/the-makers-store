package controller

import Factory.{ItemFactory, ItemFactoryBase}
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

  def create(name: String, price: Double, quantity: Integer, availableLocales: List[String]): Item = {
    val item = itemFactory.create(next_Id, name, price, quantity, availableLocales)
    db.createItem(item)
    item
  }

  private def next_Id:Int = {
    val itemArray = db.getItems()
    if (itemArray.length <= 0) 0 else itemArray.last.id + 1
  }
}
