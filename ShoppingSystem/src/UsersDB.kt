interface UsersDB{
    fun getUser(id:Int): User
    fun addUser(user:User) ;

}
object CustomerDB: UsersDB{
     private val customersData :MutableMap<Int,Customer> = mutableMapOf()
     override fun getUser(id:Int) = customersData.getValue(id)
     override fun addUser(user: User) {
         val customer = user as Customer
         customersData[user.userId] = customer
     }

}
object SellerDB: UsersDB{
    private val sellersData : MutableMap<Int,Seller> = mutableMapOf()
    override fun getUser(id:Int) = sellersData.getValue(id)
    override fun addUser(user: User) {
        val seller = user as Seller
        sellersData[user.userId] = seller
    }
}
object AdminDB:UsersDB{
    override fun getUser(id: Int) =  Admin.getInstance()
    override fun addUser(user: User) {

    }
}
object DeliveryAgentDB: UsersDB{
    private val deliveryAgentsData :MutableMap<Int,DeliveryAgent> = mutableMapOf()
    override fun getUser(id: Int)= deliveryAgentsData.getValue(id)
    override fun addUser(user: User) {
        val deliveryAgent = user as DeliveryAgent
        deliveryAgentsData[user.userId] = deliveryAgent
    }
}