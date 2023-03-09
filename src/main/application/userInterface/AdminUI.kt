package userInterface

import users.Admin

class AdminUI(private val admin: Admin) :UI{
    override fun menu(){
        val menuItems = """1.Look for orders waiting to be assigned for Delivery Agents 
            | 2. Look for Products waiting approval
            | 3.Exit
            
        """.trimMargin()

        while(true) {
            println(menuItems)
            var input = InputHelper.getIntInputWithInRange(1, 2)
            when (input) {
                1 -> assignOrders()
                2 -> productApproval()
                3 -> break
            }
        }

    }
    private fun notifyDeliveryAgents(){

    }
    private fun productApproval(){
        admin.approveSellerRequests()
    }
    private fun assignOrders(){
        admin.assignOrderToDeliveryAgents()
    }

}