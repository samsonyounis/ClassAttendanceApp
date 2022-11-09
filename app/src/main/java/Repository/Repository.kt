package Repository

import Model.*
import RestApi.RetrofitObjInstance
import retrofit2.Call

class Repository {

    // function to add account request
fun AddAccountRequest(request: AccountRequest):Call<GenericResponse>{
    return RetrofitObjInstance.ApiConnect.AddAccountRequest(request)
}

    // function to add single attendance record
    fun RecordAttendance(request: SignAttendanceRequest):Call<GenericResponse>{
        return RetrofitObjInstance.ApiConnect.RecordAttendance(request)
    }

    // function to add attendance authorization
    fun AuthorizeAttenance(authorization: AttendanceAuthorization):Call<GenericResponse>{
        return RetrofitObjInstance.ApiConnect.AuthorizeAttendance(authorization)
    }
    // function to remove class attendance authorization
    fun DeletAuthorization(classCode: String):Call<GenericResponse>{
        return RetrofitObjInstance.ApiConnect.DeleteAuthorization(classCode)
    }
    // function to add single class record
    fun AddClass(singleClass:Class):Call<GenericResponse>{
        return RetrofitObjInstance.ApiConnect.AddClass(singleClass)
    }
    // function to create user account
    fun CreateUserAccount(account: UserAccount):Call<GenericResponse>{
        return RetrofitObjInstance.ApiConnect.createAccount(account)
    }
    // function to add class enrollment
    fun AddClassEnrollment(enrollment: Enrollment):Call<GenericResponse>{
        return RetrofitObjInstance.ApiConnect.AddEnrollment(enrollment)
    }
    // function to add single student
    fun AddStudent(student: Student):Call<GenericResponse>{
        return RetrofitObjInstance.ApiConnect.AddStudent(student)
    }
    // function to add staff
    fun AddStaff(staff: Staff):Call<GenericResponse>{
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
    fun LoginUser(loginRequest: LoginRequest):Call<GenericResponse>{
        return RetrofitObjInstance.ApiConnect.LoginUser(loginRequest)
    }
    // function to recover account
    fun recoverAccount(request:RecoverAccount):Call<GenericResponse>{
        return RetrofitObjInstance.ApiConnect.recoverAccount(request)
    }
}