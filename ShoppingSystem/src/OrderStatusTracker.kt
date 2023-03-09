import enums.DeliveryStage

data class OrderStatusTracker(
    val orderId: Int,
    var deliveryStatus: DeliveryStage,
    internal var deliveryAgentId: String? = null
)