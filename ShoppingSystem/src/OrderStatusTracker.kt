data class OrderStatusTracker(
    val orderId: Int,
    var deliveryStatus: DeliveryStage,
    var deliveryAgentId: String? = null
)