package userInterface

fun main(){
    val numbers :List<Int> = listOf<Int>(1,2,3,4,4)
       with(numbers){
           checkEven(this[1])
       }


}
fun checkEven( number:Int){
    if((number%2) ==0)
    {
        println("I am even number $number")
    }

}