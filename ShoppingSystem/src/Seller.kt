import java.time.LocalDate

 class Seller(
     sellerId: Int,
     name: String,
     emailId: String,
     dateOfBirth: LocalDate,
     phoneNo: String
 ) : User(sellerId,name,emailId,dateOfBirth,phoneNo){
     val catalog  = Catalog()
     val pickUpAddresses: MutableList<Address> = mutableListOf()

}
