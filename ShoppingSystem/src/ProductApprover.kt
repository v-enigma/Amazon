internal class ProductApprover(
    val productsWaitingForApproval: MutableList<ProductApprovalRequest> = mutableListOf()
) {

    private fun moveToProductDB(product: Product) {

    }

    internal fun evaluateProductApprovalRequests(productApprovalRequests: MutableList<ProductApprovalRequest>) {
        productApprovalRequests.forEach(::canRequestBeApproved)

    }

    private fun canRequestBeApproved(productApprovalRequest: ProductApprovalRequest):Boolean {
        if (productApprovalRequest.isManufacturer) {
                productApprovalRequest.approvalRequestStatus = ProductApprovalStatus.APPROVED
                true

        }else  if(productApprovalRequest.isDealer){
            when(productApprovalRequest.hasManufacturerApproval){
                ManufacturerApproval.YES , ManufacturerApproval.NOT_APPLICABLE  -> {productApprovalRequest.approvalRequestStatus = ProductApprovalStatus.APPROVED; true}
                ManufacturerApproval.NO -> {ProductApprovalStatus.DENIED; false}
            }
        }
        return false
    }
    private fun evaluateRequest(productApprovalRequest: ProductApprovalRequest){
        if(canRequestBeApproved(productApprovalRequest))
            moveToProductDB(productApprovalRequest.product)
    }

}