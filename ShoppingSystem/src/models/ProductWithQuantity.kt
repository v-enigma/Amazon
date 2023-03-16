package models

import models.Product

data class ProductWithQuantity(val product: Product, var quantity: Int)
