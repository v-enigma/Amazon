package data

import models.Bill

internal object  BillsStorage {
    private val billsForAnOrder :MutableMap<Int, List<Bill>> = mutableMapOf() // orderId is acting as key
    internal fun getBillsForAnOrder(orderId:Int):List<Bill>{
        return billsForAnOrder.getValue(orderId).toList()
    }
    internal fun addBillsForAnOrder(orderId: Int, bills:List<Bill>){
        if(!billsForAnOrder.contains(orderId)){
            billsForAnOrder[orderId] = bills
        }
    }
}