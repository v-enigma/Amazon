package userInterface

import enums.Role
import factories.UserCreationHelper


object Application {
    fun start(){
        run()
    }
    init{
        //println("I am inside")
        val  details = listOf("USERNAME","1998-01-12","6234567890","ABcd@1234")
        val emailId = "dummy@gmail.com"
        val address1 = listOf("16-16-144/1","Dummy","Dummy","Dummy","Dummy","603202")
        UserCreationHelper.createUser(Role.CUSTOMER,details, null,address1)
        UserCreationHelper.createUser(Role.SELLER,details,null,address1)
        UserCreationHelper.createUser(Role.DELIVERY_AGENT,details,emailId,address1, 603202)
        //println("I have added ")
    }

    private fun run(){
        println("--------------------Welcome to Amazon--------------------")

        while(true) {
            println("1.Sign In \n2.Sign Up \n3.Exit\n")
            when (InputHelper.getIntInputWithInRange(1, 3)) {
                1 -> signIn()
                2 -> signUp()
                3 -> break
            }
        }
    }
    private fun signIn(){
        val userRoleInfo = "Enter your role\n1 -> Customer\n2 -> Seller\n3 -> DeliveryAgent\n4 -> Admin"
        println(userRoleInfo)
        when(InputHelper.getIntInputWithInRange(1,4,"Please $userRoleInfo")){
            1 ->  (UIFactory.getUIObject(Role.CUSTOMER) as? CustomerUI )?.menu()

            2 -> (UIFactory.getUIObject(Role.SELLER) as? SellerUI)?.menu()

            3 -> (UIFactory.getUIObject(Role.DELIVERY_AGENT) as? DeliveryAgentUI )?.menu()

            4 -> (UIFactory.getUIObject(Role.ADMIN) as? AdminUI)?.menu()
        }

    }
    private fun signUp(){
        val userRoleInfo = "Enter your role\n1 -> Customer\n2 -> Seller\n3 -> DeliveryAgent "
        println(userRoleInfo)
        when(InputHelper.getIntInputWithInRange(1,3, "Please $userRoleInfo")){
            1 -> {
                val details = getUserDetails(Role.CUSTOMER)
                val emailId = InputHelper.getEmail()
                val address = InputHelper.getAddress()
                UserCreationHelper.createUser(Role.CUSTOMER,details,emailId,address)
            }
            2 -> {
                val sDetails = getUserDetails(Role.SELLER)
                val emailId = InputHelper.getEmail()
                println("Enter your registered Address")
                val address = InputHelper.getAddress()
                UserCreationHelper.createUser(Role.SELLER,  sDetails,emailId,address)
            }
            3 ->{
                val dDetails = getUserDetails(Role.DELIVERY_AGENT)
                val emailId = InputHelper.getEmail()
                println("Enter pin code of the serving location")
                val pinCode = InputHelper.getIntegerInput()

                UserCreationHelper.createUser(Role.DELIVERY_AGENT,dDetails,emailId, pinCode = pinCode)
            }
        }
    }
    private fun getUserDetails(role: Role):List<String>{
        println("Enter phone number")
        var phoneNo = InputHelper.getPhoneNo()
        phoneNo = UIFactory.validatePhoneNoOrEmailExistence(phoneNo,role)
        println("Enter password")
        val password = InputHelper.getPassword()
        println(" Enter user Name")
        val name = InputHelper.getStringInput()

        println("Enter dateOfBirth")
        val dateOfBirth = InputHelper.getDateInput()
        return listOf(name,dateOfBirth,phoneNo,password)
    }




}