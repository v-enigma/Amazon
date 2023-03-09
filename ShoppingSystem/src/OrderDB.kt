import enums.DeliveryStage
import java.time.LocalDate

internal object OrderDB {
    private val allOrders :MutableMap<Int, Order> = mutableMapOf()
    private val deliveriesMade: MutableMap<LocalDate, DeliveriesMadeByAgent > = mutableMapOf()
    private val ordersStatusTracker: MutableList<OrderStatusTracker> =  mutableListOf()
    internal fun addOrder(order:Order){
        allOrders[order.orderId] = order
    }
    internal fun addOrderStatusTracker( orderTracker: OrderStatusTracker){
        ordersStatusTracker.add(orderTracker)

    }
    internal fun getOrderTrackersInStatusTracker():List<OrderStatusTracker>{
        return ordersStatusTracker.toList()
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


}
internal class DeliveriesMadeByAgent{

    private val deliveryAgentToDeliveriesMade :MutableMap<Int, Int> = mutableMapOf()
    init{
        DeliveryAgentDB.getAllDeliveryAgentsIds().forEach{ deliveryAgentToDeliveriesMade[it] = 0 }
    }
    internal fun updateDeliverCount(id:Int, count:Int){
        deliveryAgentToDeliveriesMade[id] = deliveryAgentToDeliveriesMade[id]?.plus(count) ?: count
    }

}