package model

import controller.ItemController
import main.db.{DbAdapter, DbAdapterBase}
import main.model.{Item, Location}

import scala.collection.mutable.ArrayBuffer

class Cart (val location: Location,
            val itemController: ItemController = new ItemController,
            val db: DbAdapterBase = DbAdapter,
            val uuidFactory: factory.UUIDFactoryBase = factory.UUIDFactory,
            val contents: ArrayBuffer[Item] = ArrayBuffer()) {
  val uuid = uuidFactory.create()

  def addItem(itemName: String, amountToAdd: Int): Unit = {
    val stock = itemController.getItemsByLocation(location.name)
    val requestedItem = stock.filter(_.name == itemName)

    if(stock.isEmpty) throw new Exception("Item not at location")
    else if (requestedItem(0).quantity < amountToAdd) throw new Exception("Not enough in stock")
    else contents.append(requestedItem(0))
  }

  def clearCart(): Unit = {
    contents.clear()
  }
}
