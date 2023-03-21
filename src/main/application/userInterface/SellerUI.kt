package userInterface

import enums.ManufacturerApproval
import enums.ProductCategory
import factories.ProductFactory
import enums.RelationToProduct
import models.ProductWithQuantity
//import enums.RelationToProduct
import models.Seller
import models.SellerCatalogComponent

class  SellerUI(private val seller: Seller) :UI {
     override fun menu(){
        val menuItems ="""1.Add product
            |2.Remove product
            |3.View Catalog
            |4.Update product Quantity
            |5.logout
        """.trimMargin()


        while(true){
            println(menuItems)
            var input = InputHelper.getIntInputWithInRange(1,5)
            when(input) {
                1 -> addProduct()
                2 -> removeProduct()
                3 -> displayCatalog()
                4 -> updateProductQuantity()
                5 -> break
            }
        }
    }
    private fun addProduct(){
        //seller.addProduct()
        println("Choose your product category. Enter index to choose")
        var index = 1
        ProductCategory.values().forEach { println("$index $it") ;index++}
        val categoryIndexInput = InputHelper.getIntInputWithInRange(1, ProductCategory.values().size)
        val productCategory = ProductCategory.values()[categoryIndexInput-1]
        println("Enter name of the product")
        val productName = InputHelper.getStringInput()
        println("""Enter your relationship with product.
            | Are you a dealer  or Manufacturer.
            | Enter
            | 1 -> dealer
            | 2-> manufacturer""".trimMargin())
        val relationToProduct = when(InputHelper.getIntInputWithInRange(1,2)){
            1 -> RelationToProduct.DEALER
            2 -> RelationToProduct.MANUFACTURER
            else ->
                println("Enter valid option")

        }
        var manufacturerApproval : ManufacturerApproval = ManufacturerApproval.NOT_APPLICABLE
        if(relationToProduct == RelationToProduct.DEALER){1
            println("Do you have manufacturer approval? Enter  yes or no")
            manufacturerApproval = when(InputHelper.getStringInput()){
                 "y","Y" -> ManufacturerApproval.YES
                  "n","N" -> ManufacturerApproval.NO
                 else -> ManufacturerApproval.NOT_APPLICABLE
            }
        }
        println("Enter the price of the product")
        val price = InputHelper.getDouble()
        println("Enter the product description")
        val description = InputHelper.getStringInput()
        println("Enter no of units you have")
        val quantity = InputHelper.getIntInputWithInRange(1,100000);
        ProductFactory.createProduct(productName,price,description,productCategory,quantity,seller,relationToProduct as RelationToProduct, manufacturerApproval )

    }
    private fun removeProduct(){
        if(displayCatalog() > 0) {
            println("Enter the index of the product you want to remove")
            val input = InputHelper.getIntInputWithInRange(1, seller.displayAllProducts().size)
            seller.removeProduct(seller.displayAllProducts()[input - 1].productWithQuantity.product.id)
        }

    }
    private fun displayCatalog():Int{
        var index = 1
        seller.displayAllProducts().forEach{ PrintHelper.printProduct(it, index); index++}
        if(seller.displayAllProducts().isEmpty()){
            println("No products to under your catalog ")
        }
        return seller.displayAllProducts().size
    }

    private fun updateProductQuantity(){
        if(displayCatalog() > 0) {
            println("Enter the index of the product you want to increase the quantity")
            val input = InputHelper.getIntInputWithInRange(1, seller.displayAllProducts().size)
            println("Enter no of units you want to increase")
            val quantity = InputHelper.getIntegerInput()
            //println(seller.displayAllProducts()[input-1])
            seller.incrementExistingProductQuantity(
                seller.displayAllProducts()[input - 1].productWithQuantity.product.id,
                quantity
            )
        }
    }

}