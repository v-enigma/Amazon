package models

import enums.ProductApprovalStatus
import models.Product

interface SellerCatalogComponent{
    val productWithQuantity: ProductWithQuantity
    var status : ProductApprovalStatus

}



