import userInterface.Application
import java.time.LocalDate

fun main(vararg args: String){
    //Application.start()
    var kk = TestingDataClass("Venu", 21)
    println(kk)


}
fun getRandomString(length: Int) : String {
    val allowedChars = ('A'..'Z') +  ('0'..'9')
    return (1..length)
        .map { allowedChars.random() }
        .joinToString("")
}

