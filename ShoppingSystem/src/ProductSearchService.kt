internal class ProductSearchService {
    private val productKeyWordToProductIdMap: MutableMap<String,MutableList<Int>> = mutableMapOf<String, MutableList<Int>>().also{
        it["miscellaneous"] = mutableListOf()
    }
    internal fun searchMatchingProducts(keyWord:String):List<Int>{
        return (productKeyWordToProductIdMap.getValue(keyWord)).toList()
    }
    internal fun addProductIdToAppropriateKeyWord(keyWord: String,productId:Int){
        productKeyWordToProductIdMap[keyWord]?.add(productId)

    }

}
