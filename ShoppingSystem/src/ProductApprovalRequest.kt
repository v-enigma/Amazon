class ProductApprovalRequest(
    val product: Product,
    val relationToProduct:RelationToProduct,
    //val isManufacturer:Boolean, // has to change the roles  productRelationShip
   // val isDealer:Boolean = false,
    var approvalRequestStatus : ProductApprovalStatus = ProductApprovalStatus.WAITING_APPROVAL
) {

    var hasManufacturerApproval: ManufacturerApproval = ManufacturerApproval.NOT_APPLICABLE
}