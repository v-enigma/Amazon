class ProductApprovalRequest(
    val product: Product,
    val relationToProduct:RelationToProduct,
    var approvalRequestStatus : ProductApprovalStatus = ProductApprovalStatus.WAITING_APPROVAL
) {

    var hasManufacturerApproval: ManufacturerApproval = ManufacturerApproval.NOT_APPLICABLE
}