package RestApi

import Model.*
import retrofit2.Call
import retrofit2.http.*

interface Api {

@POST("requestAccount")
fun AddAccountRequest(@Body request:AccountRequest):Call<String>

@POST("/api")
fun RecordAttendance(@Body attendance:Attendance):Call<String>

@POST("/api")
fun AuthorizeAttendance(@Body authorization:AttendanceAuthorization):Call<String>

@DELETE("deleteAuthorization")
fun DeleteAuthorization(@Query("classCode") classCode: String):Call<String>

@POST("/api")
fun AddClass(@Body singleClass:Class):Call<String>

@POST("/api")
fun createAccount(@Body account:UserAccount):Call<String>

@POST("/enrollment")
fun AddEnrollment(@Body enrollment:Enrollment):Call<String>

@POST("/api")
fun AddStudent(@Body student:Student):Call<String>

@POST("/api")
 fun AddStaff(@Body staff: Staff):Call<String>

 @GET("/api")
 fun Get_StudentAttendanceReport(@Query("regNo") regNo:String):Call<List<Student_attendanceReport>>

 @GET("/api")
 fun Get_FacultyAttendanceReport(@Query("classCode") classCode:String):Call<List<Faculty_AttendanceReport>>

 @GET("/api")
 fun Get_AccountRequests():Call<List<AccountRequest>>

 @POST("login")
 fun LoginUser(@Body loginRequest: LoginRequest):Call<String>
}