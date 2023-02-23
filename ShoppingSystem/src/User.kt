import java.time.LocalDate

sealed  class User(
    val userId:String,
    var name: String,
    var emailId: String,
    var dateOfBirth: LocalDate,
    var phoneNo :String){

}