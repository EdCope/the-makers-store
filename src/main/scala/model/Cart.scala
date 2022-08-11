package model

import main.model.Item
import scala.collection.mutable.ArrayBuffer

class Cart (val uuidFactory: factory.UUIDFactoryBase = factory.UUIDFactory,
            val contents: ArrayBuffer[Item] = ArrayBuffer()) {
  val uuid = uuidFactory.create()
}
