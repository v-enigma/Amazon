internal object ProductDB {
    private val allProducts :MutableMap<String, Product> = mutableMapOf()
    private val searchHelper : MutableMap<ProductCategory,ProductSearchService> = mutableMapOf<ProductCategory,ProductSearchService>().apply{
        ProductCategory.values().forEach{category -> this[category] = ProductSearchService() }

    }
    // has to write a method to find keywords from miscellaneous and update keywords
    private val categoryWiseAvailableKeyWords :MutableMap<ProductCategory,MutableList<String>> = mutableMapOf<ProductCategory,MutableList<String>>().apply{
        ProductCategory.values().forEach { category -> this[category] = mutableListOf("miscellaneous") }
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
    internal fun findMatchingProducts(keyWord:String, category: ProductCategory):List<Product> {
        var searchKeyWord = keyWord
        var hasMatchingKeyword = categoryWiseAvailableKeyWords[category]?.contains(keyWord) ?: false
        if(!hasMatchingKeyword)
             searchKeyWord = "miscellaneous"
        val searchResults: MutableList<Product> = mutableListOf()

            searchHelper.getValue(category).searchMatchingProducts(searchKeyWord).let { productIds ->
                productIds.forEach {
                    var product = allProducts[it]
                    if (product != null) {
                        if(keyWord == "miscellaneous"){
                            if(product.description.contains(keyWord)|| product.name.contains(keyWord))
                                searchResults.add(product)
                        }
                        else {
                            searchResults.add(product)
                        }
                    }
                }
                return searchResults.toList()
            }


    }



}