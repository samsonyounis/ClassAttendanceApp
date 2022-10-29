package ViewModel

import Model.ServerRes
import Repository.Repository
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StopClassAttendanceViewModel(private val repository: Repository):ViewModel() {

    var feedback:MutableLiveData<String> = MutableLiveData()

    fun DeleteAuthorization(classCode:String){
        repository.DeletAuthorization(classCode).enqueue(object : Callback<ServerRes>{
            override fun onResponse(call: Call<ServerRes>, response: Response<ServerRes>) {
                if (response.isSuccessful){
                    feedback.value = "success"
                }
                else{
                    feedback.value = "failed to stop class attendance\n${response.body()?.message}\n${response.code()}"
                }
            }

            override fun onFailure(call: Call<ServerRes>, t: Throwable) {
                feedback.value = "Couldn't stop class attendance\nCheck connection and try again\n${t.message}"
            }

        })
    }
}