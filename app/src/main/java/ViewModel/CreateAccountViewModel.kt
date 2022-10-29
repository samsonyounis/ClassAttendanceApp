package ViewModel

import Model.ServerRes
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
        repository.CreateUserAccount(account).enqueue(object :Callback<ServerRes>{
            override fun onResponse(call: Call<ServerRes>, response: Response<ServerRes>) {
                if (response.isSuccessful){
                    feedback.value = "success"
                }
                else{
                    feedback.value = "failed to create account\n${response.body()?.message}\n${response.code()}"
                }
            }

            override fun onFailure(call: Call<ServerRes>, t: Throwable) {
                feedback.value = "Check your connection\n\n${t.message}"
            }

        })
    }
}