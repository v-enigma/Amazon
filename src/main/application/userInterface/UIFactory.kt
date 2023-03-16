package userInterface
import AuthenticationException
import factories.AuthenticationHelper
import models.Customer
import models.DeliveryAgent
import enums.Role
import models.User
import models.Seller

object UIFactory {
    fun getUIObject(role: Role):UI?{
        val loginCred = getLoginCredentials()

        return when(role){
            Role.ADMIN ->  getUIForRole(loginCred, AuthenticationHelper::adminAuthentication)
            Role.SELLER -> getUIForRole(loginCred, AuthenticationHelper::sellerAuthentication)
            Role.DELIVERY_AGENT ->  getUIForRole(loginCred, AuthenticationHelper::deliveryAgentAuthentication)
            Role.CUSTOMER -> getUIForRole(loginCred, AuthenticationHelper::customerAuthentication)
        }
    }
    private fun getUIForRole(loginCredentials: Pair<String,String>, authMethod:(String,String)-> User?):UI?{
        return try{
            when(val authResult = authMethod(loginCredentials.first,loginCredentials.second)) {
                is Customer -> CustomerUI(authResult)
                is Seller -> SellerUI(authResult)
                is DeliveryAgent -> DeliveryAgentUI(authResult)
                else -> null
            }
        }catch (authenticationException :AuthenticationException){
            println(authenticationException.message)
            null
        }

    }

    private fun getLoginCredentials():Pair<String,String>{
        println("Enter EmailId or phoneNo")
        var emailOrPhoneNo = InputHelper.getEmailOrPhoneNo();
        println("Enter password")
        var password = InputHelper.getPassword()
        return Pair(emailOrPhoneNo,password)

    }

    fun validatePhoneNoOrEmailExistence( emailOrPhoneNo: String, role: Role):String{
        var phone = emailOrPhoneNo
        while(AuthenticationHelper.isDuplicatePhoneNo(phone, role)) {
            println("Account exist with this phoneNumber.Please enter another phoneNumber")
            phone = InputHelper.getEmailOrPhoneNo()
        }
        return phone
    }

}