package ViewModel

import Model.GenericResponse
import Model.UserAccount
import Repository.Repository
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateAccountViewModel(private val repository: Repository):ViewModel() {
    var feedback:MutableLiveData<String> = MutableLiveData()
    fun CreateAccount(account: UserAccount){
        repository.CreateUserAccount(account).enqueue(object :Callback<GenericResponse>{
            override fun onResponse(call: Call<GenericResponse>, response: Response<GenericResponse>) {
                if (response.isSuccessful){
                    feedback.value = "success"
                }
                else{
                    if (response.code() == 400){
                        feedback.value = "failed to create account\n" +
                                "Username ${account.username} already taken try another username\n\n" +
                                "HTTP Error code: ${response.code()}"
                    }
                    else if (response.code() == 409){
                        feedback.value = "failed to create account\n" +
                                "User with ID ${account.user_ID} already has an account\n\n" +
                                "HTTP Error code: ${response.code()}"
                    }
                    else{
                        feedback.value = "failed to create account\n" +
                                "Some error occurred on the server\n\n" +
                                "HTTP Error code: ${response.code()}"
                    }
                }
            }

            override fun onFailure(call: Call<GenericResponse>, t: Throwable) {
                feedback.value = "Something went wrong with your connection\n\n${t.message}"
            }

        })
    }
}