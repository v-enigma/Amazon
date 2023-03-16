package data

import models.Product
import ProductSearchService
import models.ProductWithQuantity
import enums.ProductCategory

internal object ProductDB {
    private val allProducts :MutableMap<Int, ProductWithQuantity> = mutableMapOf()
    private val searchHelper : MutableMap<ProductCategory, ProductSearchService> = mutableMapOf<ProductCategory, ProductSearchService>().apply{
        ProductCategory.values().forEach{ category -> this[category] = ProductSearchService() }

    }
    // has to write a method to find keywords from miscellaneous and update keywords
    private val categoryWiseAvailableKeyWords :MutableMap<ProductCategory,MutableList<String>> = mutableMapOf<ProductCategory,MutableList<String>>().apply{
        ProductCategory.values().forEach { category -> this[category] = mutableListOf("miscellaneous") }
    }

    internal fun addProductToDB(product: Product, quantity: Int){
        if(!allProducts.contains(product.id)){
            allProducts[product.id] = ProductWithQuantity(product, quantity)
            searchHelper.getValue(product.category).addProductIdToAppropriateKeyWord("miscellaneous",product.id)
        }
    }
    internal fun getProduct(id:Int): Product {
        return allProducts.getValue(id).product
    }
    internal fun removeProductFromDB(productId:Int):Boolean{
        return if(allProducts.contains(productId)){
            allProducts.remove(productId)
            true
        }
        else
            false
    }
    internal fun findMatchingProducts(keyWord:String, category: ProductCategory):List<Product> {
        var searchKeyWord = keyWord
        val hasMatchingKeyword = categoryWiseAvailableKeyWords[category]?.contains(keyWord) ?: false
        if(!hasMatchingKeyword)
             searchKeyWord = "miscellaneous"
        val searchResults: MutableList<Product> = mutableListOf()

            searchHelper.getValue(category).searchMatchingProducts(searchKeyWord).let { productIds ->
                productIds.forEach {
                    val productWithQuantity = allProducts[it]
                    if (productWithQuantity != null) {
                        if(keyWord == "miscellaneous"){
                            if((productWithQuantity.product.description.contains(keyWord)|| productWithQuantity.product.name.contains(keyWord) )&& productWithQuantity.quantity > 0)
                                searchResults.add(productWithQuantity.product)
                        }
                        else {
                            searchResults.add(productWithQuantity.product)
                        }
                    }
                }
                return searchResults.toList()
            }


    }
    internal fun getAvailableQuantity(productId: Int):Int{
        return allProducts.getValue(productId).quantity
    }
    internal fun incrementProductQuantity(productId: Int,quantity: Int){
        allProducts.getValue(productId).quantity = allProducts.getValue(productId).quantity + quantity
    }
    internal fun decrementProductQuantity(productId: Int,quantity: Int){
        allProducts.getValue(productId).quantity = allProducts.getValue(productId).quantity -quantity
    }



}