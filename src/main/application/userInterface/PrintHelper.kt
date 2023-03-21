package userInterface

import models.Product
import models.SellerCatalogComponent

object PrintHelper {
    internal fun printProduct(product: Product, index:Int, quantity: Int = 0 ){
        println("""$index. ${product.name} ${product.price} ${product.description} ${product.rating}  ${if(quantity > 0) quantity else ""} """)

    }
    internal fun printProduct(sellerCatalogComponent: SellerCatalogComponent,index:Int){
        println("""$index. Product Name : ${sellerCatalogComponent.productWithQuantity.product.name}
            |       Description : ${sellerCatalogComponent.productWithQuantity.product.description}
            |       Rating : ${sellerCatalogComponent.productWithQuantity.product.rating}
            |       Approval Status : ${sellerCatalogComponent.status}
            |       Quantity : ${sellerCatalogComponent.productWithQuantity.quantity}
            |       Price : ${sellerCatalogComponent.productWithQuantity.product.price}
        """.trimMargin())
    }
}