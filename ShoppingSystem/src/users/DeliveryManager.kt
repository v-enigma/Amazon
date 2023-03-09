package users

import DeliveryAgentDB
import Order
import OrderStatusTracker

internal object DeliveryManager{

    fun assignOrders(){
        val ordersStatusTracker = OrderDB.filterOrdersStatusTrackerByLocation()
        val ordersStatusTrackerIterator = ordersStatusTracker.iterator()
        ordersStatusTrackerIterator.forEach {  DeliveryAgentDB.filterDeliveryAgentsByLocation(it.key) }

    }

}
