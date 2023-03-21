package data

import models.Address
import models.Order
import models.OrderStatusTracker
import enums.DeliveryStage
import java.time.LocalDate

internal object OrderDB {
    private val allOrders :MutableMap<Int, Order> = mutableMapOf()
    private val ordersStatusTracker: MutableMap<Int, OrderStatusTracker> =  mutableMapOf()
    private val cancelledOrdersWithStatus: MutableMap<Int, OrderStatusTracker> = mutableMapOf()
    internal fun addOrder(order: Order){
        allOrders[order.orderId] = order
    }
    internal fun addOrderStatusTracker( orderTracker: OrderStatusTracker){
        ordersStatusTracker[orderTracker.orderId] = orderTracker
    }
    internal fun getOrderTrackerInStatusTracker(id: Int): OrderStatusTracker {
        return ordersStatusTracker.getValue(id)
    }
    internal fun filterOrdersStatusTrackerByLocation():Map<Int,MutableList<OrderStatusTracker>>{
        val deliveryAgentsByLocation = mutableMapOf<Int, MutableList<OrderStatusTracker>>()
        ordersStatusTracker.forEach{
            if(it.value.deliveryStatus == DeliveryStage.RECEIVED_ORDER && allOrders.contains(it.value.orderId)){
                val locationId = allOrders[it.value.orderId]?.getLocation() ?: 0
                if(!deliveryAgentsByLocation.contains(locationId)){
                    deliveryAgentsByLocation[locationId] = mutableListOf()
                }
                    deliveryAgentsByLocation.getValue(locationId).add(it.value)
                }
            }
            return deliveryAgentsByLocation.toMap()
        }
    internal fun getShippingAddressOfOrder(orderId:Int): Address {
        return allOrders.getValue(orderId).shippingAddress
    }
    internal fun updateStatus(orderStatusTrackerId: Int,status : DeliveryStage){

        ordersStatusTracker[orderStatusTrackerId]?.deliveryStatus = status
        if(status == DeliveryStage.DELIVERED)
            allOrders.getValue(orderStatusTrackerId).deliveredDate = LocalDate.now()
    }
    internal fun getOrderStatus(orderId:Int):DeliveryStage{
        return ordersStatusTracker.getValue(orderId).deliveryStatus
    }
    internal fun moveToCancelledMap(orderId: Int){
        val statusTracker : OrderStatusTracker = ordersStatusTracker.getValue(orderId)
        ordersStatusTracker.remove(orderId)
        cancelledOrdersWithStatus[orderId] = statusTracker
    }
    internal fun getOrder(orderId: Int):Order{
        return allOrders.getValue(orderId)
    }

}
