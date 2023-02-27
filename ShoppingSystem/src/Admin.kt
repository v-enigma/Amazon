import java.time.LocalDate

class Admin private constructor(
    userId: Int,
    name: String,
    emailId: String,
    dateOfBirth: LocalDate,
    phoneNo: String,
    private val productApprover: ProductApprover,
    private val deliveryManager: DeliveryManager
) :
    User(userId, name, emailId, dateOfBirth, phoneNo) {

    companion object {

        private val admin =
            Admin(1, "ADMIN", "amin@gmail.com", LocalDate.now(), "1234567890", ProductApprover(), DeliveryManager());

        fun getInstance(): Admin {
            return admin
        }
    }
}