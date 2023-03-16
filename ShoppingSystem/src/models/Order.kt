package models

import enums.PaymentStatus
import enums.PaymentType
import java.time.LocalDate

 data class Order internal constructor( // has to work on access specifiers
     val orderId:Int,
     val shippingAddress: Address,
     val orderedDate: LocalDate,
     val productsWithQuantity: List<ProductWithQuantity>,
     private var paymentMode : PaymentType,
     private var paymentStatus : PaymentStatus,
     val total : Double
    ){
     var deliveredDate : LocalDate? = null
        internal set(date){
            field = date
        }
     internal fun getLocation():Int{
         return this.shippingAddress.pinCode
     }
     internal fun setPaymentStatus(status: PaymentStatus){
          paymentStatus = status
     }
     internal fun getPaymentStatus():PaymentStatus{
         return paymentStatus
     }
     internal fun setPaymentMode(modeOfPaymentType: PaymentType){
         paymentMode = modeOfPaymentType
     }
     internal fun getPaymentMode():PaymentType{
         return paymentMode
     }
 }


