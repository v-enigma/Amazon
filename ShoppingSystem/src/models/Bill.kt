package models

class Bill( // has to work on access modifier  of the class
 val billId :Int,
 val sellerName: String,
 val sellerAddress: Address,
 val customerName: String,
 val shippingAddress: Address,
 val productsWithQuantity: Map<Product, Int>,
 val offers:List<Offer>

 ) {
  val totalAmount :Double
   get() = 0.0 // has to implement it

}