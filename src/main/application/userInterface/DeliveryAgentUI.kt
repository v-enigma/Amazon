package userInterface

import enums.DeliveryStage
import enums.PaymentType
import factories.OrderFactory
import models.DeliveryAgent


class  DeliveryAgentUI(private val deliveryAgent: DeliveryAgent):UI {
     override fun menu() {
         val menuItems = """1->View Assigned Deliveries
             |2->Update Order Status
             |3->Exit
         """.trimMargin()
         println(menuItems)
         while (true) {
             println("Enter your option ")
             var input = InputHelper.getIntInputWithInRange(1, 3)
             when (input) {
                 1 -> viewAssignedDeliveries()
                 2 -> updateOrderStatus()
                 3 -> break
             }
         }
     }

    private fun viewAssignedDeliveries(){
        deliveryAgent.viewAssignedDeliveries()
    }
    private fun updatePaymentDetailsOfPackage(orderId:Int){
        OrderFactory.updatePaymentDetails(orderId)
    }
    private fun updateOrderStatus(){
        if(deliveryAgent.getDeliverables().isEmpty()){
            println("You don't have any items to deliver")
            return
        }
        println("Enter Id of the delivery you want to update the status")
        val id = InputHelper.getIntegerInput()
        if(deliveryAgent.getDeliverables().contains(id) &&
            (deliveryAgent.getCurrentStatusOfOrder(id) == DeliveryStage.OUT_FOR_DELIVERY||
                    deliveryAgent.getCurrentStatusOfOrder(id) == DeliveryStage.ASSIGNED_TO_AGENT))
        {
                    when(deliveryAgent.getCurrentStatusOfOrder(id)){
                        DeliveryStage.OUT_FOR_DELIVERY -> { println("Have you delivered the package.Enter yes or No");

                            val input = InputHelper.getYesOrNo();
                            if(input == "yes") {
                                if(OrderFactory.getPaymentType(id) == PaymentType.CASH_ON_DELIVERY)
                                    updatePaymentDetailsOfPackage(id)

                                deliveryAgent.updatePackageDeliveryStatus(id, DeliveryStage.DELIVERED)
                            }
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