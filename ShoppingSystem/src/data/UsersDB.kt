package data

import models.Admin
import models.Customer
import models.Seller
import models.User
import models.DeliveryAgent
import java.time.LocalDate

internal interface UsersDB {
    fun getUser(id: Int): User
    fun addUser(user: User)

}

internal object CustomerDB : UsersDB{

    private val customersData: MutableMap<Int, Customer> = mutableMapOf()
    override fun getUser(id: Int) = customersData.getValue(id)
    override fun addUser(user: User) {
        val customer = user as Customer
        customersData[user.userId] = customer
    }

}

internal object SellerDB : UsersDB {
    private val sellersData: MutableMap<Int, Seller> = mutableMapOf()
    override fun getUser(id: Int) = sellersData.getValue(id)
    override fun addUser(user: User) {
        val seller = user as Seller
        sellersData[user.userId] = seller
    }
}

internal object AdminDB : UsersDB {
    override fun getUser(id: Int) = Admin
    override fun addUser(user: User) {

    }
}

internal object DeliveryAgentDB : UsersDB {
    private val deliveryAgentsData: MutableMap<Int, DeliveryAgent> = mutableMapOf()
    private val deliveryAgentsAtALocation : MutableMap<Int ,MutableList<Int>> = mutableMapOf()
    private val deliveriesAssigned: MutableMap<LocalDate, DeliveriesMadeByAgent> = mutableMapOf()
    //private val deliveryAgentsByLocationWithDeliveriesMade : MutableMap<LocalDate, MutableMap<Int,MutableMap<Int, MutableList<Int>> >> = mutableMapOf()
    override fun getUser(id: Int) = deliveryAgentsData.getValue(id)
    override fun addUser(user: User) {
        val deliveryAgent = user as DeliveryAgent
        deliveryAgentsData[user.userId] = deliveryAgent

        if (deliveryAgentsAtALocation.contains(deliveryAgent.pinCode)) {
            deliveryAgentsAtALocation.getValue(deliveryAgent.pinCode).add(deliveryAgent.userId)

        } else {
            deliveryAgentsAtALocation[deliveryAgent.pinCode] = mutableListOf(deliveryAgent.userId)
        }

        //val  dummy =   deliveryAgentsAtALocation[deliveryAgent.pinCode]?.add(deliveryAgent.userId)?: deliveryAgentsAtALocation.put(deliveryAgent.pinCode ,mutableListOf(deliveryAgent.userId))
    }
    internal fun removeAgent(agentId: Int){

        deliveryAgentsAtALocation.getValue(deliveryAgentsData.getValue(agentId).pinCode).remove(agentId)
        deliveryAgentsData.remove(agentId)
    }
    internal fun updateDeliveriesAssignedToAgent(date:LocalDate, count:Int,agentId:Int, orderId:Int){
        if(deliveriesAssigned.contains(date)){
            deliveriesAssigned.getValue(date).updateDeliveriesCount(agentId, count)
            deliveryAgentsData.getValue(agentId).deliverables.add(orderId)
        }else{
            deliveriesAssigned[date] = DeliveriesMadeByAgent()
        }
    }
    internal fun getAllDeliveryAgentsIds():List<Int>{
        return deliveryAgentsData.keys.toList()
    }
    internal fun filterDeliveryAgentsByLocation(locationId:Int):List<Int> {

        var deliveryAgentIds: MutableList<Int> = try {
            deliveryAgentsAtALocation.getValue(locationId)
        } catch (exception: NoSuchElementException) {

            mutableListOf()
        }
        return deliveryAgentIds.toList()
    }
    internal fun deliveriesAssignedOnADate(deliveryAgentId:Int,date :LocalDate ):Int{
      return   if(deliveriesAssigned.contains(date)){
            deliveriesAssigned.getValue(date).deliveriesMade(deliveryAgentId)
        }else{
            0
      }

    }
}
internal class DeliveriesMadeByAgent{

    private val deliveryAgentToDeliveriesMadeOrAssigned :MutableMap<Int, Int> = mutableMapOf()
    init{
        DeliveryAgentDB.getAllDeliveryAgentsIds().forEach{ deliveryAgentToDeliveriesMadeOrAssigned[it] = 0 }
    }
    internal fun updateDeliveriesCount(id:Int, count:Int){
        deliveryAgentToDeliveriesMadeOrAssigned[id] = deliveryAgentToDeliveriesMadeOrAssigned[id]?.plus(count) ?: count
    }
    internal fun deliveriesMade(deliveryAgentId: Int):Int{
        return deliveryAgentToDeliveriesMadeOrAssigned[deliveryAgentId] ?: 0
    }
    internal fun removeAgent(agentId:Int){

        deliveryAgentToDeliveriesMadeOrAssigned.remove(agentId)
    }

}