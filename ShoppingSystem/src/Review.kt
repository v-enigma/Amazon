import java.time.LocalDate

data class Review (
    val userId: String,
    var comment : String,
    var rating : Float ,
    val reviewDate : LocalDate){
}