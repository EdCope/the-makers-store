package Factory

trait UUIDFactoryBase {
  def create(): String
}

object UUIDFactory extends UUIDFactoryBase {
  def create(): String ={
    ""
  }
}
