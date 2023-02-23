abstract class Offer {
    abstract val description: String
    abstract fun applyOffer(grossPrice: Double): Double
}