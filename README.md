# The Makers Store

The makers store is a lightweight API designed to represent an e-commerce website's API.

In this excercise, you'll be building out the features of this API. We won't be using any frameworks or any real databases, this excercise will focus solely on the architecture and some problems where functional programming might come in handy.

We want to stick to the premises that the core functionalities of our app should be functional, and we only want to use object oriented styles where I/O is concerned.

You can assume that all User I/O with this "API" will be done so through the command line rather than HTTP. As for the database I/O, we'll connect to a mysterious local database that should be explained in the boiler plate code provided.

## Exercise

The Makers Store is currently only available in certain cities globally. We track the locations available as `Location`s, stored in the locations table. The Makers Store carries a variety of `Item`s, which have a variety of prices, quantities, and most importantly what regions the product is available in.

The first phase of the project is to fully build out this API so that we can:
- Create, update, and fetch all `Item`s (These should feel fairly redundant, but we're using this to demonstrate decoupling from our database. More on this later)
- Fetch a particular `Item` by it's ID
- Fetch all `Item`s available in a particular `Location` (either by name or id)
- Fetch all `Location`s in a given continent (i.e. "NA", "EU")

To accomplish this, we'll likely need some kind of controller. Or a couple. Don't worry about connecting them to HTTP for now. It should be sufficient that if we made a class called `Client` that calls to these controller methods, they would simply return the raw data returned from our queries. For example, a call to fetchAll `Item` should return some form of `Iterable[Item]`. Don't worry about returning JSON or HTML like a real API would

It might even be a good idea to make a new class `Client`. Since we aren't setting up a real API over HTTP, nor using a real database, the `Client` will act as our user for the purpose of these excercises. This would be a way we could test I/O and write integration tests. Or not. Just an idea :)

In order to accomplish this, you'll probably need to use the `DbAdapter`

### The Database

Much like the gilded rose, there is indeed a Goblin living within this codebase who doesn't like their code being touched much. It's required that you do not change the following directories:

`src/main/resources/*`
`src/main/scala/db/*`
`src/main/scala/payment/*`

This is meant to illustrate often how these things are abstracted in the real world. We want to design software that isn't tightly coupled to any particular solution. You should be able to build your API in such a way that if we changed from our current RyQL database to, say, a postgres one our app would remain mostly unchanged. Similarly, the PaymentAdapter is meant to represent a third party payment gateway. This represents outside world I/O.

We will however provide simple instructions of how the database adapter works:

`DbAdapter` is an object (or static class)

`DbAdapter.getItems(): ArrayBuffer[Item]` this will query the database for all `Item`s and return them to you as an ArrayBuffer

`DbAdapter.getLocations(): LinkedHashMap[String, LinkedHashMap[String, Seq[Location]]]` this will query the database for all locations. They are stored in a nested hash map to preserve their taxonomy (i.e. EU -> UK -> London)

`DbAdapter.updateItem(id: Int, newItem: Item): Unit` this will update a single `Item` matching the id provided. We want to provide an entirely new Item, rather than any individual field to update. The existing record will simply be replaced with our new one

`DbAdapter.createItem(newItem: Item): Unit` this will add a new `Item` to our list of `Item`s. 

`DbAdapter.dropAndReset(): Unit` will reset the database to it's default seeded state (Items fully stocked to the default inventory)

You can assume all insert and update operations are successful.

### Task List

**Important**
- [ ] Complete the initial excercise to build out the basic API functions
- [ ] Add the ability to have a Cart
  - [ ] A Cart should be assigned a UUID
  - [ ] A Cart allows us to add an item to our cart, assuming we still have enough quantity in stock and it's available in the city we're in
  - [ ] A Cart also allows us to clear its contents entirely, or remove a particular item
  - [ ] We can change the quantity of an item already in our cart, so long as the new quantity is in stock
  - [ ] We should have two functions `onPaymentSuccess` and `onPaymentFailed`. A failed payment should clear the cart, while a successful payment should update our inventory to subtract the recently sold stock.
  - [ ] We want to wire this up to the `PaymentAdapter`. Think of this as simulating a third party payment gateway like Paypal - we just need to pass the total amount to be charged and it'll let us know whether it was successful or not

**Stretch**
- [ ] Add the ability to track all transactions that have occurred during a session. We'll define a session as starting the app, and then ending it. We don't need to store these in the database, but we do want to have a few functionalities. We may want to create a service so that we can store this information in memory, because the functionaities we'd like are:
  - [ ] Track total sales in a session
  - [ ] Track total sales per continent, region, and city
  - [ ] Track the biggest selling item in a session
  - [ ] Generate a report with the name of each item and how much quantity was sold (i.e. this should be a HashMap of (Name -> quantitySold))
  - [ ] Track the sum total amount of failed transactions in a session

  ### The Payment Adapter

  The payment adapter has one method, `makePayment(amount: Double, onSuccess: (Payment) => Any, onFailure: (Payment) => Any): Unit`

  This means that it accepts two *functions* as arguments. This is something we haven't really touched much in Scala so far. Functions are *first class* in Scala - this means they can be used as values. Here, we're asked to provide two functions that have the signature of accepting a `Payment` as an argument and return anything. It's up to us how our success and failure functions are implemented, but they need to accept one argument which is a `Payment` class

  The `Payment` class has one property `success` which simply returns true or false depending on whether the payment was successful and the amount.



