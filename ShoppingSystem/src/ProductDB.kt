internal object ProductDB {
    private val allProducts :MutableMap<String, Product> = mutableMapOf()
    private val searchHelper : MutableMap<ProductCategory,ProductSearchService> = mutableMapOf<ProductCategory,ProductSearchService>().apply{
        ProductCategory.values().forEach{category -> this[category] = ProductSearchService() }

    }

    internal fun addProductToDB(product:Product){
        if(!allProducts.contains(product.id)){
            allProducts[product.id] = product
        }
    }
    internal fun removeProductFromDB(productId:String){
        if(allProducts.contains(productId)){
            allProducts.remove(productId)
        }
    }

}