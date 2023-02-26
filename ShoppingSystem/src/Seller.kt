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

     internal fun removeProduct(productId:String) {
         sellerCatalog.removeProduct(productId)

     }
     fun addProduct(product:Product,quantity:Int){
         sellerCatalog.addProduct(product,quantity)
     }
     fun displayAllProducts():List<SellerCatalogComponent>{
         return sellerCatalog.getAllProducts()

     }
    fun incrementExistingProduct(productId: String, quantity: Int){
        sellerCatalog.incrementProductQuantity(productId,quantity)
    }

}
