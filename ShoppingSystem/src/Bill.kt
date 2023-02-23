import java.time.LocalDate
 class Bill(
  val sellerName: String,
  val sellerAddress: Address,
  val customerName: String,
  val shippingAddress: Address,
  val productsWithQuantity: Map<Product, Int>,
  val offers:List<Offer>,

 ) {
  val totalAmount :Double
   get() = 0.0 // has to implement it





}