package userInterface

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