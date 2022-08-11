package model

import main.model.{Item, Location}

import scala.collection.mutable.ArrayBuffer

class Cart (val location: Location, val uuidFactory: factory.UUIDFactoryBase = factory.UUIDFactory,
            val contents: ArrayBuffer[Item] = ArrayBuffer()) {
  val uuid = uuidFactory.create()

  def addItem(item: Item): Unit = {
    contents.append(item)
  }
}
