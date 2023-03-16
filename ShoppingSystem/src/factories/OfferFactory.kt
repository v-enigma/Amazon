package factories

import models.FestiveOffer
import models.MinimumPurchaseOffer
import models.Offer
import data.OffersData
import enums.OfferType
import java.time.LocalDate

object  OfferFactory {
    private fun createOffer(type: OfferType, startDate: LocalDate? , endDate: LocalDate? , minimumPurchase:Int?, description: String, percentageOff :Int   ): Offer {
        return when (type){
                OfferType.FESTIVE_OFFER -> FestiveOffer(startDate!!,endDate!!, description, percentageOff)
                OfferType.MINIMUM_PURCHASE_OFFER -> MinimumPurchaseOffer(minimumPurchase!!, description,percentageOff)
        }
    }
    fun handleNewOffer(type: OfferType, startDate: LocalDate? , endDate: LocalDate? , minimumPurchase:Int?, description: String, percentageOff :Int ){
        val offer = createOffer(type,startDate,endDate,minimumPurchase,description,percentageOff)
        OffersData.addOffer(offer)
    }
    internal fun findEligibleOffers(grossPrice:Double):List<Offer>{
        val eligibleOffers = mutableListOf<Offer>()
        OffersData.getOffers().forEach {
            if(it.isEligible(grossPrice))
                eligibleOffers.add(it)
        }
        return eligibleOffers.toList()
    }
}