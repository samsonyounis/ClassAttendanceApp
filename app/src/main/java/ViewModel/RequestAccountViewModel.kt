package ViewModel

import Model.AccountRequest
import Model.GenericResponse
import Repository.Repository
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RequestAccountViewModel(private val repository: Repository):ViewModel() {

    var response1:MutableLiveData<String> = MutableLiveData()

    fun AddAccountRequest(request: AccountRequest){
        repository.AddAccountRequest(request).enqueue(object: Callback<GenericResponse>{

            override fun onResponse(call: Call<GenericResponse>, response: Response<GenericResponse>) {
                if (response.isSuccessful){
                    response1.value = "success"
                }
                else{
                    if (response.code() == 400){
                        response1.value = "failed to send rquest\n" +
                                "No student associated with this ID ${request.student_ID}\n\n" +
                                "HTTP Error code: ${response.code()}"
                    }
                    else if (response.code() == 409){
                        response1.value = "failed to send request\n" +
                                "Request already exists\n\n" +
                                "HTTP Error code: ${response.code()}"
                    }
                    else{
                        response1.value = "failed to send request\n" +
                                "some error occurred on the server\n\n" +
                                "HTTP Error code: ${response.code()}"
                    }
                }
            }

            override fun onFailure(call: Call<GenericResponse>, t: Throwable) {
                response1.value = "something went wrong with your connection ${t.message}"
            }

        })
    }
}