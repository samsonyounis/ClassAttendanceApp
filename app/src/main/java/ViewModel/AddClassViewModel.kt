package ViewModel

import Model.Class
import Repository.Repository
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddClassViewModel(private val repository: Repository):ViewModel() {
    
    var feedback:MutableLiveData<String> = MutableLiveData()
    fun AddClass(singleClass:Class){
        repository.AddClass(singleClass).enqueue(object :Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful){
                    feedback.value = "success"
                }
                else{
                    feedback.value = "failed to add class\n\n${response.code()}"
                }
            }


            override fun onFailure(call: Call<String>, t: Throwable){
                feedback.value = "check connection\n\n${t.message}"
            }

        })
    }
}