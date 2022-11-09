package ViewModel

import Model.GenericResponse
import Repository.Repository
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StopClassAttendanceViewModel(private val repository: Repository):ViewModel() {

    var feedback:MutableLiveData<String> = MutableLiveData()

    fun DeleteAuthorization(classCode:String){
        repository.DeletAuthorization(classCode).enqueue(object : Callback<GenericResponse>{
            override fun onResponse(call: Call<GenericResponse>, response: Response<GenericResponse>) {
                if (response.isSuccessful){
                    feedback.value = "success"
                }
                else{
                    if (response.code() == 400){
                        feedback.value = "authorization not found\n you have not authorized this class before\n" +
                                "${response.code()}"
                    }
                    else{
                        feedback.value = "failed to stop class attendance\n" +
                                "some error occurred on the server\n" +
                                "${response.code()}"
                    }
                }
            }

            override fun onFailure(call: Call<GenericResponse>, t: Throwable) {
                feedback.value = "something went wrong with your connection\n\n${t.message}"
            }

        })
    }
}