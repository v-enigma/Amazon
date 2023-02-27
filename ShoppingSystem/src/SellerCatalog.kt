internal class SellerCatalog (
    private val allProductsWithQuantity : MutableMap<String, SellerCatalogComponent> = mutableMapOf()) {
    internal fun removeProduct(productId:String){
        if(allProductsWithQuantity.contains(productId)){
            allProductsWithQuantity.remove(productId)
        }
    }
    internal fun addProduct(product: Product,availableQuantity:Int){
        if(!allProductsWithQuantity.contains(product.id)){
            allProductsWithQuantity[product.id] = SellerCatalogComponent(product,availableQuantity,ProductApprovalStatus.WAITING_APPROVAL);
        }
    }
    internal fun getAllProducts():List<SellerCatalogComponent>{
        var productsList : MutableList<SellerCatalogComponent> = mutableListOf()
        allProductsWithQuantity.forEach{(_,component) -> productsList.add(component)}
        return productsList.toList()
    }
    internal fun incrementProductQuantity(productId:String, quantity:Int = 1){
        if(allProductsWithQuantity.contains(productId)){
              allProductsWithQuantity.getValue(productId).availableQuantity = allProductsWithQuantity.getValue(productId).availableQuantity + quantity
        }
    }
    internal fun decrementProductQuantity(productId: String,quantity: Int=1){
        if(allProductsWithQuantity.contains(productId) && allProductsWithQuantity.getValue(productId).availableQuantity - quantity >= 0){
            allProductsWithQuantity.getValue(productId).availableQuantity = (allProductsWithQuantity.getValue(productId).availableQuantity - quantity )
        }
        else if((allProductsWithQuantity.getValue(productId).availableQuantity - quantity) < 0)
            allProductsWithQuantity.getValue(productId).availableQuantity = 0
    }

}

