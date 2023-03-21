package models

import kotlin.properties.Delegates

data class Bill( // has to work on access modifier  of the class
    private val billId :Int,
    private val sellerName: String,
    private val sellerAddress: Address,
    private val customerName: String,
    private val shippingAddress: Address,
    private val productsWithQuantity: List<ProductWithQuantity>,
    private val offers:List<Offer>

    ) {
    internal fun getNetPrice():Double{
       var price = 0.0
       productsWithQuantity.forEach{
        price +=(it.product.price * it.quantity)

       }
       offers.forEach{
        price = it.applyOffer(price)
       }
      return price
     }

    override fun toString(): String {
        val sb = StringBuilder()
        sb.appendLine("Bill ID: $billId")
        sb.appendLine("Seller: $sellerName")
        sb.appendLine("Seller Address: $sellerAddress")
        sb.appendLine("Customer: $customerName")
        sb.appendLine("Shipping Address: $shippingAddress")
        sb.appendLine()
        sb.appendLine("Products:")
        var grossPrice = 0.0
        productsWithQuantity.forEach { pwq ->
         grossPrice+=(pwq.product.price * pwq.quantity)
         sb.appendLine("${pwq.product.name} x ${pwq.quantity}: ${pwq.product.price * pwq.quantity}")
        }
        sb.appendLine( "Gross Price : $grossPrice")
        sb.appendLine()
        sb.appendLine("Offers:")
        offers.forEach { offer ->
         sb.appendLine("${offer.description}: -${offer.applyOffer(grossPrice)}")
        }
        sb.appendLine()
        sb.appendLine("Total: ${getNetPrice()}")
        return sb.toString()
        }
    }
