import enums.DeliveryStage
import java.time.LocalDate

internal object OrderDB {
    private val allOrders :MutableMap<Int, Order> = mutableMapOf()

    private val ordersStatusTracker: MutableList<OrderStatusTracker> =  mutableListOf()
    internal fun addOrder(order:Order){
        allOrders[order.orderId] = order
    }
    internal fun addOrderStatusTracker( orderTracker: OrderStatusTracker){
        ordersStatusTracker.add(orderTracker)

    }
    internal fun getOrderTrackerInStatusTracker(id: Int):OrderStatusTracker{

        return ordersStatusTracker[id]
    }
    internal fun filterOrdersStatusTrackerByLocation():Map<Int,MutableList<OrderStatusTracker>>{
        val map = mutableMapOf<Int, MutableList<OrderStatusTracker>>()
        ordersStatusTracker.forEach{
            if(it.deliveryStatus == DeliveryStage.RECEIVED_ORDER && allOrders.contains(it.orderId)){
                val locationId = allOrders[it.orderId]!!.getLocation()
                if(!map.contains(locationId)){
                    map[locationId] = mutableListOf()
                }
                    map.getValue(locationId).add(it)
                }
            }
            return map.toMap()
        }
    internal fun getShippingAddressOfOrder(orderId:Int):Address{
        return allOrders.getValue(orderId).shippingAddress
    }
    internal fun updateStatus(orderStatusTrackerId: Int,status : DeliveryStage){
        ordersStatusTracker[orderStatusTrackerId].deliveryStatus = status


    }
    internal fun getOrderStatus(orderId:Int):DeliveryStage{
        return ordersStatusTracker[orderId].deliveryStatus
    }
}
