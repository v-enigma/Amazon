import enums.DeliveryStage

data class OrderStatusTracker(
    val orderId: Int,
    val customerId:Int,
    internal var deliveryStatus: DeliveryStage,
    internal var deliveryAgentId: String? = null
){
    internal fun  updateDeliveryStatus(stage: DeliveryStage){
        deliveryStatus = stage
    }
}