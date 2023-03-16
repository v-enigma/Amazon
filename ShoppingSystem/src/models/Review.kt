package models

import java.time.LocalDate

data class Review (
    private val userId: Int,
    val  userName: String,
    var comment : String?,
    var rating : Float ,
    val reviewedDate : LocalDate)
