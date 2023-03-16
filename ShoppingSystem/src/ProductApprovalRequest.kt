import enums.ManufacturerApproval
import enums.ProductApprovalStatus
import enums.RelationToProduct
import models.Product

class ProductApprovalRequest(
    val product: Product,
    val relationToProduct: RelationToProduct,
    val quantity: Int,
    var approvalRequestStatus : ProductApprovalStatus = ProductApprovalStatus.WAITING_APPROVAL
) {

    var hasManufacturerApproval: ManufacturerApproval = ManufacturerApproval.NOT_APPLICABLE
}