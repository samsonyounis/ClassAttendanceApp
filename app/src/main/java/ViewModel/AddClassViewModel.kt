package ViewModel

import Model.Class
import Model.ServerRes
import Repository.Repository
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddClassViewModel(private val repository: Repository):ViewModel() {
    
    var feedback:MutableLiveData<String> = MutableLiveData()
    fun AddClass(singleClass:Class){
        repository.AddClass(singleClass).enqueue(object :Callback<ServerRes>{
            override fun onResponse(call: Call<ServerRes>, response: Response<ServerRes>) {
                if (response.isSuccessful){
                    feedback.value = "success"
                }
                else{
                    feedback.value = "failed to add class\n${response.body()?.message}\n${response.code()}"
                }
            }


            override fun onFailure(call: Call<ServerRes>, t: Throwable){
                feedback.value = "something went wrong with your connection\n\n${t.message}"
            }

        })
    }
}