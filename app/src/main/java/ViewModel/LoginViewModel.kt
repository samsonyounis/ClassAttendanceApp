package ViewModel

import Model.LoginRequest
import Repository.Repository
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val repository: Repository):ViewModel() {

    var feedback:MutableLiveData<String> = MutableLiveData()
    fun LoginUser(loginRequest: LoginRequest){
        repository.LoginUser(loginRequest).enqueue(object :Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful){
                    feedback.value = "success"
                }
                else{
                    feedback.value = "Login failed\n\n${response.errorBody()}"
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                feedback.value = "check your connection\n\n${t.message}"
            }

        })
    }
}