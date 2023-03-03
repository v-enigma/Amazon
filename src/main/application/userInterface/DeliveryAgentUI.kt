package userInterface

import users.DeliveryAgent


class  DeliveryAgentUI(deliveryAgent: DeliveryAgent):UI {
     override fun menu() {
         val menuItems = """1->View Notifications
             |2->Collect Order
             |3->Update Order Status
             |4->Exit
         """.trimMargin()
         println(menuItems)
         var loop = true
         var input = InputHelper.getIntInputWithInRange(1, 4)
         while (loop) {
             when (input) {
                 1 -> viewNotifications()
                 2 -> collectOrder()
                 3 -> updateOrderStatus()
                 4 -> loop = false
             }
         }
     }
    private fun viewNotifications(){

    }
    private fun collectOrder(){

    }
    private fun updateOrderStatus(){

    }


}