import java.time.LocalDate

class Order(
    val orderId:String,
    val shippingAddress: Address,
    val orderedDate: LocalDate,
    val productsWithQuantity : Map<Product, Int> = mapOf(),
    val paymentDetails : PaymentType,
    val deliveredDate : LocalDate
    ){


}