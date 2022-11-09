package Model

data class LoginRequest(
    val username:String,
    val password:String,
    val user_type:String
)
