package factories

import models.OrderStatusTracker
import data.*
import data.CustomerDB
import data.DeliveryAgentDB
import data.OrderDB
import enums.DeliveryStage
import enums.PaymentStatus
import enums.PaymentType
import models.*
import java.time.LocalDate

object OrderFactory {
    private var orderCount = 0
    internal fun handleOrder(allProducts:List<ProductWithQuantity>, address: List<String>, paymentType: PaymentType,paymentStatus:PaymentStatus, grossPrice:Double, userId:Int){
        val orderId = generateOrderId()
        val shippingAddress = Address(
            address.component1(), address.component2(),
            address.component3(), address.component4(),
            address.component5(), address.last().toInt()
        )
        val netPrice = handleInvoicesAndGetDiscountedPrice(grossPrice, allProducts,orderId,userId,shippingAddress)
        val order = Order(
            orderId,
            shippingAddress, LocalDate.now(), allProducts, paymentType, paymentStatus, netPrice
        )
        updateProductQuantity(allProducts)
        OrderDB.addOrder(order)
        OrderDB.addOrderStatusTracker(OrderStatusTracker(orderId, userId, DeliveryStage.RECEIVED_ORDER))
        CustomerDB.getUser(userId).addOrder(order)
    }
    private fun generateOrderId():Int{
        return ++orderCount
    }
     fun checkPossibilityOfDelivery(pinCode:Int):Boolean{
        return (DeliveryAgentDB.filterDeliveryAgentsByLocation(pinCode).isNotEmpty())
    }
    fun getOrderShippingAddressOfOrder(orderId:Int):Address{
        return OrderDB.getShippingAddressOfOrder(orderId)
    }
    fun  isOrderEligibleForCancellation(orderId: Int):Boolean{

        return when(OrderDB.getOrderStatus(orderId)){
             DeliveryStage.RECEIVED_ORDER -> true
             else -> false
        }
    }
    private fun updateProductQuantity(allProducts:List<ProductWithQuantity>){
        allProducts.forEach{
            ProductFactory.decreaseProductQuantity(it.product.id, it.quantity)
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
    private fun generateBills(grossPrice:Double, allProducts: List<ProductWithQuantity>,orderId:Int,userId:Int,shippingAddress: Address): List<Bill>{
        val bills :MutableList<Bill> = mutableListOf()
        val eligibleOffers = OfferFactory.findEligibleOffers(grossPrice)
        val productGroupBySellerId = allProducts.groupBy { it.product.sellerId }
        productGroupBySellerId.forEach{
           val sellerAddress  = SellerDB.getUser(it.key).registeredAddress
           val sellerName = SellerDB.getUser(it.key).name
           val customerName = CustomerDB.getUser(userId).name
           val bill =createBill(orderId,sellerName,sellerAddress,customerName,shippingAddress,it.value,eligibleOffers)
           bills.add(bill)
        }
       return bills
    }
   private fun handleInvoicesAndGetDiscountedPrice(grossPrice: Double, allProducts: List<ProductWithQuantity>, orderId: Int,userId: Int,shippingAddress: Address):Double{
       var bills = generateBills(grossPrice,allProducts,orderId,userId,shippingAddress)
       BillsStorage.addBillsForAnOrder(orderId, bills)
       var discountedPrice = 0.0
       bills.forEach{
           discountedPrice += it.getNetPrice()
       }
      return discountedPrice
   }
    fun getBills(orderId:Int):List<Bill>{
      return BillsStorage.getBillsForAnOrder(orderId)
   }
   private fun createBill(id:Int,sellerName: String,sellerAddress: Address,customerName:String, shippingAddress: Address,productsWithQuantity: List<ProductWithQuantity>, offers:List<Offer>):Bill{
     return Bill(id,sellerName,sellerAddress,customerName,shippingAddress,productsWithQuantity,offers)
   }

}