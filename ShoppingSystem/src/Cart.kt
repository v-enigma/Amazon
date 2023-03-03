
 internal class Cart{

     private var totalAmount :Double = 0.0
     private val offers : MutableList<Offer> = mutableListOf()
     private val itemsInCart : MutableMap<String, ProductWithQuantity> = mutableMapOf()
     internal fun calculateCartSummary(): Double{

         for(item in itemsInCart) {
             item.value.product.price.let {
                 val d = (item.value.quantity * it)

                 totalAmount += d
             }

         }

         return totalAmount
     }
     internal fun incrementItemQuantity(product: Product, value:Int ){

         if(itemsInCart.contains(product.id))
             itemsInCart[product.id]?.quantity = itemsInCart[product.id]?.quantity.let{ val d = it?: 0;
                 d+value}

     }
     internal fun decrementItemQuantity(product: Product,value:Int){
         if(itemsInCart.contains(product.id))
             itemsInCart[product.id]?.quantity = itemsInCart[product.id]?.quantity.let{ val d = it?: 0;
                 if(d-value < 0)
                     0
                 else d-value}
     }
     internal fun clearTheCart(){
         val productsIterator = itemsInCart.iterator()
         while(productsIterator.hasNext()){
             productsIterator.remove()
         }
         val offersIterator = offers.iterator()
         while(offersIterator.hasNext()){
             offersIterator.remove()

         }
     }
     internal fun addProduct(product:Product, quantity:Int){
         if(!itemsInCart.contains(product.id)){
             itemsInCart[product.id] = ProductWithQuantity(product,quantity)
         }
     }
     internal fun removeProduct(product:Product){
         if(itemsInCart.contains(product.id)){
             itemsInCart.remove(product.id)
         }
     }
     internal fun displayCart(){
         itemsInCart.forEach{ item -> println("${item.value.product.name} ${item.value.product.price} ${item.value.quantity}")}
     }
     internal fun getContentsInCart():List<ProductWithQuantity>{
        val productsWithQuantity = mutableListOf<ProductWithQuantity>()
         itemsInCart.forEach{  productsWithQuantity.add(it.value) }
        return productsWithQuantity.toList()
     }

 }

