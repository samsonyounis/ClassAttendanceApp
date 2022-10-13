package ViewModel

import Repository.Repository
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StopClassAttendanceViewModel(private val repository: Repository):ViewModel() {

    var feedback:MutableLiveData<String> = MutableLiveData()

    fun DeleteAuthorization(classCode:String){
        repository.DeletAuthorization(classCode).enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful){
                    feedback.value = "success"
                }
                else{
                    feedback.value = "failed to stop class attendance\ntry again\n${response.code()}"
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                feedback.value = "Couldn't stop class attendance\nCheck connection and try again\n${t.message}"
            }

        })
    }
}