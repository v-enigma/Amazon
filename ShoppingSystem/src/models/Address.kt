package models

data class Address(
    private val buildingNo :String,
    private val street:String,
    private val locality:String,
    private val city:String,
    private val state:String ,
    internal val pinCode: Int)