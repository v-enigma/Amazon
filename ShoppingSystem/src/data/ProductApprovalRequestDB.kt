package data

import ProductApprovalRequest
import enums.ProductApprovalStatus

object ProductApprovalRequestDB {
    private val productsWaitingForApproval: MutableList<ProductApprovalRequest> = mutableListOf()
    internal fun  addProductApprovalRequest(productApprovalRequest: ProductApprovalRequest){
        productsWaitingForApproval.add(productApprovalRequest)
    }
    internal fun getApprovalRequests():List<ProductApprovalRequest>{
        return productsWaitingForApproval.toList()
    }
    internal fun removeApprovedRequests(){
        val productApprovalRequestsIterator = productsWaitingForApproval.iterator()
        productApprovalRequestsIterator.forEach{  if(it.approvalRequestStatus == ProductApprovalStatus.APPROVED) {
            productApprovalRequestsIterator.remove()
            }
        }
    }

}