import java.time.LocalDate

class Customer(
    userId: Int,
    name: String,
    emailId: String,
    dateOfBirth: LocalDate,
    phoneNo: String,
    var address : Address

): User(userId,name,emailId,dateOfBirth,phoneNo){
    private val _pastOrders: MutableList<Order> = mutableListOf()
    val pastOrders :List<Order> = _pastOrders
    private val cart = Cart();
    val notifications : MutableList<Notification> = mutableListOf()

}