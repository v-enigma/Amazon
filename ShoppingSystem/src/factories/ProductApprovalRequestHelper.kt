package factories

import data.ProductApprovalRequestDB
import enums.ManufacturerApproval
import enums.ProductApprovalStatus
import enums.RelationToProduct
import models.Product
import models.ApprovalRequest

internal object ProductApprovalRequestHelper {
    internal fun addToApprovalWaitingList(productApprovalRequest: ApprovalRequest) {
        ProductApprovalRequestDB.addProductApprovalRequest(productApprovalRequest)
    }


}