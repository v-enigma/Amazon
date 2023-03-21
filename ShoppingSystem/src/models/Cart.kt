package models

internal class Cart{

     private var totalAmount :Double = 0.0
     private val offers : MutableList<Offer> = mutableListOf()
     private val itemsInCart : MutableMap<Int, ProductWithQuantity> = mutableMapOf()
     internal fun calculateCartSummary(): Double{

         for(item in itemsInCart) {
             item.value.product.price.let {
                 val itemCost = (item.value.quantity * it)

                 totalAmount += itemCost
             }

         }

         return totalAmount
     }
     internal fun incrementItemQuantity(product: Product, value:Int ){

         if(itemsInCart.contains(product.id))
             itemsInCart[product.id]?.quantity = itemsInCart[product.id]?.quantity.let{ val quantity = it?: 0;
                 quantity+value}

     }
     internal fun decrementItemQuantity(product: Product, incrementalValue:Int){
         if(itemsInCart.contains(product.id))
             itemsInCart[product.id]?.quantity = itemsInCart[product.id]?.quantity.let{ val quantity = it?: 0;
                 if(quantity-incrementalValue< 0)
                     0
                 else quantity-incrementalValue}
     }
     internal fun clearTheCart(){
         if(itemsInCart.isEmpty())
             return
         val productsIterator = itemsInCart.iterator()
         while(productsIterator.hasNext()){
             productsIterator.next()
             productsIterator.remove()
         }
         val offersIterator = offers.iterator()
         while(offersIterator.hasNext()){
             offersIterator.remove()

         }
     }
     internal fun addProduct(product: Product, quantity:Int){
         if(!itemsInCart.contains(product.id)){
             itemsInCart[product.id] = ProductWithQuantity(product,quantity)
         }
     }
     internal fun removeProduct(product: Product){
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
     internal fun containsInCart(productId:Int):Boolean{
         return (itemsInCart.containsKey(productId))
     }

 }

