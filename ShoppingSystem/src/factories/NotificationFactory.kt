package factories

import data.CustomerDB
import java.time.LocalDateTime

class Notification(val description: String, val generatedTime: LocalDateTime)
internal object NotificationFactory {
    private fun createNotification(description: String): Notification {
       return Notification(description, LocalDateTime.now())
    }
    fun notifyCustomer(description: String,customerId:Int){
        CustomerDB.getUser(customerId).addNotification(createNotification(description))
    }
}