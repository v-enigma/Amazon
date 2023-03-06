package userInterface

import PaymentType
import Product
import users.Customer
import ProductCategory

class CustomerUI(private val customer: Customer):UI {
    override fun menu(){
        var loop = true

       val menuItems = """1.Do Shopping
           |2.CheckOut
           |3.Your Orders
           |4.View Cart
           |5.Empty Cart
           |6.Exit
       """.trimMargin()
        while(loop){
            println(menuItems)
            var input = InputHelper.getIntInputWithInRange(1,7)
            when(input){
                1 -> shopping()
                2 -> checkOut()
                3 -> viewOrders()
                4 -> viewCart()
                5 -> emptyCart()
                6 -> incrementProductQuantityInCart()
                7 -> break

            }
        }


    }
    private fun incrementProductQuantityInCart(){
        viewCart()
        println("Enter the index of the product you want to increase the quantity")
        val input = InputHelper.getIntInputWithInRange(1, customer.getItemsInCart().size)
        val quantity = InputHelper.getIntegerInput()
        customer.incrementCartContents(customer.getItemsInCart()[input-1].product,quantity )
    }
    private fun 
    private fun shopping(){
        val keyWord = "Dummy"
        val productCategory = ProductCategory.BOOKS
        val searchResults = customer.search(keyWord,productCategory)
        var index = 1
        searchResults.forEach {InputHelper.printProduct(it,index); index++}

        if(searchResults.isNotEmpty()) {
            println("Enter the product serial no to select the product ")
            val productIndex = InputHelper.getIntInputWithInRange(0, searchResults.size-1)
            println("""Would you like to add to cart or proceed to buy
                |1.Add to Cart
                |2.Proceed to buy
            """.trimMargin())
            when (InputHelper.getIntInputWithInRange(1, 2)) {
                1 -> addToCart(searchResults[productIndex])
                2 -> checkOut()
            }
        }
        println("""Would you like to proceed shopping or Checkout.
            |Enter your option
            |1. proceed shopping
            |2.CheckOut
        """.trimMargin())
        when(InputHelper.getIntInputWithInRange(1,2)){
            1 -> search()
            2 -> checkOut()
        }

    }
    private fun viewOrders(){
         customer.pastOrders.forEach{order -> println("""${order.orderId}  ${order.orderedDate} ${order.deliveredDate} ${order.shippingAddress} ${order.total}""");
            var index = 1;
            order.productsWithQuantity.forEach{item -> InputHelper.printProduct(item.product,index,item.quantity);index++}
         }

    }
    private fun viewCart(){
        val cartContents = customer.getItemsInCart();
        var index = 1
        cartContents.forEach{item -> InputHelper.printProduct(item.product, index, item.quantity);index++ }

    }
    private fun emptyCart(){
        customer.emptyCart()
    }
    private fun checkOut(){
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
        customer.checkOut(shippingAddress=shippingAddress, paymentType = paymentType as PaymentType)
    }
    private fun addToCart(product: Product, quantity:Int = 1){
        customer.addToCart(product,quantity)
    }
    private fun search(){

    }

}