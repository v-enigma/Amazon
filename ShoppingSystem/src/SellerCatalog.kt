import enums.ManufacturerApproval
import enums.ProductApprovalStatus
import enums.RelationToProduct
import factories.ProductDBFactory
import models.Product
import models.SellerCatalogComponent

internal class SellerCatalog (
    private val allProductsWithQuantity : MutableMap<Int, SellerCatalogComponent> = mutableMapOf()) {
    internal fun removeProduct(productId:Int) : Boolean{
       return if(allProductsWithQuantity.contains(productId)){
           allProductsWithQuantity.remove(productId)
           ProductDBFactory.removeProduct(productId)
            true
        }
        else{
            false
        }
    }
    internal fun addProduct(product: Product, availableQuantity:Int, relationToProduct: RelationToProduct, manufacturerApproval: ManufacturerApproval?):Boolean{
       return if(!allProductsWithQuantity.contains(product.id)){
            allProductsWithQuantity[product.id] = SellerCatalogComponent(
                product, availableQuantity,
                ProductApprovalStatus.WAITING_APPROVAL
            )
           ProductApprovalRequestHelper.addToApprovalWaitingList(
               ProductApprovalRequestHelper.createProductApprovalRequest(
                   product,
                   availableQuantity,
                   relationToProduct,
                   manufacturerApproval
               )
           )
           true
        }
        else{
            false
       }
    }
    internal fun getAllProducts():List<SellerCatalogComponent>{
        var productsList : MutableList<SellerCatalogComponent> = mutableListOf()
        allProductsWithQuantity.forEach{(_,component) -> productsList.add(component)}
        return productsList.toList()
    }
    internal fun incrementProductQuantity(productId:Int, quantity:Int = 1){
        if(allProductsWithQuantity.contains(productId)){
              allProductsWithQuantity.getValue(productId).availableQuantity = allProductsWithQuantity.getValue(productId).availableQuantity + quantity
        }
    }
    internal fun decrementProductQuantity(productId: Int,quantity: Int=1){
        if(allProductsWithQuantity.contains(productId) && allProductsWithQuantity.getValue(productId).availableQuantity - quantity >= 0){
            allProductsWithQuantity.getValue(productId).availableQuantity = (allProductsWithQuantity.getValue(productId).availableQuantity - quantity )
        }
        else if((allProductsWithQuantity.getValue(productId).availableQuantity - quantity) < 0)
            allProductsWithQuantity.getValue(productId).availableQuantity = 0
    }

}

