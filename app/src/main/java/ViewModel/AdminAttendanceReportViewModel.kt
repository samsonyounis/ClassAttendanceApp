package ViewModel

import Model.Faculty_AttendanceReport
import Repository.Repository
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminAttendanceReportViewModel(private  val repository: Repository):ViewModel() {

    var feedback: MutableLiveData<String> = MutableLiveData()
    var attendanceRecords:List<Faculty_AttendanceReport> = listOf()

    fun Get_FacultyAttendanceReport(classCode:String){
        repository.Get_FacultyAttendanceReport(classCode).enqueue(object :
            Callback<List<Faculty_AttendanceReport>> {
            override fun onResponse(
                call: Call<List<Faculty_AttendanceReport>>,
                response: Response<List<Faculty_AttendanceReport>>
            ) {
                if (response.isSuccessful){
                    feedback.value = "success"
                    attendanceRecords = response.body()!!
                }
                else{
                    feedback.value = "failed to load attendance report\n" +
                            "some error occurred on server\n\n" +
                            "HTTP Error code: ${response.code()}"
                }
            }

            override fun onFailure(call: Call<List<Faculty_AttendanceReport>>, t: Throwable) {
                feedback.value = "something went wrong with your cnnection\n\n${t.message}"
            }

        })
    }
}