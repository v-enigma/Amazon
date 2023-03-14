import enums.ProductCategory

data class Product(
    val id:Int,
    val name:String,
    val price:Double,
    val description:String,
    val category: ProductCategory,
    private val reviews :MutableList<Review> = mutableListOf()){
     private var _rating : Float = 0.0F
     val rating :Float = _rating
     internal fun updateRating(){
        var ratingsSum = 0.0F
           if(reviews.size > 0 )
            ratingsSum+= (reviews[reviews.size-1].rating+ _rating)

        _rating = ratingsSum/2
    }
    internal fun addReview(review: Review){
        reviews.add(review)
    }

}