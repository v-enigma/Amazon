 class Cart( // rework access specifiers
    var totalAmount:Double = 0.0,
    val productsWithQuantity :MutableMap<Product,Int> = mutableMapOf(),
    val offers : MutableList<Offer> = mutableListOf()
)
fun Cart.calculateCartSummary(): Double{
   for(item in productsWithQuantity){
       totalAmount+= (item.key.price * item.value)
   }
   return totalAmount

}
