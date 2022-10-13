package Model

    data class AccountRequest(
        val userID: String,
        val firstName: String,
        val lastName: String,
        val email: String,
        val phone: String,
        val accountType:String
    )