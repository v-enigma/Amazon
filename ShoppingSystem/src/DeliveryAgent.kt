import java.time.LocalDate

class DeliveryAgent(
    userId: String,
    name: String,
    emailId: String,
    dateOfBirth: LocalDate,
    phoneNo: String,
    var pinCode: Int // area covered
    ):User(userId,name,emailId,dateOfBirth,phoneNo) {
       private  val notifications : MutableList<Notification> = mutableListOf()
       private  val deliverables : MutableList<String> = mutableListOf()

}