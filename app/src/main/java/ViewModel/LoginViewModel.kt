package ViewModel

import Model.GenericResponse
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
        repository.LoginUser(loginRequest).enqueue(object :Callback<GenericResponse>{
            override fun onResponse(call: Call<GenericResponse>, response: Response<GenericResponse>) {
                if (response.isSuccessful){
                    feedback.value = "success"
                }
                else{
                    if (response.code() == 400){
                        feedback.value = "login failed\n" +
                                "Username ${loginRequest.username} does not exist\n" +
                                "${response.code()}"
                    }
                    else if (response.code() == 409){
                        feedback.value  = "login failed\n" +
                                "Invalid password or user type not matching the credentials"
                    }
                    else{
                        feedback.value = "Login failed\n" +
                                "some error occurred on the server\n" +
                                "${response.code()}"
                    }
                }
            }

            override fun onFailure(call: Call<GenericResponse>, t: Throwable) {
                feedback.value = "check your connection\n\n${t.message}"
            }

        })
    }
}