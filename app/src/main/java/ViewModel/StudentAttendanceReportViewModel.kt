package ViewModel

import Model.Student_attendanceReport
import Repository.Repository
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StudentAttendanceReportViewModel(private val repository: Repository):ViewModel() {

    var feedback:MutableLiveData<String> = MutableLiveData()
    lateinit var attendanceRecords:List<Student_attendanceReport>

    fun Get_StudentAttendanceReport(regNO:String){
        repository.Get_StudentAttendanceReport(regNO).enqueue(object :Callback<List<Student_attendanceReport>>{
            override fun onResponse(
                call: Call<List<Student_attendanceReport>>,
                response: Response<List<Student_attendanceReport>>
            ) {
                if (response.isSuccessful){
                    feedback.value = "success"
                    attendanceRecords = response.body()!!
                }
                else{
                    feedback.value = "failed to load attendance report\n\n${response.code()}"
                }
            }

            override fun onFailure(call: Call<List<Student_attendanceReport>>, t: Throwable) {
                feedback.value = "check connection\n\n${t.message}"
            }

        })
    }
}