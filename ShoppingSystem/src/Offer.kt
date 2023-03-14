import jdk.jfr.Description
import java.time.LocalDate

abstract class Offer {
    abstract val description: String
    abstract fun isEligible(grossPrice:Double):Boolean
    abstract fun applyOffer(grossPrice: Double): Double
}

abstract class FestiveOffer(protected val startDate : LocalDate, protected val endDate :LocalDate, description: String ,private val percentageOff :Int):Offer( ){
    override val description = description



    override fun applyOffer(grossPrice: Double): Double {
        return if((LocalDate.now().isEqual(startDate) || LocalDate.now().isAfter(startDate)) && (LocalDate.now().isBefore(endDate) || LocalDate.now().isEqual(endDate)) )
            -1*(grossPrice*( percentageOff/100))
        else
            0.0
    }

}
object  DiwaliOffer : FestiveOffer(LocalDate.parse("2023-11-06"), LocalDate.parse("2023-11-12")," Flat 10% off on all purchases. No minimum purchase", 10){
    override fun isEligible(grossPrice: Double):Boolean {

     return (LocalDate.now().isEqual(startDate) || LocalDate.now().isAfter(startDate)) && (LocalDate.now().isBefore(endDate) || LocalDate.now().isEqual(endDate)) && (grossPrice > 0)
    }
}


object  ClearanceSale:Offer(){
    override val description = " Flat 20% off on min purchase of 1000"
    override fun isEligible(grossPrice: Double): Boolean {
        return (grossPrice>= 1000)
    }
    override fun applyOffer(grossPrice: Double): Double {
       return if(grossPrice>= 1000)
           -1*(grossPrice* 0.2)

        else 0.0

    }
}