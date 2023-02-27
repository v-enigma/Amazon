internal class ProductApprover(
   private val productsWaitingForApproval: MutableList<ProductApprovalRequest> = mutableListOf()
) {


    internal fun evaluateProductApprovalRequests() {
        productsWaitingForApproval.forEach{
            if(canRequestBeApproved(it)){
                ProductDB.addProductToDB(it.product)

            }
        }

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


}