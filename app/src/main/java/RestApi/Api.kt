package RestApi

import Model.*
import retrofit2.Call
import retrofit2.http.*

interface Api {

@POST("requestAccount")
fun AddAccountRequest(@Body request:AccountRequest):Call<GenericResponse>

@POST("attendance/sign")
fun RecordAttendance(@Body request:SignAttendanceRequest):Call<GenericResponse>

@POST("attendance/authorize")
fun AuthorizeAttendance(@Body authorization:AttendanceAuthorization):Call<GenericResponse>

@DELETE("attendance/delete/{classCode}")
fun DeleteAuthorization(@Path("classCode") classCode: String):Call<GenericResponse>

@POST("class")
fun AddClass(@Body singleClass:Class):Call<GenericResponse>

@POST("userAccounts/create")
fun createAccount(@Body account:UserAccount):Call<GenericResponse>

@POST("enrollment")
fun AddEnrollment(@Body enrollment:Enrollment):Call<GenericResponse>

@POST("student")
fun AddStudent(@Body student:Student):Call<GenericResponse>

@POST("staff")
 fun AddStaff(@Body staff: Staff):Call<GenericResponse>

 @GET("/api/{regNo}")
 fun Get_StudentAttendanceReport(@Path("regNo") regNo:String):Call<List<Student_attendanceReport>>

 @GET("/api/{classCode}")
 fun Get_FacultyAttendanceReport(@Path("classCode") classCode:String):Call<List<Faculty_AttendanceReport>>

 @GET("requestAccount")
 fun Get_AccountRequests():Call<List<AccountRequest>>

 @POST("userAccounts/login")
 fun LoginUser(@Body loginRequest: LoginRequest):Call<GenericResponse>

 @POST("userAccounts/recoverAccount")
 fun recoverAccount(@Body request:RecoverAccount):Call<GenericResponse>
}