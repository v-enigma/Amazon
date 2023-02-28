package userInterface

import Users.Customer
import ProductCategory

class CustomerUI(private val customer: Customer):UI {
    override fun menu(){
        var loop = true

       val menuItems = """1.Search products
           |2.CheckOut
           |3.Your Orders
           |4.View Cart
           |5.Empty Cart
           |6.Exit
       """.trimMargin()
        while(loop){
            println(menuItems)
            var input = InputHelper.getIntInputWithInRange(1,6)
            when(input){
                1 -> search()
                2-> checkOut()
                3 -> viewOrders()
                4 -> viewCart()
                5-> emptyCart()
                6 -> loop = false

            }
        }


    }
    private fun search(){
        val keyWord = "Dummy"
        val productCategory = ProductCategory.BOOKS
        var searchResults = customer.search(keyWord,productCategory)

    }
    private fun viewOrders(){
        val pastOrders = customer.pastOrders
    }
    private fun viewCart(){

    }
    private fun emptyCart(){
        customer.emptyCart()
    }
    private fun checkOut(){
      //return bill
    }

}