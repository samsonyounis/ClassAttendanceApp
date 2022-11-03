package RestApi

import Model.*
import retrofit2.Call
import retrofit2.http.*

interface Api {

@POST("requestAccount")
fun AddAccountRequest(@Body request:AccountRequest):Call<ServerRes>

@POST("/api")
fun RecordAttendance(@Body request:SignAttendanceRequest):Call<ServerRes>

@POST("attendance/authorize")
fun AuthorizeAttendance(@Body authorization:AttendanceAuthorization):Call<ServerRes>

@DELETE("attendance/delete/{classCode}")
fun DeleteAuthorization(@Path("classCode") classCode: String):Call<ServerRes>

@POST("class")
fun AddClass(@Body singleClass:Class):Call<ServerRes>

@POST("userAccounts/create")
fun createAccount(@Body account:UserAccount):Call<ServerRes>

@POST("enrollment")
fun AddEnrollment(@Body enrollment:Enrollment):Call<ServerRes>

@POST("student")
fun AddStudent(@Body student:Student):Call<ServerRes>

@POST("staff")
 fun AddStaff(@Body staff: Staff):Call<ServerRes>

 @GET("/api/{regNo}")
 fun Get_StudentAttendanceReport(@Path("regNo") regNo:String):Call<List<Student_attendanceReport>>

 @GET("/api/{classCode}")
 fun Get_FacultyAttendanceReport(@Path("classCode") classCode:String):Call<List<Faculty_AttendanceReport>>

 @GET("requestAccount")
 fun Get_AccountRequests():Call<List<AccountRequest>>

 @POST("userAccounts/login")
 fun LoginUser(@Body loginRequest: LoginRequest):Call<ServerRes>

 @POST("userAccounts/recoverAccount")
 fun recoverAccount(@Body request:RecoverAccount):Call<ServerRes>
}