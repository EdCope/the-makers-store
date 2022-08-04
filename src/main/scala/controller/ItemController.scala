package controller

import main.db.DbAdapter
import main.model.Item

import scala.collection.mutable.ArrayBuffer

class ItemController( ) {
  def fetchAll(): ArrayBuffer[Item] = {
    main.db.DbAdapter.getItems()
  }

  def create(name: String, price: Double, quantity: Integer, availableLocales: List[String]): Unit = {
//    {
//      "id": 7,
//      "name": "Orange Peel",
//      "price": 0.4,
//      "quantity": 8,
//      "availableLocales": ["EU", "UK"]
//    }
    val item = new Item(next_id, name, price, quantity, availableLocales)
    DbAdapter.createItem(item)
  }

  private def next_id:Int = fetchAll().last.id + 1
}
