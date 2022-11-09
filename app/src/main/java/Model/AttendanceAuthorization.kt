package Model

data class AttendanceAuthorization(
    val class_Code:String,
    val class_Venue:String,
    val class_Duration:Int,
    val class_Date:String,
    val week:String,
    val lecture_ID:String
)
