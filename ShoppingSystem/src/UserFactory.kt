import Users.Admin
import Users.Customer
import Users.DeliveryAgent
import Users.Seller
import java.time.LocalDate

object AuthenticationHelper {
    fun customerAuthentication(userId: String, password: String): Customer {
        return AuthenticationData.customerAuthentication(userId, password)
            ?: throw AuthenticationException("Invalid Credentials")
    }

    fun adminAuthentication(userId: String, password: String): Admin {
        return AuthenticationData.adminAuthentication(userId.toInt(), password)

    }

    fun sellerAuthentication(userId: String, password: String): Seller {
        return AuthenticationData.sellerAuthentication(userId, password)

    }

    fun deliveryAgentAuthentication(userId: String, password: String): DeliveryAgent {
        return AuthenticationData.deliveryAgentAuthentication(userId, password)

    }

    fun isDuplicatePhoneNo(phoneNo: String, role: Role): Boolean {
        return AuthenticationData.checkPhoneNoExistence(phoneNo, role)
    }
}

object UserCreationHelper {
    private var customerId = 0
    private var sellerId = 0;
    private var deliveryAgent = 0;
    private fun generateCustomerId(): Int {
        return ++customerId
    }

    private fun generateSellerId(): Int {
        return ++sellerId
    }

    private fun generateDeliveryAgentId(): Int {
        return ++deliveryAgent
    }

    fun createUser(role: Role, userDetails: List<String>, address: List<String> = listOf(), pinCode: Int = 0) {
        when (role) {
            Role.ADMIN -> {}
            Role.SELLER -> {
                val sellerId = generateSellerId()
                val seller = Seller(
                    sellerId,
                    userDetails.component1(),
                    userDetails.component2(),
                    LocalDate.parse(userDetails.component3()),
                    userDetails.component4()
                )
                SellerDB.addUser(seller)
                AuthenticationData.addDetailsForAuthentication(role, seller, userDetails.last());
            }

            Role.DELIVERY_AGENT -> {
                val deliveryAgentId = generateDeliveryAgentId()
                val deliveryAgent = DeliveryAgent(
                    deliveryAgentId,
                    userDetails.component1(),
                    userDetails.component2(),
                    LocalDate.parse(userDetails.component3()),
                    userDetails.component4(),
                    pinCode
                )
                DeliveryAgentDB.addUser(deliveryAgent);
                AuthenticationData.addDetailsForAuthentication(role, deliveryAgent, userDetails.last())
            }

            Role.CUSTOMER -> {
                val customerId = generateCustomerId()
                val customer = Customer(
                    customerId,
                    userDetails.component1(),
                    userDetails.component2(),
                    LocalDate.parse(userDetails.component3()),
                    userDetails.component4(),

                ).also{
                    it.address.add(Address(
                        address.component1(), address.component2(), address.component3(),
                        address.component4(), address.component5(), address.last().toInt()
                    ))
                }
                CustomerDB.addUser(customer);
                AuthenticationData.addDetailsForAuthentication(role, customer, userDetails.last())
            }
        }

    }
}