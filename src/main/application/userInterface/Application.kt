package userInterface

import enums.Role
import factories.UserCreationHelper

object Application {
    fun start(){
        run()
    }

    private fun run(){
        println("--------------------Welcome to Amazon--------------------")
        var loop = true
        while(loop) {
            println("1.Sign In \n2.Sign Up \n3.Exit\n")
            when (InputHelper.getIntInputWithInRange(1, 3)) {
                1 -> signIn()
                2 -> signUp()
                3 -> loop = false
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