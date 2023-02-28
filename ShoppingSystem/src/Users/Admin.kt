package Users

import java.time.LocalDate

object Admin :User(1,"ADMIN","admin@gmail.com", LocalDate.now(),"1234567890") {
    private val productApprover: ProductApprover = ProductApprover()
    private val deliveryManager: DeliveryManager = DeliveryManager()
}