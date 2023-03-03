import java.time.LocalDate

 class Order internal constructor(
    val orderId:String,
    val shippingAddress: Address,
    val orderedDate: LocalDate,
     val productsWithQuantity: List<ProductWithQuantity>,
    val paymentDetails : PaymentType,
    val deliveredDate : LocalDate,
    val total : Double
    )


