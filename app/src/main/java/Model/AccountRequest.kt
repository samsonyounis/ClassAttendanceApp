package Model

    data class AccountRequest(
        val student_ID: String,
        val student_Firstname: String,
        val student_lastname: String,
        val email: String,
        val phone: String
    )