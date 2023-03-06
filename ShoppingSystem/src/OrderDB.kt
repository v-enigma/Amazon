import java.time.LocalDate

internal object OrderDB {
    private val allOrders :MutableMap<Int, Order> = mutableMapOf()
    private val deliveriesMade: MutableMap<LocalDate, DeliveriesMadeByAgent > = mutableMapOf()
    internal fun addOrder(order:Order){
        allOrders[order.orderId] = order
    }

}
internal class DeliveriesMadeByAgent{

    private val deliveryAgentToDeliveriesMade :MutableMap<Int, Int> = mutableMapOf()
    init{
        DeliveryAgentDB.getAllDeliveryAgentsIds().forEach{ deliveryAgentToDeliveriesMade[it] = 0 }
    }
    internal fun updateDeliverCount(id:Int, count:Int){
        deliveryAgentToDeliveriesMade[id] = deliveryAgentToDeliveriesMade[id]?.plus(count) ?: 0
    }

}