internal class ProductSearchService {
    private val productKeyWordToProductIdMap: MutableMap<String,MutableList<String>> = mutableMapOf<String, MutableList<String>>().also{
        it["miscellaneous"] = mutableListOf()
    }

}
