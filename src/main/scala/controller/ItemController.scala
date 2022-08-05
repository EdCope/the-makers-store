package controller

import main.db.{DbAdapter, DbAdapterBase}
import main.model.Item

import scala.collection.mutable.ArrayBuffer

class ItemController(val db: DbAdapterBase = DbAdapter) {
  def fetchAll(): ArrayBuffer[Item] = {
    db.getItems()
  }

  def getItemById(id: Int): Item = {
    val items = db.getItems()
    items.filter(_.id == id)(0)
  }

  def create(name: String, price: Double, quantity: Integer, availableLocales: List[String]): String = {
    val item = new Item(next_id, name, price, quantity, availableLocales)
    db.createItem(item)
    ""
  }

  private def next_id:Int = fetchAll().last.id + 1
}
