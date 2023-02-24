import java.time.LocalDate

object AuthenticationHelper {
    fun customerAuthentication(userId:String, password:String):Customer{
       return AuthenticationData.customerAuthentication(userId, password)?: throw AuthenticationException("Invalid Credentials")
    }
    fun adminAuthentication(userId:String, password:String):Admin{
        return AuthenticationData.adminAuthentication(userId, password) ?: throw AuthenticationException("Invalid Credentials")
    }
    fun sellerAuthentication(userId:String, password:String):Seller{
        return AuthenticationData.sellerAuthentication(userId, password) ?: throw AuthenticationException("Invalid Credentials")
    }
    fun deliveryAgentAuthentication(userId:String, password:String):DeliveryAgent{
        return AuthenticationData.deliveryAgentAuthentication(userId, password) ?: throw AuthenticationException("Invalid Credentials")
    }
    fun isDuplicatePhoneNo(phoneNo:String, role: Role):Boolean{
       return AuthenticationData.checkPhoneNoExistence(phoneNo,role)
    }
}
object  UserCreationHelper{

    fun createUser(role : Role ,userDetails:List<String>,address:List<String> = listOf() ,pinCode: Int = 0){
        when(role){
            Role.ADMIN -> {}
            Role.SELLER -> { val sellerId = "1"
                Seller(sellerId, userDetails.component1(),userDetails.component2(),LocalDate.parse(userDetails.component3()),userDetails.component4(),  )}
            Role.DELIVERY_AGENT -> {
                val deliveryAgentId = "1"
                DeliveryAgent(deliveryAgentId,userDetails.component1(),userDetails.component2(),LocalDate.parse(userDetails.component3()),userDetails.component4(), pinCode  )
            }
            Role.CUSTOMER -> {
                val customerId = "1"
                Customer(customerId,userDetails.component1(),userDetails.component2(),LocalDate.parse(userDetails.component3()),userDetails.component4(),
                    Address(address.component1(), address.component2(), address.component3(),
                        address.component4(), address.component5(),address.last().toInt()))
            }
        }

    }
}