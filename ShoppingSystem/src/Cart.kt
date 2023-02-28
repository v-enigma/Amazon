 internal class Cart( // rework access specifiers

    private var totalAmount:Double = 0.0,
    private val productsWithQuantity :MutableMap<Product,Int> = mutableMapOf(),
    private val offers : MutableList<Offer> = mutableListOf()
){
     fun calculateCartSummary(): Double{
         for(item in productsWithQuantity){
             totalAmount+= (item.key.price * item.value)
         }
         return totalAmount

     }
     fun incrementItemQuantity(product: Product){
         if(productsWithQuantity.contains(product))
            productsWithQuantity[product] = productsWithQuantity.getValue(product) + 1

     }
     fun decrementItemQuantity(product: Product){
         if(productsWithQuantity.contains(product) && productsWithQuantity.getValue(product)> 0){
             productsWithQuantity[product] = productsWithQuantity.getValue(product)-1
         }
     }
     fun applyOffer(offer: Offer){
         if(!offers.contains(offer)){
             offers.add(offer)
         }
     }
     fun clearTheCart(){
         val productsIterator = productsWithQuantity.iterator()
         while(productsIterator.hasNext()){
             productsIterator.remove()
         }
         val offersIterator = offers.iterator()
         while(offersIterator.hasNext()){
             offersIterator.remove()

         }
     }

 }

