package ViewModel

import Model.AccountRequest
import Repository.Repository
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RequestAccountViewModel(private val repository: Repository):ViewModel() {

    var response1:MutableLiveData<String> = MutableLiveData()

    fun AddAccountRequest(request: AccountRequest){
        repository.AddAccountRequest(request).enqueue(object: Callback<String>{

            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful){
                    response1.value = "success"
                }
                else{
                    response1.value = "failed to send request\n${response.code()}"
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                response1.value = t.message
            }

        })
    }
}