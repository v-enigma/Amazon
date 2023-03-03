package users

import java.time.LocalDate

 sealed  class User(
    val userId:Int,
    var name: String,
    var emailId: String,
    var dateOfBirth: LocalDate,
    var phoneNo :String){

}