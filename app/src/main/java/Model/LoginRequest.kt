package Model

data class LoginRequest(
    val userName:String,
    val password:String,
    val user_type:String
)
