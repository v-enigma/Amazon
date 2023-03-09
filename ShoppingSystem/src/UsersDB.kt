import users.Admin
import users.Customer
import users.Seller
import users.User
import users.DeliveryAgent

internal interface UsersDB {
    fun getUser(id: Int): User
    fun addUser(user: User)


}

internal object CustomerDB : UsersDB {
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
    override fun getUser(id: Int) = deliveryAgentsData.getValue(id)
    override fun addUser(user: User) {
        val deliveryAgent = user as DeliveryAgent
        deliveryAgentsData[user.userId] = deliveryAgent
    }
    internal fun getAllDeliveryAgentsIds():List<Int>{
        return deliveryAgentsData.keys.toList()
    }
    internal fun filterDeliveryAgentsByLocation(locationId:Int):List<Int>{
        val deliveryAgentIds = mutableListOf<Int>()
        deliveryAgentsData.forEach{ if(it.value.pinCode == locationId)
            deliveryAgentIds.add(it.key)
        }
        return deliveryAgentIds.toList()
    }
}