package ViewModel

import Model.LoginRequest
import Model.ServerRes
import Repository.Repository
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val repository: Repository):ViewModel() {

    var feedback:MutableLiveData<String> = MutableLiveData()
    fun LoginUser(loginRequest: LoginRequest){
        repository.LoginUser(loginRequest).enqueue(object :Callback<ServerRes>{
            override fun onResponse(call: Call<ServerRes>, response: Response<ServerRes>) {
                if (response.isSuccessful){
                    feedback.value = "success"
                }
                else{
                    feedback.value = "Login failed\n${response.body()?.message}\n${response.code()}"
                }
            }

            override fun onFailure(call: Call<ServerRes>, t: Throwable) {
                feedback.value = "check your connection\n\n${t.message}"
            }

        })
    }
}