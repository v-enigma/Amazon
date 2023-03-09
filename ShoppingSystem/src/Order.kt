import enums.PaymentType
import java.time.LocalDate

 class Order internal constructor( // has to work on access specifiers
     val orderId:Int,
     val shippingAddress: Address,
     val orderedDate: LocalDate,
     val productsWithQuantity: List<ProductWithQuantity>,
     val paymentDetails : PaymentType,
     val total : Double
    ){
     var deliveredDate : LocalDate? = null
        internal set(date:LocalDate?){
            field = date
        }
     internal fun getLocation():Int{
         return this.shippingAddress.pinCode
     }
 }


