package roles

import models.ApprovalRequest
import enums.ManufacturerApproval
import data.ProductApprovalRequestDB
import enums.ProductApprovalStatus
import data.ProductDB
import enums.RelationToProduct
import factories.CatalogItemWithApprovalRequestFactory
import models.CatalogItemWithApprovalRequest

internal object ProductApprover{

    internal fun evaluateProductApprovalRequests() {
        val productsWaitingForApproval = ProductApprovalRequestDB.getApprovalRequests()
        productsWaitingForApproval.forEach{
            if(canRequestBeApproved(it)){
                CatalogItemWithApprovalRequestFactory.updateStatus(it as CatalogItemWithApprovalRequest,ProductApprovalStatus.APPROVED)
                ProductDB.addProductToDB(it.productWithQuantity.product, it.productWithQuantity.quantity)
                ProductApprovalRequestDB.removeApprovedRequests(it.productWithQuantity.product.id)
            }
            else{
                CatalogItemWithApprovalRequestFactory.updateStatus(it as CatalogItemWithApprovalRequest,ProductApprovalStatus.DENIED)
            }

        }

    }

    private fun canRequestBeApproved(productApprovalRequest: ApprovalRequest):Boolean {
        return when (productApprovalRequest.relationToProduct) {
            RelationToProduct.MANUFACTURER -> {
                productApprovalRequest.status = ProductApprovalStatus.APPROVED
                true
            }
            RelationToProduct.DEALER -> {
                when(productApprovalRequest.hasManufacturerApproval) {
                    ManufacturerApproval.YES, ManufacturerApproval.NOT_APPLICABLE -> {
                        productApprovalRequest.status = ProductApprovalStatus.APPROVED; true
                    }

                    ManufacturerApproval.NO -> {
                        ProductApprovalStatus.DENIED;false
                    }
                }
            }

          }
    }


}