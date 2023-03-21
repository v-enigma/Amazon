package data

import models.ApprovalRequest


object ProductApprovalRequestDB {
    private val productsWaitingForApproval: MutableMap<Int, ApprovalRequest> = mutableMapOf()
    internal fun  addProductApprovalRequest(productApprovalRequest: ApprovalRequest){
        productsWaitingForApproval[productApprovalRequest.productWithQuantity.product.id] = productApprovalRequest
    }
    internal fun getApprovalRequests():List<ApprovalRequest>{
        val approvalRequest: MutableList<ApprovalRequest>  = mutableListOf()
        productsWaitingForApproval.forEach { approvalRequest.add(it.value)}
        return approvalRequest.toList()
    }
    internal fun removeApprovedRequests(id:Int):Boolean{
        return if(productsWaitingForApproval.contains(id)) {

            productsWaitingForApproval.remove(id)
            true
        }
              else
                  false
    }
    internal fun contains(productId:Int):Boolean{
        return (productsWaitingForApproval.containsKey(productId))
    }
    internal fun updateQuantityInTheRequest(productId: Int, quantity: Int){
        if(productsWaitingForApproval.contains(productId)){
            productsWaitingForApproval.getValue(productId).productWithQuantity.quantity = productsWaitingForApproval.getValue(productId).productWithQuantity.quantity + quantity
        }
    }



}