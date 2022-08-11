package model

class Cart (val uuidFactory: Factory.UUIDFactoryBase = Factory.UUIDFactory) {
  val uuid = uuidFactory.create()
}
