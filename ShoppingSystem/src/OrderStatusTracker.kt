data class OrderStatusTracker(
    val orderId: String,
    var deliveryStatus:DeliveryStage,
    var deliveryAgentId:String? = null ) {

}