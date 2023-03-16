package data

import AuthenticationException
import enums.Role
import models.Customer
import models.DeliveryAgent
import models.Seller
import models.User
import models.Admin

/*data class UserCredential(val phoneNo: String , var password: String){

}*/
internal object AuthenticationData {
    private val customerPhoneNumberPasswordMap :MutableMap<String,String> = mutableMapOf()
    private val customerEmailPhoneNumberMap :MutableMap<String, String> = mutableMapOf()
    private val customerPhoneToID : MutableMap<String,Int> = mutableMapOf()

    private val sellerPhoneNumberPasswordMap :MutableMap<String,String> = mutableMapOf()
    private val sellerEmailPhoneNumberMap :MutableMap<String,String> = mutableMapOf()
    private val sellerPhoneNumberToID : MutableMap<String,Int> = mutableMapOf()

   /* private val AdminPhoneNumberPasswordMap :MutableMap<String,String> = mutableMapOf()
    private val AdminEmailPhoneNumberMap :MutableMap<String,String> = mutableMapOf()
    private val AdminPhoneNumberToID : MutableMap<String,String> = mutableMapOf()*/
   private val adminLoginCredentials : Pair<Int,String> = Pair(1,"PASSS")

    private val deliveryAgentPhoneNumberPasswordMap :MutableMap<String,String> = mutableMapOf()
    private val deliveryAgentEmailPhoneNumberMap :MutableMap<String,String> = mutableMapOf()
    private val deliveryAgentPhoneToID : MutableMap<String,Int> = mutableMapOf()
    private  fun getUser(credentialMap: MutableMap<String,String>, phoneIdMap:MutableMap<String,Int>,phoneNo:String, password: String, userDB : UsersDB): User?{
     return   if(credentialMap.contains(phoneNo) && credentialMap.getValue(phoneNo) == password)
            userDB.getUser(phoneIdMap.getValue(phoneNo))
        else
            null
    }
    internal fun customerAuthentication(emailOrPhoneNo :String, password:String): Customer?{
        return if(customerPhoneNumberPasswordMap.contains(emailOrPhoneNo)){
             getUser(customerPhoneNumberPasswordMap, customerPhoneToID,emailOrPhoneNo,password,CustomerDB) as? Customer
        }
        else if( customerEmailPhoneNumberMap.contains(emailOrPhoneNo)){
            val phoneNo = customerEmailPhoneNumberMap.getValue(emailOrPhoneNo)
            getUser(customerPhoneNumberPasswordMap, customerPhoneToID,phoneNo, password,CustomerDB) as? Customer
        }
        else
            null
    }


    internal fun adminAuthentication(userId:Int, password:String): Admin {
       return  if(adminLoginCredentials.first == userId && adminLoginCredentials.second == password)
            AdminDB.getUser(userId)
        else
           throw AuthenticationException("Invalid Credentials")
    }
    internal fun sellerAuthentication(emailOrPhoneNo:String, password:String): Seller {
        return if(sellerPhoneNumberPasswordMap.contains(emailOrPhoneNo)){
            getUser(sellerPhoneNumberPasswordMap, sellerPhoneNumberToID, emailOrPhoneNo, password, SellerDB) as Seller
        }
        else if(sellerEmailPhoneNumberMap.contains(emailOrPhoneNo)){
            val phoneNo = sellerEmailPhoneNumberMap.getValue(emailOrPhoneNo)
            getUser(sellerPhoneNumberPasswordMap, sellerPhoneNumberToID, phoneNo,password,SellerDB) as Seller
        }
        else{
            throw AuthenticationException("Invalid Credentials")
        }
    }
    internal fun deliveryAgentAuthentication(emailOrPhoneNo:String, password:String): DeliveryAgent {
        return if(deliveryAgentPhoneNumberPasswordMap.contains((emailOrPhoneNo))){
            getUser(deliveryAgentEmailPhoneNumberMap, deliveryAgentPhoneToID, emailOrPhoneNo, password,DeliveryAgentDB) as DeliveryAgent
        }
        else if(deliveryAgentEmailPhoneNumberMap.contains(emailOrPhoneNo)){
            val phoneNo = deliveryAgentEmailPhoneNumberMap.getValue(emailOrPhoneNo)
            getUser(deliveryAgentPhoneNumberPasswordMap,deliveryAgentPhoneToID, phoneNo, password,DeliveryAgentDB) as DeliveryAgent
        }
        else
            throw AuthenticationException("Invalid Credentials")
    }
    internal fun checkPhoneNoExistence(phoneNo: String, role: Role):Boolean{
        return when(role){
            Role.ADMIN -> true
            Role.CUSTOMER -> customerPhoneNumberPasswordMap.contains(phoneNo)
            Role.SELLER -> sellerPhoneNumberPasswordMap.contains(phoneNo)
            Role.DELIVERY_AGENT -> deliveryAgentPhoneNumberPasswordMap.contains(phoneNo)
        }

    }
    internal fun addDetailsForAuthentication(role: Role, user: User, password: String){
        when(role){
             Role.CUSTOMER ->
                 addToAuthenticationMaps(password, user,customerPhoneNumberPasswordMap, customerEmailPhoneNumberMap, customerPhoneToID)
             Role.SELLER ->
                 addToAuthenticationMaps(password,user, sellerPhoneNumberPasswordMap, sellerEmailPhoneNumberMap, sellerPhoneNumberToID)
            Role.DELIVERY_AGENT ->
                addToAuthenticationMaps(password,user, deliveryAgentPhoneNumberPasswordMap, deliveryAgentEmailPhoneNumberMap, deliveryAgentPhoneToID)
            Role.ADMIN -> {}
        }
    }
    private fun addToAuthenticationMaps(password: String, user: User, phoneNoToPassword:MutableMap<String,String>,
                                        emailToPhoneNo:MutableMap<String,String>, phoneNoToID :MutableMap<String,Int>){
        phoneNoToPassword[user.phoneNo] = password
        if(user.emailId!= null)
            emailToPhoneNo[user.emailId!!] = user.phoneNo
        phoneNoToID[user.phoneNo] = user.userId

    }

}