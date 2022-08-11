package main

import helper.LocationHelper
import main.db.DbAdapter

// This class simply exists so we have a main method to run the application. Feel free to do with it what you please
object App {
  def main(args: Array[String]): Unit = {
    App.start()
  }
  
  def start(): String = {
    val db = DbAdapter.getLocations()

    "OK"
  }
}
