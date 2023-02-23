class ProductApprovalRequest(
    val product: Product,
    val isManufacturer:Boolean,
    val isDealer:Boolean = false,
    var approvalRequestStatus : ProductApprovalStatus = ProductApprovalStatus.WAITING_APPROVAL
) {

    var hasManufacturerApproval: ManufacturerApproval = ManufacturerApproval.NOT_APPLICABLE
}