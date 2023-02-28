package Users

import Address
import Cart
import Notification
import Order
import Product
import ProductCategory
import ProductDBFactory
import java.time.LocalDate

class Customer(
    userId: Int,
    name: String,
    emailId: String,
    dateOfBirth: LocalDate,
    phoneNo: String,


): User(userId,name,emailId,dateOfBirth,phoneNo){
    internal var address : MutableList<Address> = mutableListOf()
    private val _pastOrders: MutableList<Order> = mutableListOf()
    val pastOrders :List<Order> = _pastOrders
    private val cart = Cart();
    val notifications : MutableList<Notification> = mutableListOf()

    fun search(keyWord:String, productCategory: ProductCategory):List<Product>{
       return ProductDBFactory.findMatchingProducts(keyWord, productCategory)
    }
    fun emptyCart(){
        cart.clearTheCart()
    }
    fun getItemsInCart(){

    }




}