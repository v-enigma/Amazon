import java.time.LocalDate

class DeliveryAgent(
    userId: Int,
    name: String,
    emailId: String,
    dateOfBirth: LocalDate,
    phoneNo: String,
    var pinCode: Int // area covered assuming area is already decided by delivery boy
    ):User(userId,name,emailId,dateOfBirth,phoneNo) {
       private  val notifications : MutableList<Notification> = mutableListOf()
       private  val deliverables : MutableList<String> = mutableListOf()

}