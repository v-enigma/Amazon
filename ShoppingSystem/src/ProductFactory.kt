import users.Seller

object ProductFactory {
    private var productId = 1
    fun createProduct(productName:String, price:Double, description:String,productCategory: ProductCategory,quantity:Int,seller: Seller){
        val product = Product(productId++,productName,price,description,productCategory)
        seller.addProduct(product,quantity)

    }
}