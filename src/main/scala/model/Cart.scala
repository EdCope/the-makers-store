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

  def addItem(item: Item): Unit = {
    val stock = itemController.getItemsByLocation(location.name)
    if(stock.isEmpty) throw new Exception("Item not at location") else contents.append(item)
  }
}
