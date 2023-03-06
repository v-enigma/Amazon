package users
import OrderStatusTracker
import java.time.LocalDate

object Admin :User(1,"ADMIN","admin@gmail.com", LocalDate.now(),"1234567890") {
    internal val productApprover: ProductApprover = ProductApprover
    internal val deliveryManager : DeliveryManager = DeliveryManager
}

