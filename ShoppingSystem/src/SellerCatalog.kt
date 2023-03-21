import enums.ManufacturerApproval
import enums.ProductApprovalStatus
import enums.RelationToProduct
import factories.ProductFactory
import models.Product
import models.ProductWithQuantity
import models.SellerCatalogComponent

internal class SellerCatalog (
    private val allProductsWithQuantity : MutableMap<Int, SellerCatalogComponent> = mutableMapOf()) {
    internal fun removeProduct(productId:Int) : Boolean{
       return if(allProductsWithQuantity.contains(productId)){
           allProductsWithQuantity.remove(productId)
           ProductFactory.removeProduct(productId)
            true
        }
        else{
            false
        }
    }
    internal fun addProduct(sellerCatalogComponent: SellerCatalogComponent):Boolean{
       return if(!allProductsWithQuantity.contains(sellerCatalogComponent.productWithQuantity.product.id)){
            allProductsWithQuantity[sellerCatalogComponent.productWithQuantity.product.id] = sellerCatalogComponent


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
    internal fun incrementProductQuantity(productId:Int, quantity:Int){
        if(allProductsWithQuantity.contains(productId)){
            allProductsWithQuantity.getValue(productId).productWithQuantity.quantity= allProductsWithQuantity.getValue(productId).productWithQuantity.quantity + quantity
            ProductFactory.incrementProductQuantity(productId, quantity)
        }

    }
    internal fun decrementProductQuantity(productId: Int,quantity: Int=1){
        if(allProductsWithQuantity.contains(productId) && allProductsWithQuantity.getValue(productId).productWithQuantity.quantity - quantity >= 0){
            allProductsWithQuantity.getValue(productId).productWithQuantity.quantity = (allProductsWithQuantity.getValue(productId).productWithQuantity.quantity - quantity )
        }
        else if((allProductsWithQuantity.getValue(productId).productWithQuantity.quantity - quantity) < 0)
            allProductsWithQuantity.getValue(productId).productWithQuantity.quantity= 0
    }

}

