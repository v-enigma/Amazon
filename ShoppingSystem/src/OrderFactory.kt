import enums.DeliveryStage
import enums.PaymentType
import users.Admin
import java.time.LocalDate

object OrderFactory {
    private var orderCount = 0
    internal fun handleOrder(allProducts:List<ProductWithQuantity>, shippingAddress: List<String>, paymentType: PaymentType, total:Double,userId:Int){
        val id = generateOrderId()
        OrderDB.addOrder(Order(id ,
            Address(shippingAddress.component1(),shippingAddress.component2(),
                shippingAddress.component3(),shippingAddress.component4(),
                shippingAddress.component5(),shippingAddress.last().toInt()), LocalDate.now(),allProducts ,paymentType, total))
        OrderDB.addOrderStatusTracker(OrderStatusTracker(id,userId, DeliveryStage.RECEIVED_ORDER))
    }
    private fun generateOrderId():Int{
        return ++orderCount
    }
     fun checkPossibilityOfDelivery(pinCode:Int):Boolean{
        return (DeliveryAgentDB.filterDeliveryAgentsByLocation(pinCode).isNotEmpty())
    }
    fun getOrderShippingAddressOfOrder(orderId:Int){
        OrderDB.getShippingAddressOfOrder(orderId)
    }

}