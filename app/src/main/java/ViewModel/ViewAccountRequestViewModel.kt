package ViewModel

import Model.AccountRequest
import Repository.Repository
import androidx.compose.runtime.MutableState
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewAccountRequestViewModel(private val repository: Repository):ViewModel() {

    var feedback: MutableLiveData<String> = MutableLiveData()
    var requestList: List<AccountRequest> = listOf()

    fun Get_AccountRequests() {
        repository.Get_AccountRequests().enqueue(object : Callback<List<AccountRequest>> {
            override fun onResponse(
                call: Call<List<AccountRequest>>, response: Response<List<AccountRequest>>
            ) {
                if (response.isSuccessful) {
                    feedback.value = "success"
                    requestList = response.body()!!
                }
                else {
                    feedback.value = "could not get requests\n" +
                            "some error on the server\n" +
                            "${response.code()}"
                }
            }

            override fun onFailure(call: Call<List<AccountRequest>>, t: Throwable) {
                feedback.value = "Something went wrong with connection ${t.message}"
            }
        })
    }
}