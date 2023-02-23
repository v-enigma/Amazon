interface UsersDB{
   fun getUser(id:String): User
   fun addUser(user:User) ;

}
object CustomerDB: UsersDB{
     private val customersData :MutableMap<String,Customer> = mutableMapOf()
     override fun getUser(id:String) = customersData.getValue(id)
     override fun addUser(user: User) {
         val customer = user as Customer
         customersData[user.userId] = customer
     }

}
object SellerDB: UsersDB{
    private val sellersData : MutableMap<String,Seller> = mutableMapOf()
    override fun getUser(id:String) = sellersData.getValue(id)
    override fun addUser(user: User) {
        val seller = user as Seller
        sellersData[user.userId] = seller
    }
}
object AdminDB:UsersDB{
    override fun getUser(id: String) =  Admin.getInstance()
    override fun addUser(user: User) {

    }
}
object DeliveryAgentDB: UsersDB{
    private val deliveryAgentsData :MutableMap<String,DeliveryAgent> = mutableMapOf()
    override fun getUser(id: String)= deliveryAgentsData.getValue(id)
    override fun addUser(user: User) {
        val deliveryAgent = user as DeliveryAgent
        deliveryAgentsData[user.userId] = deliveryAgent
    }
}