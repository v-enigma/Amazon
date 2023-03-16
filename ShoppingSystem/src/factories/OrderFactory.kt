package factories

import models.Address
import models.Order
import OrderStatusTracker
import data.*
import data.CustomerDB
import data.DeliveryAgentDB
import data.OrderDB
import models.ProductWithQuantity
import enums.DeliveryStage
import enums.PaymentStatus
import enums.PaymentType
import models.Bill
import java.time.LocalDate

object OrderFactory {
    private var orderCount = 0
    internal fun handleOrder(allProducts:List<ProductWithQuantity>, shippingAddress: List<String>, paymentType: PaymentType,paymentStatus:PaymentStatus, grossPrice:Double, userId:Int){
        val id = generateOrderId()
        val netPrice = handleInvoicesAndGetDiscountedPrice(grossPrice)
        val order = Order(
            id,
            Address(
                shippingAddress.component1(), shippingAddress.component2(),
                shippingAddress.component3(), shippingAddress.component4(),
                shippingAddress.component5(), shippingAddress.last().toInt()
            ), LocalDate.now(), allProducts, paymentType, paymentStatus, netPrice
        )
        OrderDB.addOrder(order)
        OrderDB.addOrderStatusTracker(OrderStatusTracker(id, userId, DeliveryStage.RECEIVED_ORDER))
        CustomerDB.getUser(userId).addOrder(order)
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
    fun  isOrderEligibleForCancellation(orderId: Int):Boolean{

        return when(OrderDB.getOrderStatus(orderId)){
             DeliveryStage.RECEIVED_ORDER -> true
             else -> false
        }
    }
    fun updateOrderStatus(orderStatusTrackerId: Int,status : DeliveryStage){
        OrderDB.updateStatus(orderStatusTrackerId,status)
    }
    fun cancelOrder(orderId:Int){
       OrderDB.updateStatus(orderId,DeliveryStage.CANCELLED )
       OrderDB.moveToCancelledMap(orderId)
    }
    fun getPaymentType(orderId: Int):PaymentType{
        return OrderDB.getOrder(orderId).getPaymentMode()
    }
    fun updatePaymentDetails(orderId:Int){
        OrderDB.getOrder(orderId).setPaymentStatus(PaymentStatus.PAID)

    }
    private fun generateBills(grossPrice:Double): List<Bill>{
        val eligibleOffers = OfferFactory.findEligibleOffers(grossPrice)


    }
   private fun handleInvoicesAndGetDiscountedPrice(grossPrice: Double):Double{
       var bills = generateBills(grossPrice)

   }
    fun getBills(orderId:Int):List<Bill>{
      return BillsStorage.getBillsForAnOrder(orderId)
   }

}