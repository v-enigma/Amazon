package factories

import data.ProductApprovalRequestDB
import data.ProductDB
import data.SellerDB
import enums.ManufacturerApproval
import enums.ProductCategory
import enums.RelationToProduct
import models.*
import java.time.LocalDate

object ProductFactory {
    private var productId = 1
    fun createProduct(productName:String, price:Double, description:String, productCategory: ProductCategory, quantity:Int, seller: Seller, relationToProduct: RelationToProduct, manufacturerApproval: ManufacturerApproval){
        val product = Product(productId++, productName, price, description, productCategory,seller.userId)
        val catalogItem = CatalogItemWithApprovalRequestFactory.createCatalogItemWithApprovalRequestFactory(product,quantity,relationToProduct,manufacturerApproval)
        seller.addProduct(catalogItem as SellerCatalogComponent)
        ProductApprovalRequestHelper.addToApprovalWaitingList(catalogItem as ApprovalRequest)

    }
    private fun createReview(userId:Int, userName: String, comment: String?, rating: Float, reviewedDate : LocalDate ): Review {
         return Review(userId, userName, comment, rating, reviewedDate)
    }
    fun reviewProduct(userId:Int, userName: String, comment: String? = null, rating: Float, reviewedDate : LocalDate ,productId:Int) {
        val product = ProductDB.getProduct(productId)
        product.addReview(createReview(userId, userName, comment,rating,reviewedDate))
        product.updateRating()
    }
    fun getProductWIthQuantity(product: Product, quantity: Int):ProductWithQuantity{
        return ProductWithQuantity(product, quantity)
    }
    internal fun removeProduct(productId:Int):Boolean{
       return if(ProductApprovalRequestDB.contains(productId))
            ProductApprovalRequestDB.removeApprovedRequests(productId)

        else
            ProductDB.removeProductFromDB(productId)
    }
    internal fun findMatchingProducts(keyWord: String, productCategory: ProductCategory):List<Product>{
        return ProductDB.findMatchingProducts(keyWord,productCategory)
    }
    fun validateQuantityExistence(product:Product,quantity: Int ):Int {
        val availableQuantity = ProductDB.getAvailableQuantity(product.id)
        return if ( availableQuantity >= quantity)
            quantity
        else
            availableQuantity

    }
    internal fun decreaseProductQuantity(productId: Int,quantity: Int){
        ProductDB.decrementProductQuantity(productId, quantity)
        SellerDB.getUser(ProductDB.getProduct(productId).sellerId).decreaseExistingProductQuantity(productId, quantity)

    }
    fun incrementProductQuantity(productId:Int, quantity: Int){
        if(ProductApprovalRequestDB.contains(productId)){
            ProductApprovalRequestDB.updateQuantityInTheRequest(productId,quantity)
        }
        else
            ProductDB.incrementProductQuantity(productId, quantity)
    }
    fun createProductWithQuantity(product: Product, quantity: Int):ProductWithQuantity{
        return ProductWithQuantity(product, quantity)
    }

    }
