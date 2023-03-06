package userInterface

import ProductCategory
import ProductFactory
import users.Seller

class  SellerUI(private val seller: Seller) :UI {
     override fun menu(){
        val menuItems ="""1.Add product
            |2.Remove product
            |3.View Catalog
            |4.Update product Quantity
            |5.logout
        """.trimMargin()
        var loop = true

        while(loop){
            var input = InputHelper.getIntInputWithInRange(1,4)
            when(input) {
                1 -> addProduct(seller)
                2 -> removeProduct( seller)
                3 -> displayCatalog(seller)
                4 -> updateProductQuantity(seller)
                5 -> loop = false
            }
        }
    }
    private fun addProduct(seller:Seller){
        //seller.addProduct()
        println("Choose your product category. Enter index to choose")
        var index = 1
        ProductCategory.values().forEach { println("$index $it") ;index++}
        val categoryIndexInput = InputHelper.getIntInputWithInRange(1,ProductCategory.values().size)
        val productCategory = ProductCategory.values()[categoryIndexInput-1]
        println("Enter name of the product")
        val productName = InputHelper.getStringInput()
        println("Enter the price of the product")
        val price = InputHelper.getDouble()
        println("Enter the product description")
        val description = InputHelper.getStringInput()
        println("Enter no of units you have")
        val quantity = InputHelper.getIntegerInput();
        ProductFactory.createProduct(productName,price,description,productCategory,quantity,seller)

    }
    private fun removeProduct(seller:Seller){
        //seller.removeProduct()
    }
    private fun displayCatalog(seller:Seller){
        //seller.displayAllProducts()
    }
    private fun updateProductQuantity(seller: Seller){
        //seller.incrementExistingProductQuantity()

    }

}