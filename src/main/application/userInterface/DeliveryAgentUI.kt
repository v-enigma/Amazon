package userInterface

import enums.DeliveryStage
import users.DeliveryAgent


class  DeliveryAgentUI(private val deliveryAgent: DeliveryAgent):UI {
     override fun menu() {
         val menuItems = """1->View Notifications
             |2->View Assigned Deliveries
             |3->Update Order Status
             |4->Exit
         """.trimMargin()
         println(menuItems)
         while (true) {
             var input = InputHelper.getIntInputWithInRange(1, 4)
             when (input) {
                 1 -> viewNotifications()
                 2 -> viewAssignedDeliveries()
                 3 -> updateOrderStatus()
                 4 -> break
             }
         }
     }
    private fun viewNotifications(){

    }
    private fun viewAssignedDeliveries(){
        deliveryAgent.viewAssignedDeliveries()
    }
    private fun updateOrderStatus(){
        println("Enter Id of the delivery you want to update the status")
        val id = InputHelper.getIntegerInput()
        if(deliveryAgent.getDeliverables().contains(id) &&
            (deliveryAgent.getCurrentStatusOfOrder(id) == DeliveryStage.OUT_FOR_DELIVERY||
                    deliveryAgent.getCurrentStatusOfOrder(id) == DeliveryStage.ASSIGNED_TO_AGENT))
        {
                    when(deliveryAgent.getCurrentStatusOfOrder(id)){
                        DeliveryStage.OUT_FOR_DELIVERY -> { println("Have you delivered the order.Enter yes or No");
                            val input = InputHelper.getYesOrNo();
                            if(input == "yes")
                                deliveryAgent.updatePackageDeliveryStatus(id, DeliveryStage.DELIVERED)
                            else
                                println("You can update the  status of current package only after delivery")

                        }
                        DeliveryStage.ASSIGNED_TO_AGENT ->{ println("Do you want to deliver the package today?");
                            val input = InputHelper.getYesOrNo();
                            if(input == "yes")
                                deliveryAgent.updatePackageDeliveryStatus(id, DeliveryStage.OUT_FOR_DELIVERY)

                            else
                                println("At the current stage you can  only update on the delivery day")

                        }
                        else -> println(" Not possible to update the status of the order")
                    }
            }
        else{
            println("Order Status is InValid  or Invalid order Id ")
        }



    }


}