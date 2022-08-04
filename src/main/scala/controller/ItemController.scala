package controller

import main.model.Item

import scala.collection.mutable.ArrayBuffer


class ItemController( ) {
  def fetchAll(): ArrayBuffer[Item] = {
    main.db.DbAdapter.getItems()
  }
}
