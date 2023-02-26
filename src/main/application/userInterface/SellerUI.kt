package userInterface

import Seller

class  SellerUI(private val seller: Seller? = null) :UI {
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
                1 -> addProduct()
                2 -> removeProduct()
                3 -> displayCatalog()
                4 -> updateProductQuantity()
                5 -> loop = false
            }
        }
    }
    private fun addProduct(){

    }
    private fun removeProduct(){

    }
    private fun displayCatalog(){

    }
    private fun updateProductQuantity(){

    }

}