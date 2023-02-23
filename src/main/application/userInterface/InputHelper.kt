package userInterface

import java.time.LocalDate
import java.util.regex.Pattern

object InputHelper {

    internal fun getIntegerInput():Int{
        var userInput :Int  = try{
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
        var value : Int = getIntegerInput()
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
        var input = readln()
        if(!Pattern.matches(passwordPattern,input)){
                println("""SHOULD CONTAIN AT LEAST 8 CHARACTERS LONG.
                    |SHOULD CONTAIN CAPITAL LETTER , SMALL CASE LETTERS, SPECIAL CHARACTERS AND NUMBERS.
                    |SHOULD NOT CONTAIN WHITE SPACES """.trimMargin())
                return getPassword()
        }
        return input
    }
    internal fun getPhoneNo( ):String{
        val phonePattern = "[6-9][0-9]{9}";
        var input = readln()
        if(!Pattern.matches(phonePattern,input)){
            println("Please enter 10 digit phone number ")
            return getPhoneNo()
        }
        return input

    }
    internal fun getEmailOrPhoneNo():String{
        var input = readln()
        val emailPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"
        val phonePattern =  "[6-9][0-9]{9}";
        if(!Pattern.matches(emailPattern,input) &&  !Pattern.matches(phonePattern,input)){
            println("Please enter valid ")
            return getEmailOrPhoneNo()
        }
        return input
    }
    internal fun getDateInput():String {
        var input = readln()
        val datePattern = "^[1-9][0-9]{3}-(([0-1][1-2])|0[1-9])-(([0-2][1-9])|3[01])$"
        if (!Pattern.matches(datePattern, input)) {
            return getDateInput()
        }
        return input
    }
    internal fun getAddress():List<String>{
        println("Enter Building No")
        var buildingNo = readln()
        println("Enter Street Name")
        var streetNo = readln()
        println("Enter locality")
        var locality = readln()
        println("Enter city")
        var city = readln()
        println("Enter state")
        var state = readln()
        println("Enter pinCode")
        val pinCode = getIntegerInput()
        var sPinCode  = pinCode.toString()
       return listOf(buildingNo,streetNo,locality,city,state,sPinCode)
    }

}
