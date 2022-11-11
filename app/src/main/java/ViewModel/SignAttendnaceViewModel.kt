package ViewModel

import Model.GenericResponse
import Model.SignAttendanceRequest
import Repository.Repository
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignAttendnaceViewModel(private val repository: Repository):ViewModel() {

    var feedback: MutableLiveData<String> = MutableLiveData()
    fun recordAttendance(request: SignAttendanceRequest){
        repository.RecordAttendance(request).enqueue(object : Callback<GenericResponse> {
            override fun onResponse(call: Call<GenericResponse>, response: Response<GenericResponse>) {
                if (response.isSuccessful) {
                    feedback.value = "success"
                }
                else {
                    if (response.code() == 400){
                        feedback.value = "failed to sign attendance\n" +
                                "Attendance for ${request.classCode} not authorized yet or" +
                                "you are not within the range of your instructor\n\n" +
                                "HTTP Error code: ${response.code()}"
                    }
                    else if(response.code() == 409){
                        feedback.value = "failed to sign attendance\n" +
                                "you have already signed the attendance for ${request.classCode}\n\n" +
                                "HTTP Error code: ${response.code()}"
                    }
                    else{
                        feedback.value = "failed to record the attendance\n" +
                                    "some error occurred on the server\n\n" +
                                "HTTP Error code: ${response.code()}"
                    }
                }
            }


            override fun onFailure(call: Call<GenericResponse>, t: Throwable) {
                feedback.value = "something went wrong with your connection\n\n${t.message}"
            }

        })
    }
}