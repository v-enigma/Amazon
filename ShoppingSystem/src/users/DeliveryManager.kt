package users

import OrderStatusTracker

internal object DeliveryManager{
    private val ordersStatusTracker: MutableList<OrderStatusTracker> =  mutableListOf()
    //internal
    fun  addNewOrder(orderStatusTracker: OrderStatusTracker){
        ordersStatusTracker.add(orderStatusTracker)
    }
    fun assignOrders(){

    }

}
