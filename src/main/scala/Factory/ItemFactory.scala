package Factory

import main.model.Item

trait ItemFactoryBase {
  def create(id: Int,
             name: String,
             price: Double,
             quantity: Int,
             availableLocales: List[String]): Item
}

object ItemFactory extends ItemFactoryBase {
  def create(id: Int,
             name: String,
             price: Double,
             quantity: Int,
             availableLocales: List[String]): Item = {
    new Item(id, name, price, quantity, availableLocales)
  }
}
