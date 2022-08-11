package model

class Cart (val uuidFactory: factory.UUIDFactoryBase = factory.UUIDFactory) {
  val uuid = uuidFactory.create()
}
