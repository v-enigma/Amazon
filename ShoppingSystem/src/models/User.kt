package models

import data.OrderDB
import factories.Notification
import factories.OrderFactory
import SellerCatalog
import enums.*
import factories.ProductFactory
import roles.DeliveryManager
import roles.ProductApprover
import java.time.LocalDate

sealed  class User(
    val userId:Int,
    var name: String,
    var emailId: String?,
    var dateOfBirth: LocalDate,
    var phoneNo :String){
    fun getuserName():String{
        return name
    }
}

class Seller(
    sellerId: Int,
    name: String,
    emailId: String?,
    dateOfBirth: LocalDate,
    phoneNo: String,
    val registeredAddress: Address
) : User(sellerId,name,emailId,dateOfBirth,phoneNo){
    private val sellerCatalog  = SellerCatalog()


    fun removeProduct(productId:Int):Boolean {
        val status = sellerCatalog.removeProduct(productId)
        return status && ProductFactory.removeProduct(productId)

    }
    fun addProduct(sellerCatalogComponent: SellerCatalogComponent):Boolean{
        return sellerCatalog.addProduct( sellerCatalogComponent )
    }
    fun displayAllProducts():List<SellerCatalogComponent>{
        return sellerCatalog.getAllProducts()

    }
    fun incrementExistingProductQuantity(productId: Int, quantity: Int){
        sellerCatalog.incrementProductQuantity(productId,quantity)
    }
    fun decreaseExistingProductQuantity(productId:Int, quantity: Int){
        sellerCatalog.decrementProductQuantity(productId,quantity)
    }



}
class DeliveryAgent(
    userId: Int,
    name: String,
    emailId: String?,
    dateOfBirth: LocalDate,
    phoneNo: String,
    var pinCode: Int // area covered assuming area is already decided by delivery boy
): User(userId,name,emailId,dateOfBirth,phoneNo) {
    //private val notifications : MutableList<factories.Notification> = mutableListOf()
    internal val deliverables : MutableList<Int> = mutableListOf()

    fun viewAssignedDeliveries(){
        var index = 1
       deliverables.forEach{
           val address = OrderFactory.getOrderShippingAddressOfOrder(it)
           println("$index. $it -- $address")
           index++
       }
    }
    fun updatePackageDeliveryStatus(orderId:Int, status:DeliveryStage){
        OrderFactory.updateOrderStatus(orderId,status)
    }
    fun getDeliverables():List<Int>{
       return deliverables.toList()
    }
    fun getCurrentStatusOfOrder(orderId: Int):DeliveryStage{
        return OrderDB.getOrderStatus(orderId)
    }
}

class Customer(
    userId: Int,
    name: String,
    emailId: String?,
    dateOfBirth: LocalDate,
    phoneNo: String,
    ): User(userId,name,emailId,dateOfBirth,phoneNo) {
    internal var address: MutableList<Address> = mutableListOf()
    private val _orders: MutableList<Order> = mutableListOf()
    val orders: List<Order> = _orders
    private val cart = Cart()
    private val _notifications : MutableList<Notification> = mutableListOf()
    val notifications: List<Notification> = _notifications

    fun addNotification(notification: Notification){
        _notifications.add(0,notification)
    }

    fun search(keyWord: String, productCategory: ProductCategory): List<Product> {
        return ProductFactory.findMatchingProducts(keyWord, productCategory)
    }

    fun emptyCart() {
        cart.clearTheCart()
    }
    fun containsInCart(productId: Int):Boolean{
        return cart.containsInCart(productId)
    }
    fun getItemsInCart():List<ProductWithQuantity> {
        return cart.getContentsInCart()
    }
    fun getQuantityOfProductInCart(productId: Int):Int{
        return cart.getQuantityOfAProduct(productId)
    }

    fun addToCart(product: Product, quantity: Int) {
        cart.addProduct(product, quantity)
    }

    fun checkOut(productWithQuantity: ProductWithQuantity? = null, shippingAddress: List<String>, paymentType: PaymentType,paymentStatus: PaymentStatus) {
        val productsToBuy = whetherSingleProductOrCartToList(productWithQuantity)
        if(productWithQuantity == null)
            emptyCart()
        OrderFactory.handleOrder(productsToBuy, shippingAddress,paymentType, paymentStatus, cart.calculateCartSummary() , userId )
    }
    internal fun addOrder(order: Order){
        _orders.add(0,order)
    }
    private fun whetherSingleProductOrCartToList(productWithQuantity: ProductWithQuantity?):List<ProductWithQuantity>{
        return if(productWithQuantity == null){
            cart.getContentsInCart()
        }
        else{

            listOf(ProductWithQuantity(productWithQuantity.product, productWithQuantity.quantity))
        }
    }
    fun incrementCartContents(product: Product, quantity: Int){
        cart.incrementItemQuantity(product,quantity)
    }
    fun decrementCartContents(product: Product, quantity: Int){
        cart.decrementItemQuantity(product,quantity)
    }


}
object Admin : User(1,"ADMIN","admin@gmail.com", LocalDate.now(),"6234567890") {
    private val productApprover: ProductApprover = ProductApprover
    private val deliveryManager : DeliveryManager = DeliveryManager
    fun approveSellerRequests(){
       productApprover.evaluateProductApprovalRequests()
    }
    fun assignOrderToDeliveryAgents(){
        deliveryManager.assignOrders()
    }
}





