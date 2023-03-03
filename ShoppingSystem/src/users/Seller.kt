package users

import Address
import Product
import SellerCatalog
import SellerCatalogComponent
import java.time.LocalDate

class Seller(
     sellerId: Int,
     name: String,
     emailId: String,
     dateOfBirth: LocalDate,
     phoneNo: String
 ) : User(sellerId,name,emailId,dateOfBirth,phoneNo){
     private val sellerCatalog  = SellerCatalog()
     private val pickUpAddresses: MutableList<Address> = mutableListOf()

      fun removeProduct(productId:String):Boolean {
         return sellerCatalog.removeProduct(productId)

     }
     fun addProduct(product: Product, quantity:Int):Boolean{
         return sellerCatalog.addProduct(product,quantity)
     }
     fun displayAllProducts():List<SellerCatalogComponent>{
         return sellerCatalog.getAllProducts()

     }
    fun incrementExistingProductQuantity(productId: String, quantity: Int){
        sellerCatalog.incrementProductQuantity(productId,quantity)
    }


}
