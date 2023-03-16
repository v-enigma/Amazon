package factories

import models.Product
import data.ProductDB
import enums.ProductCategory

internal object ProductDBFactory {
    internal fun removeProduct(productId:Int):Boolean{
        return ProductDB.removeProductFromDB(productId)
    }
    internal fun findMatchingProducts(keyWord: String, productCategory: ProductCategory):List<Product>{
        return ProductDB.findMatchingProducts(keyWord,productCategory)
    }

}