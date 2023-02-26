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

}