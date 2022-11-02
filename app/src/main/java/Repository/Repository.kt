package Repository

import Model.*
import RestApi.RetrofitObjInstance
import retrofit2.Call

class Repository {
    // function to add account request
fun AddAccountRequest(request: AccountRequest):Call<ServerRes>{
    return RetrofitObjInstance.ApiConnect.AddAccountRequest(request)
}

    // function to add single attendance record
    fun RecordAttendance(attendance: Attendance):Call<String>{
        return RetrofitObjInstance.ApiConnect.RecordAttendance(attendance)
    }

    // function to add attendance authorization
    fun AuthorizeAttenance(authorization: AttendanceAuthorization):Call<ServerRes>{
        return RetrofitObjInstance.ApiConnect.AuthorizeAttendance(authorization)
    }
    // function to remove class attendance authorization
    fun DeletAuthorization(classCode: String):Call<ServerRes>{
        return RetrofitObjInstance.ApiConnect.DeleteAuthorization(classCode)
    }
    // function to add single class record
    fun AddClass(singleClass:Class):Call<ServerRes>{
        return RetrofitObjInstance.ApiConnect.AddClass(singleClass)
    }
    // function to create user account
    fun CreateUserAccount(account: UserAccount):Call<ServerRes>{
        return RetrofitObjInstance.ApiConnect.createAccount(account)
    }
    // function to add class enrollment
    fun AddClassEnrollment(enrollment: Enrollment):Call<ServerRes>{
        return RetrofitObjInstance.ApiConnect.AddEnrollment(enrollment)
    }
    // function to add single student
    fun AddStudent(student: Student):Call<ServerRes>{
        return RetrofitObjInstance.ApiConnect.AddStudent(student)
    }
    // function to add staff
    fun AddStaff(staff: Staff):Call<ServerRes>{
        return RetrofitObjInstance.ApiConnect.AddStaff(staff)
    }
    // function to get the attendance report for the student
    fun Get_StudentAttendanceReport(regNo:String):Call<List<Student_attendanceReport>>{
        return RetrofitObjInstance.ApiConnect.Get_StudentAttendanceReport(regNo)
    }

    // function to get the attendance report for the Faculty
    fun Get_FacultyAttendanceReport(classCode:String):Call<List<Faculty_AttendanceReport>>{
        return RetrofitObjInstance.ApiConnect.Get_FacultyAttendanceReport(classCode)
    }

    // function to get the account requests sent by the users.
    fun Get_AccountRequests():Call<List<AccountRequest>>{
        return RetrofitObjInstance.ApiConnect.Get_AccountRequests()
    }

    // function to login  the users.
    fun LoginUser(loginRequest: LoginRequest):Call<ServerRes>{
        return RetrofitObjInstance.ApiConnect.LoginUser(loginRequest)
    }
    // function to recover account
    fun recoverAccount(request:RecoverAccount):Call<ServerRes>{
        return RetrofitObjInstance.ApiConnect.recoverAccount(request)
    }
}