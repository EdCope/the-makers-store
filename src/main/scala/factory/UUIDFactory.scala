package factory

import java.util.UUID

trait UUIDFactoryBase {
  def create(): String
}

object UUIDFactory extends UUIDFactoryBase {
  def create(): String ={
    UUID.randomUUID.toString
  }
}
