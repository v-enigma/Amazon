package userInterface
import  Product
import ProductWithQuantity

import java.util.regex.Pattern

object InputHelper {

    internal fun getIntegerInput():Int{
        val userInput :Int  = try{
             readln().toInt()
        }catch(exception : NumberFormatException){
            println("Please enter only Integer value ")
            getIntegerInput()
        }
      return userInput
    }
    internal fun getIntInputWithInRange(start :Int, end :Int, input :String? = null):Int{
        var userPrompt = input
        if(userPrompt == null)
            userPrompt =  "Enter the value in range[$start - $end] inclusively"
        val value : Int = getIntegerInput()
        if(value  !in start..end){
            println(userPrompt)
            return getIntInputWithInRange(start,end,userPrompt)
        }
        return value

    }
    internal fun getStringInput():String{
     return readln()
    }
    internal fun getEmail(){

    }
    internal fun getPassword():String{
        val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$"
        val input = readln()
        if(!Pattern.matches(passwordPattern,input)){
                println("""SHOULD CONTAIN AT LEAST 8 CHARACTERS LONG.
                    |SHOULD CONTAIN CAPITAL LETTER , SMALL CASE LETTERS, SPECIAL CHARACTERS AND NUMBERS.
                    |SHOULD NOT CONTAIN WHITE SPACES """.trimMargin())
                return getPassword()
        }
        return input
    }
    internal fun getPhoneNo( ):String{
        val phonePattern = "[6-9][0-9]{9}"
        val input = readln()
        if(!Pattern.matches(phonePattern,input)){
            println("Please enter 10 digit phone number ")
            return getPhoneNo()
        }
        return input

    }
    internal fun getEmailOrPhoneNo():String{
        val input = readln()
        val emailPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"
        val phonePattern =  "[6-9][0-9]{9}"
        if(!Pattern.matches(emailPattern,input) &&  !Pattern.matches(phonePattern,input)){
            println("Please enter valid ")
            return getEmailOrPhoneNo()
        }
        return input
    }
    internal fun getDateInput():String {
        val input = readln()
        val datePattern = "^[1-9][0-9]{3}-(([0-1][1-2])|0[1-9])-(([0-2][1-9])|3[01])$"
        if (!Pattern.matches(datePattern, input)) {
            return getDateInput()
        }
        return input
    }
    internal fun getDouble():Double{
        val input = readln()
        val doublePattern = "^[0-9]+\\.?[0-9]{0,14}\$"
        if(!Pattern.matches(doublePattern,input)){
            return getDouble()
        }
        return input.toDouble()
    }
    internal fun getAddress():List<String>{
        println("Enter Building No")
        val buildingNo = readln()
        println("Enter Street Name")
        val streetNo = readln()
        println("Enter locality")
        val locality = readln()
        println("Enter city")
        val city = readln()
        println("Enter state")
        val state = readln()
        println("Enter pinCode")
        val pinCode = getIntegerInput()
        val sPinCode  = pinCode.toString()
       return listOf(buildingNo,streetNo,locality,city,state,sPinCode)
    }
    internal fun printProduct(product:Product, index:Int,quantity: Int = 0 ){
        println("""$index. ${product.name} ${product.price} ${product.description}  ${if(quantity > 0) quantity else ""} """)


    }


}
