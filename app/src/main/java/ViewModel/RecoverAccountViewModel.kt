package ViewModel

import Model.AccountRequest
import Model.RecoverAccount
import Model.ServerRes
import Repository.Repository
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecoverAccountViewModel(private val repository: Repository):ViewModel() {

    var feedback: MutableLiveData<String> = MutableLiveData()

    fun recoverAccount(request: RecoverAccount){
        repository.recoverAccount(request).enqueue(object: Callback<ServerRes> {

            override fun onResponse(call: Call<ServerRes>, response: Response<ServerRes>) {
                if (response.isSuccessful){
                    feedback.value = "success"
                }
                else{
                    feedback.value = "failed to send request\n${response.body()?.message}\n${response.code()}"

                }
            }

            override fun onFailure(call: Call<ServerRes>, t: Throwable) {
                feedback.value = "something went wrong\n ${t.message}"
            }

        })
    }
}