package ViewModel

import Model.AccountRequest
import Model.ServerRes
import Repository.Repository
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RequestAccountViewModel(private val repository: Repository):ViewModel() {

    var response1:MutableLiveData<String> = MutableLiveData()

    fun AddAccountRequest(request: AccountRequest){
        repository.AddAccountRequest(request).enqueue(object: Callback<ServerRes>{

            override fun onResponse(call: Call<ServerRes>, response: Response<ServerRes>) {
                if (response.isSuccessful){
                    response1.value = "success"
                }
                else{
                    response1.value = " failed to send request\n\n${response.body()?.message}\n${response.code()}"
                    //failed to send request
                    //${response.code()}
                }
            }

            override fun onFailure(call: Call<ServerRes>, t: Throwable) {
                response1.value = "something went wrong with your connection ${t.message}"
            }

        })
    }
}