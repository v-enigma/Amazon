package users
import Address
import Cart
import Notification
import Order
import Product
import ProductCategory
import ProductDBFactory
import ProductWithQuantity
import java.time.LocalDate

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

    fun checkOut(productWithQuantity: ProductWithQuantity? = null) {
        val productsToBuy = whetherSingleProductOrCartToList(productWithQuantity)


    }
    private fun whetherSingleProductOrCartToList(productWithQuantity: ProductWithQuantity?):List<ProductWithQuantity>{
       return if(productWithQuantity == null){
            cart.getContentsInCart()
        }
        else{

            listOf(ProductWithQuantity(productWithQuantity.product, productWithQuantity.quantity))
        }
    }
}




