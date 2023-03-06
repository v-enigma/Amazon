 data class Product(
     val id:Int,
     val name:String,
     val price:Double,
     val description:String,
     val category: ProductCategory,
     val reviews :MutableList<Review> = mutableListOf()){
     private var _rating : Float = 0.0F
     val rating :Float = _rating
     fun updateRating(){
        var ratingsSum = 0.0F
        for(review in reviews){
            ratingsSum+=review.rating
        }
        _rating = ratingsSum/reviews.size
    }

}