package ViewModel

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
        repository.CreateUserAccount(account).enqueue(object :Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful){
                    feedback.value = "success"
                }
                else{
                    feedback.value = "failed to create account\n\n${response.code()}"
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                feedback.value = "Check your connection\n\n${t.message}"
            }

        })
    }
}