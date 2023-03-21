package models

import enums.ManufacturerApproval
import enums.ProductApprovalStatus
import enums.RelationToProduct

internal class CatalogItemWithApprovalRequest(
    override val productWithQuantity: ProductWithQuantity,
    override var status: ProductApprovalStatus,
    override val relationToProduct: RelationToProduct,


    override var hasManufacturerApproval: ManufacturerApproval
) : ApprovalRequest,SellerCatalogComponent {
}