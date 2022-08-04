package controller

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
    println(next_id)
    val item = new Item(next_id, name, price, quantity, availableLocales)
    main.db.DbAdapter.createItem(item)
  }

  private def next_id:Int = fetchAll().last.id + 1
}
