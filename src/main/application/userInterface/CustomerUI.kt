package userInterface

import enums.PaymentStatus
import factories.OrderFactory
import enums.PaymentType
import models.Product
import factories.ProductFactory
import models.Customer
import enums.ProductCategory
import models.Order
import models.ProductWithQuantity
import java.time.LocalDate

class CustomerUI(private val customer: Customer):UI {

    override fun menu(){
       val menuItems = """1.Do Shopping
           |2.CheckOut
           |3.Your Orders
           |4.View Cart
           |5.Empty Cart
           |6.Increment Product Quantity In Cart
           |7.Decrement Product Quantity In Cart
           |8.Search a Product
           |9.view Notifications
           |10.cancel Orders
           |11.Exit
       """.trimMargin()
        while(true){
            println(menuItems)
            var input = InputHelper.getIntInputWithInRange(1,11)
            when(input){
                1 -> shopping()
                2 -> checkOut()
                3 -> viewOrders()
                4 -> viewCart()
                5 -> emptyCart()
                6 -> incrementProductQuantityInCart()
                7 -> decrementProductQuantityInCart()
                8 -> search()
                9 -> viewNotifications()
                10 -> cancelOrders()
                11 -> break
            }
        }

    }

    private fun shopping(){

       if(validateDeliveryPossibility()) {
           val searchResults = search()
           var index = 1
           searchResults.forEach { PrintHelper.printProduct(it, index); index++ }

           when {
               (searchResults.isNotEmpty()) -> {
                   println("Enter the product serial no to select the product ")
                   val productIndex = InputHelper.getIntInputWithInRange(1, searchResults.size)
                   println(
                       """Would you like to add to cart or proceed to buy
                |1.Add to Cart
                |2.Proceed to buy
            """.trimMargin()
                   )
                   when (InputHelper.getIntInputWithInRange(1, 2)) {
                       1 -> addToCart(searchResults[productIndex - 1])
                       2 -> {
                           println("Enter the quantity. You can only choose up to 5 units ")
                           val quantity = InputHelper.getIntInputWithInRange(1, 5)
                           checkOut(ProductFactory.getProductWIthQuantity(searchResults[productIndex - 1], quantity));
                           return
                       }
                   }

                   println(
                       """Would you like to proceed shopping or Checkout.
            |Enter your option
            |1. proceed shopping
            |2.CheckOut
        """.trimMargin()
                   )
                   when (InputHelper.getIntInputWithInRange(1, 2)) {
                       1 -> shopping()
                       2 -> checkOut()
                   }
               }

               else -> {
                   println("No products found.Regret the inconvenience")
               }
           }
       }
    }
    private fun viewOrders(){
        printOrders(customer.orders)
        println("""Enter 
            |1 -> to rate the products you used
            |2 -> To view Invoice of the Order
            |3 -> Ignore Above options and exit
        """.trimMargin())
        when(InputHelper.getIntInputWithInRange(1,3)){
            1 -> rateProducts()
            2 -> viewInvoice()
        }

    }
    private  fun filterOrdersByEligibilityForCancellation(): List<Order>{
        val ordersEligibleFoCancellation = mutableListOf<Order>()
        customer.orders.forEach{
            if(OrderFactory.isOrderEligibleForCancellation(it.orderId))
                ordersEligibleFoCancellation.add(it)

        }
        return ordersEligibleFoCancellation.toList()

    }
    private fun printOrders(orders:List<Order>){
        var orderIndex = 1
        orders.forEach{order -> println("""$orderIndex ${order.orderId}  ${order.orderedDate} ${order.deliveredDate} ${order.shippingAddress} ${order.total}""")
            var index = 1
            order.productsWithQuantity.forEach{item -> PrintHelper.printProduct(item.product,index,item.quantity);index++}
            orderIndex++
        }
    }
    private fun cancelOrders(){
        val orderEligibleForCancellation = filterOrdersByEligibilityForCancellation()
        printOrders(orderEligibleForCancellation)
        when {
            (orderEligibleForCancellation.isNotEmpty()) -> {
                println("These are the order eligible for cancellation.")
                println("Do you like to proceed with cancellation ? Enter yes or no")
                val userInput = InputHelper.getYesOrNo()
                if (userInput == "yes") {
                    println("Enter the index of the order you want to cancel.")
                    val index = InputHelper.getIntInputWithInRange(1, orderEligibleForCancellation.size)
                    OrderFactory.cancelOrder(orderEligibleForCancellation[index - 1].orderId)
                }
            }

            else -> {
                println("No order is eligible for cancellation")
            }
        }


    }
    private fun viewInvoice(){
      printOrders(customer.orders)
      println("Enter Index of the order ")
      val orderIndex = InputHelper.getIntInputWithInRange(1, customer.orders.size)
      val bills = OrderFactory.getBills(customer.orders[orderIndex-1].orderId)
      if(bills.size > 1){
        println(" We have more than 1 bill for this order")

      }
        bills.forEach {println(it)}
    }
    private fun rateProducts(){

        do {

            println("Enter the index of the order ")
            val orderIndex = InputHelper.getIntInputWithInRange(1, customer.orders.size)
            val order = customer.orders[orderIndex - 1]
            println("Enter the index of the product")
            val productIndexInOrder = InputHelper.getIntInputWithInRange(1, order.productsWithQuantity.size)
            val productWithQuantity = order.productsWithQuantity[productIndexInOrder - 1]
            println("Enter the rating ")
            val rating = InputHelper.getFloatInputWithInRange(0.0F, 5.0F)
            println("Do you like to comment what you felt good about the product? Enter  yes or No")
            val yesOrNo = InputHelper.getYesOrNo()
            var comment: String? = null
            if (yesOrNo == "yes")
                comment = InputHelper.getStringInput()
            ProductFactory.reviewProduct(
                customer.userId,
                customer.name,
                comment,
                rating,
                LocalDate.now(),
                productWithQuantity.product.id
                )
            println("Do you like to review your orders ? Enter yes or No ")
            val input = InputHelper.getYesOrNo()
            if(input == "no")
               break
        }while(true)
    }
    private fun viewCart(){
        val cartContents = customer.getItemsInCart();
        var index = 1
        cartContents.forEach{item -> PrintHelper.printProduct(item.product, index, item.quantity);index++ }
    }
    private fun emptyCart(){
        customer.emptyCart()
    }
    private fun checkOut(productWithQuantity: ProductWithQuantity?= null){
        if(customer.getItemsInCart().isEmpty() && productWithQuantity == null){
            println("Cart is empty")
            return
        }
        println("Enter shipping Address")
        val shippingAddress = InputHelper.getAddress()
        println("""Enter payment type 
            |1.Cash On Delivery 
            |2.UPI """.trimMargin())

        val paymentType  = when(InputHelper.getIntInputWithInRange(1,2)){
            1-> PaymentType.CASH_ON_DELIVERY
            2-> PaymentType.UPI
            else ->{
                println(" Please choose correct option")
            }
        }
        var paymentStatus= PaymentStatus.NOT_PAID
        if(paymentType == PaymentType.UPI){
            paymentStatus =  pay()
        }
        productWithQuantity?.let{customer.checkOut(productWithQuantity,shippingAddress=shippingAddress, paymentType = paymentType as PaymentType, paymentStatus = paymentStatus)
        } ?: customer.checkOut(shippingAddress=shippingAddress, paymentType = paymentType as PaymentType, paymentStatus = paymentStatus)
    }
    private fun addToCart(product: Product){
        println("Enter the quantity in the range of 1 to 5 ")
        val quantity = InputHelper.getIntInputWithInRange(1,5)
        if(validateProductQuantity(product, quantity)!=-1)
            if(customer.containsInCart(product.id))
                if(customer.getQuantityOfProductInCart(product.id) + quantity <= 5 )
                    customer.incrementCartContents(product, quantity)
                else
                    println("count exceeds the limit of 5 ")
            else
                customer.addToCart(product,quantity)

    }
    private fun validateProductQuantity(product:Product, quantity:Int):Int{
       val availableQuantity = ProductFactory.validateQuantityExistence(product,quantity)
        return if(availableQuantity != quantity  && availableQuantity > 0 ) {
            println("Only $availableQuantity units are left .Do you like to proceed with this. Enter yes or No")
            val userInput = InputHelper.getYesOrNo()
            if (userInput  == "yes")
                availableQuantity
            else -1
        } else  if(availableQuantity == 0 ) {
            println("Not available")
            -1
        } else availableQuantity

    }
    private fun search(): List<Product>{
        println("Choose your category from below")
        var categoryIndex =1
        ProductCategory.values().forEach { println("$categoryIndex $it");categoryIndex++ }
        println("Enter serial number to choose the product")
        val categoryInput = InputHelper.getIntInputWithInRange(1, ProductCategory.values().size)
        println("Enter the product you are searching")
        val keyWord = InputHelper.getStringInput()
        return customer.search(keyWord, ProductCategory.values()[categoryInput-1])

    }
    private fun pay():PaymentStatus{
        return PaymentStatus.PAID
    }
    private fun incrementProductQuantityInCart(){
        viewCart()
        if(customer.getItemsInCart().isNotEmpty()) {
            println("Enter the index of the product you want to increase the quantity")
            val input = InputHelper.getIntInputWithInRange(1, customer.getItemsInCart().size)
            val quantity = InputHelper.getIntegerInput()
            customer.incrementCartContents(customer.getItemsInCart()[input - 1].product, quantity)
        }
        else{
            println("Cart is empty")
        }
    }
    private fun decrementProductQuantityInCart(){
        viewCart()
        println("Enter the index of the product you want to increase the quantity")
        val input = InputHelper.getIntInputWithInRange(1, customer.getItemsInCart().size)
        val quantity = InputHelper.getIntegerInput()
        customer.decrementCartContents(customer.getItemsInCart()[input-1].product,quantity)
    }
    private fun validateDeliveryPossibility():Boolean{
         while(true) {
            println("Enter the pinCode of the shipping Location")
            val pinCode = InputHelper.getIntegerInput()
            if(!OrderFactory.checkPossibilityOfDelivery(pinCode)){
                println("""We are currently not serving the location. Would you like to change the location 
                    | Enter yes or No
                """.trimMargin())
                var input = InputHelper.getYesOrNo()
                return when(input){
                    "yes" -> validateDeliveryPossibility()
                    "no" -> false
                    else -> false
                }
            }

            else {

                return true
            }
        }

    }
    private fun viewNotifications(){
        customer.notifications.forEach{
            println("""${it.description} -> ${it.generatedTime.toLocalDate()}""")
        }
    }

}