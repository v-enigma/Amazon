package data

import models.Offer

object OffersData{
    private val availableOffers :MutableList<Offer> = mutableListOf()
    internal fun getOffers():List<Offer>{
        return availableOffers.toList()
    }
    internal fun addOffer(offer: Offer){
        availableOffers.add(offer)
    }
    internal fun remove(offer: Offer){
        availableOffers.remove(offer)
    }
}