import data.ProductApprovalRequestDB
import enums.ManufacturerApproval
import enums.RelationToProduct
import models.Product

internal object ProductApprovalRequestHelper {
    internal fun addToApprovalWaitingList(productApprovalRequest: ProductApprovalRequest){
        ProductApprovalRequestDB.addProductApprovalRequest(productApprovalRequest)
    }
    internal fun createProductApprovalRequest(product: Product, quantity:Int, relationToProduct: RelationToProduct, manufacturerApproval: ManufacturerApproval? = null):ProductApprovalRequest{

     val approvalRequest = ProductApprovalRequest(product,relationToProduct,quantity)
     if(manufacturerApproval != null) {
             approvalRequest.hasManufacturerApproval = manufacturerApproval
         }
        return approvalRequest
    }
}