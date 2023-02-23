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
object  userCreationHelper{

    fun createUser(role : Role ,userDetails:List<String>, address: List<String>? = null, pinCode: Int? = null){
        when(role){
            Role.ADMIN -> {}
            Role.SELLER -> {}
            Role.DELIVERY_AGENT -> {}
            Role.CUSTOMER -> {}
        }

    }
}