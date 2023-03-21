package models

import enums.ManufacturerApproval
import enums.ProductApprovalStatus
import enums.RelationToProduct

interface ApprovalRequest{
    val productWithQuantity : ProductWithQuantity
    val relationToProduct: RelationToProduct
    var status : ProductApprovalStatus
    var hasManufacturerApproval: ManufacturerApproval
}
