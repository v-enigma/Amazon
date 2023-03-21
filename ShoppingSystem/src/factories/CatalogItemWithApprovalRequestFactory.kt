package factories

import enums.ManufacturerApproval
import enums.ProductApprovalStatus
import enums.RelationToProduct
import models.CatalogItemWithApprovalRequest
import models.Product
import models.ProductWithQuantity

internal object CatalogItemWithApprovalRequestFactory {
    internal fun createCatalogItemWithApprovalRequestFactory(product: Product, quantity: Int,relationToProduct:RelationToProduct,manufacturerApproval:ManufacturerApproval):CatalogItemWithApprovalRequest{
        val productWithQuantity = ProductFactory.createProductWithQuantity(product, quantity)
        return CatalogItemWithApprovalRequest(productWithQuantity,ProductApprovalStatus.WAITING_APPROVAL,relationToProduct,manufacturerApproval )
    }
    internal fun updateStatus(catalogItemWithApprovalRequest: CatalogItemWithApprovalRequest,status: ProductApprovalStatus){
        catalogItemWithApprovalRequest.status = status
    }
}