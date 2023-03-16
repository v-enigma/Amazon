package roles

import OrderStatusTracker
import data.DeliveryAgentDB
import factories.NotificationFactory
import data.OrderDB
import enums.DeliveryStage
import java.time.LocalDate

internal object DeliveryManager{ // has to rework on names

    fun assignOrders(){
        val ordersStatusTracker = OrderDB.filterOrdersStatusTrackerByLocation()
        val ordersStatusTrackerIterator = ordersStatusTracker.iterator()
        while(ordersStatusTrackerIterator.hasNext()){
            val currentOrderStatusTrackerPointer = ordersStatusTrackerIterator.next()
            val ordersStatusTrackersForALocation = currentOrderStatusTrackerPointer.value
            val deliveryAgentsAtALocation = DeliveryAgentDB.filterDeliveryAgentsByLocation(currentOrderStatusTrackerPointer.key)
            assignToDeliveryAgent(ordersStatusTrackersForALocation.iterator(), deliveryAgentsAtALocation)

        }

    }
    private fun findPotentialDeliveryAgent(deliveryAgentIds:List<Int>, date:LocalDate):Int{
       return  if(deliveryAgentIds.isNotEmpty()){
            var potentialId :Int = deliveryAgentIds[0]
            var deliveriesMade  = -1
            deliveryAgentIds.forEach{
                if(deliveriesMade == -1)
                  deliveriesMade = DeliveryAgentDB.deliveriesAssignedOnADate(it,date)
                else{
                    var currentDeliveriesMadeCount = DeliveryAgentDB.deliveriesAssignedOnADate(it,date)
                    if( currentDeliveriesMadeCount< deliveriesMade){
                        deliveriesMade = DeliveryAgentDB.deliveriesAssignedOnADate(it,date)
                        potentialId = it
                   }
                }
            }
           potentialId
        }
        else{
            -1
        }

    }
    private fun assignToDeliveryAgent(orderStatusTrackers: Iterator<OrderStatusTracker>, deliveryAgentIds:List<Int>){ //  has to rework on names
        while(orderStatusTrackers.hasNext()){
            val orderStatusTracker = orderStatusTrackers.next()
            val deliveryAgentId = findPotentialDeliveryAgent(deliveryAgentIds, LocalDate.now())
            if(deliveryAgentId != -1){
                 DeliveryAgentDB.updateDeliveriesAssignedToAgent(LocalDate.now(),1,deliveryAgentId,orderStatusTracker.orderId)
                 OrderDB.getOrderTrackerInStatusTracker(orderStatusTracker.orderId).updateDeliveryStatus(DeliveryStage.ASSIGNED_TO_AGENT)
                if(deliveryAgentId!= -1)
                    NotificationFactory.notifyCustomer("You ${orderStatusTracker.orderId} is assigned to Delivery Agent name: ${DeliveryAgentDB.getUser(deliveryAgentId).name} phoneNo: ${DeliveryAgentDB.getUser(deliveryAgentId).phoneNo} ", customerId = orderStatusTracker.customerId)
            }
        }
    }

}
