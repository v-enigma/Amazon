data class SellerCatalogComponent(
    val product: Product,
    private var _availableQuantity:Int,
    private var _productApprovalStatus :ProductApprovalStatus ) {
    var availableQuantity:Int
        get() = _availableQuantity
        internal set(value){
            _availableQuantity = value
        }
    var productApprovalStatus : ProductApprovalStatus
        get() = _productApprovalStatus
        internal set(approvalStatus){
            _productApprovalStatus = approvalStatus
        }
}



