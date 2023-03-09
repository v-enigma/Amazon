import enums.ManufacturerApproval
import enums.ProductCategory
import enums.RelationToProduct
import users.Seller

object ProductFactory {
    private var productId = 1
    fun createProduct(productName:String, price:Double, description:String, productCategory: ProductCategory, quantity:Int, seller: Seller, relationToProduct: RelationToProduct, manufacturerApproval: ManufacturerApproval?){
        val product = Product(productId++,productName,price,description,productCategory)
        seller.addProduct(product,quantity,relationToProduct,manufacturerApproval)

    }
}