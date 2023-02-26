internal class ProductApprover(val productsWaitingForApproval: MutableList<ProductApprovalRequest> = mutableListOf()) {

    fun moveToProductDB() {

    }

    internal fun evaluateProductApprovalRequests(productApprovalRequests: MutableList<ProductApprovalRequest>) {

    }

    internal fun evaluateRequest(productApprovalRequest: ProductApprovalRequest) {
        if (productApprovalRequest.isManufacturer) {
                productApprovalRequest.approvalRequestStatus = ProductApprovalStatus.APPROVED

        }else  if(productApprovalRequest.isDealer){
            when(productApprovalRequest.hasManufacturerApproval){
                ManufacturerApproval.YES , ManufacturerApproval.NOT_APPLICABLE  -> productApprovalRequest.approvalRequestStatus = ProductApprovalStatus.APPROVED
                ManufacturerApproval.NO -> ProductApprovalStatus.DENIED
            }
        }
    }
    internal fun addProductsToDatabase(products : MutableList<Product>){

    }
}