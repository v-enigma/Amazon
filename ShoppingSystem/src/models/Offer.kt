package models

import java.time.LocalDate

interface Offer {
     val description: String
     val percentageOff:Int
     fun isEligible(grossPrice:Double):Boolean
     fun applyOffer(grossPrice: Double): Double
}

class FestiveOffer(private val startDate : LocalDate, private val endDate :LocalDate, override val description: String, override val percentageOff :Int):
    Offer {
    override fun isEligible(grossPrice: Double):Boolean {

        return (LocalDate.now().isEqual(startDate) || LocalDate.now().isAfter(startDate)) && (LocalDate.now().isBefore(endDate) || LocalDate.now().isEqual(endDate)) && (grossPrice > 0)
    }

    override fun applyOffer(grossPrice: Double): Double {
        return if((LocalDate.now().isEqual(startDate) || LocalDate.now().isAfter(startDate)) && (LocalDate.now().isBefore(endDate) || LocalDate.now().isEqual(endDate)) )
            -1*(grossPrice*( percentageOff/100))
        else
            0.0
    }

}
class  MinimumPurchaseOffer(private val minimumAmount:Int, override val  description: String, override val percentageOff: Int): Offer {
    override fun isEligible(grossPrice: Double): Boolean {
        return (grossPrice >= minimumAmount)
    }
    override fun applyOffer(grossPrice: Double): Double {
       return if(grossPrice >= minimumAmount)
           -1*(grossPrice* 0.2)
        else 0.0
    }
}