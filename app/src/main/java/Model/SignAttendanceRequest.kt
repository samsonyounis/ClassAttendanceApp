package Model

data class SignAttendanceRequest(
    val studentID:String,
    val classCode:String,
    val stu_FirstName:String,
    val stu_LastName:String,
    val stu_DeviceID:String,
    val lec_DeviceID:String
)
