package userInterface

import enums.OfferType
import factories.OfferFactory
import models.Admin
import java.time.LocalDate

class AdminUI(private val admin: Admin) :UI{
    override fun menu(){
        val menuItems = """1.Look for orders waiting to be assigned for Delivery Agents 
            | 2. Look for Products waiting approval
            | 3.Exit
            
        """.trimMargin()

        while(true) {
            println(menuItems)
            var input = InputHelper.getIntInputWithInRange(1, 4)
            when (input) {
                1 -> assignOrders()
                2 -> productApproval()
                3 -> createOffers()
                4 -> break
            }
        }

    }

    private fun productApproval(){
        admin.approveSellerRequests()
    }
    private fun assignOrders(){
        admin.assignOrderToDeliveryAgents()
    }
    private fun createOffers(){
        println("""Choose the offer type 
            |1-> Festive Offers
            |2-> Minimum purchase Offers
        """.trimMargin())
        val input = InputHelper.getIntInputWithInRange(1,2)
        println("Enter your offer description")
        val description = InputHelper.getStringInput()
        println("Enter discount percentage")
        val percentageOff = InputHelper.getIntInputWithInRange(1,100)
        var startDate : LocalDate? = null
        var endDate : LocalDate? = null
        var  minimumPurchase :Int? = null
        val offerType:OfferType
        if(input == 1){
            offerType = OfferType.FESTIVE_OFFER
            println("Enter start date and end date")
            startDate = LocalDate.parse(InputHelper.getDateInput())
            println("Enter end date ")
            endDate = LocalDate.parse(InputHelper.getDateInput())

        }
        else{
            offerType = OfferType.MINIMUM_PURCHASE_OFFER
            println("Enter minimum amount of purchase")
            minimumPurchase = InputHelper.getIntegerInput()
        }
        OfferFactory.handleNewOffer(offerType,startDate,endDate,minimumPurchase,description,percentageOff)

    }

}