package roles

import ProductApprovalRequest
import enums.ManufacturerApproval
import data.ProductApprovalRequestDB
import enums.ProductApprovalStatus
import data.ProductDB
import enums.RelationToProduct

internal object ProductApprover{

    internal fun evaluateProductApprovalRequests() {
        val productsWaitingForApproval = ProductApprovalRequestDB.getApprovalRequests()
        productsWaitingForApproval.forEach{
            if(canRequestBeApproved(it)){
                ProductDB.addProductToDB(it.product, it.quantity)
            }
        }
        ProductApprovalRequestDB.removeApprovedRequests()
    }

    private fun canRequestBeApproved(productApprovalRequest: ProductApprovalRequest):Boolean {
        return when (productApprovalRequest.relationToProduct) {
            RelationToProduct.MANUFACTURER -> {
                productApprovalRequest.approvalRequestStatus = ProductApprovalStatus.APPROVED
                true
            }
            RelationToProduct.DEALER -> {
                when(productApprovalRequest.hasManufacturerApproval) {
                    ManufacturerApproval.YES, ManufacturerApproval.NOT_APPLICABLE -> {
                        productApprovalRequest.approvalRequestStatus = ProductApprovalStatus.APPROVED; true
                    }

                    ManufacturerApproval.NO -> {
                        ProductApprovalStatus.DENIED;false
                    }
                }
            }

          }
    }


}