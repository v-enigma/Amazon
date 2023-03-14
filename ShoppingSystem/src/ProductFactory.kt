import enums.ManufacturerApproval
import enums.ProductCategory
import enums.RelationToProduct
import users.Seller
import java.time.LocalDate

object ProductFactory {
    private var productId = 1
    fun createProduct(productName:String, price:Double, description:String, productCategory: ProductCategory, quantity:Int, seller: Seller, relationToProduct: RelationToProduct, manufacturerApproval: ManufacturerApproval?){
        val product = Product(productId++,productName,price,description,productCategory)
        seller.addProduct(product,quantity,relationToProduct,manufacturerApproval)

    }
    private fun createReview(userId:Int, userName: String, comment: String?, rating: Float, reviewedDate : LocalDate ):Review{
         return Review(userId,userName, comment, rating, reviewedDate)
    }
    fun reviewProduct(userId:Int, userName: String, comment: String? = null, rating: Float, reviewedDate : LocalDate ,productId:Int) {
        val product = ProductDB.getProduct(productId)
        product.addReview(createReview(userId, userName, comment,rating,reviewedDate))
        product.updateRating()
    }
}