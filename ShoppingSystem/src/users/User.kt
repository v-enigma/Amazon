package users

import Address
import Cart
import Notification
import Order
import Product
import SellerCatalog
import ProductWithQuantity
import SellerCatalogComponent
import enums.ManufacturerApproval
import enums.PaymentType
import enums.ProductCategory
import enums.RelationToProduct
import java.time.LocalDate

sealed  class User(
    val userId:Int,
    var name: String,
    var emailId: String,
    var dateOfBirth: LocalDate,
    var phoneNo :String)

class Seller(
    sellerId: Int,
    name: String,
    emailId: String,
    dateOfBirth: LocalDate,
    phoneNo: String
) : User(sellerId,name,emailId,dateOfBirth,phoneNo){
    private val sellerCatalog  = SellerCatalog()
    private val pickUpAddresses: MutableList<Address> = mutableListOf()

    fun removeProduct(productId:Int):Boolean {
        return sellerCatalog.removeProduct(productId)

    }
    fun addProduct(product: Product, quantity:Int, relationToProduct: RelationToProduct, manufacturerApproval: ManufacturerApproval?):Boolean{
        return sellerCatalog.addProduct(product,quantity,relationToProduct, manufacturerApproval)
    }
    fun displayAllProducts():List<SellerCatalogComponent>{
        return sellerCatalog.getAllProducts()

    }
    fun incrementExistingProductQuantity(productId: Int, quantity: Int){
        sellerCatalog.incrementProductQuantity(productId,quantity)
    }



}
class DeliveryAgent(
    userId: Int,
    name: String,
    emailId: String,
    dateOfBirth: LocalDate,
    phoneNo: String,
    var pinCode: Int // area covered assuming area is already decided by delivery boy
): User(userId,name,emailId,dateOfBirth,phoneNo) {
    private  val notifications : MutableList<Notification> = mutableListOf()
    private  val deliverables : MutableList<String> = mutableListOf()

}

class Customer(
    userId: Int,
    name: String,
    emailId: String,
    dateOfBirth: LocalDate,
    phoneNo: String,


    ): User(userId,name,emailId,dateOfBirth,phoneNo) {
    internal var address: MutableList<Address> = mutableListOf()
    private val _pastOrders: MutableList<Order> = mutableListOf()
    val pastOrders: List<Order> = _pastOrders
    private val cart = Cart()
    val notifications: MutableList<Notification> = mutableListOf()

    fun search(keyWord: String, productCategory: ProductCategory): List<Product> {
        return ProductDBFactory.findMatchingProducts(keyWord, productCategory)
    }

    fun emptyCart() {
        cart.clearTheCart()
    }

    fun getItemsInCart():List<ProductWithQuantity> {
        return cart.getContentsInCart()
    }

    fun addToCart(product: Product, quantity: Int) {
        cart.addProduct(product, quantity)
    }

    fun checkOut(productWithQuantity: ProductWithQuantity? = null, shippingAddress: List<String>,paymentType: PaymentType) {
        val productsToBuy = whetherSingleProductOrCartToList(productWithQuantity)
        OrderFactory.handleOrder(productsToBuy, shippingAddress,paymentType, cart.calculateCartSummary() )
    }
    private fun whetherSingleProductOrCartToList(productWithQuantity: ProductWithQuantity?):List<ProductWithQuantity>{
        return if(productWithQuantity == null){
            cart.getContentsInCart()
        }
        else{

            listOf(ProductWithQuantity(productWithQuantity.product, productWithQuantity.quantity))
        }
    }
    fun incrementCartContents(product:Product, quantity: Int){
        cart.incrementItemQuantity(product,quantity)
    }
    fun decrementCartContents(product:Product, quantity: Int){
        cart.decrementItemQuantity(product,quantity)
    }


}
object Admin :User(1,"ADMIN","admin@gmail.com", LocalDate.now(),"1234567890") {
    private val productApprover: ProductApprover = ProductApprover
    private val deliveryManager : DeliveryManager = DeliveryManager
    fun approveSellerRequests(){
        productApprover.evaluateProductApprovalRequests()
    }
    fun assignOrderToDeliveryAgents(){
        deliveryManager.assignOrders()
    }
}





