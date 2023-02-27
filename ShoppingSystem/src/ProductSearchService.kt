internal class ProductSearchService {
    private val productKeyWordToProductIdMap: MutableMap<String,MutableList<String>> = mutableMapOf<String, MutableList<String>>().also{
        it["miscellaneous"] = mutableListOf()
    }
    internal fun searchMatchingProducts(keyWord:String):List<String>{
        return (productKeyWordToProductIdMap.getValue(keyWord)).toList()
    }
    internal fun addProductIdToAppropriateKeyWord(keyWord: String,productId:String){
        productKeyWordToProductIdMap[keyWord]?.add(productId)

    }

}
