package ViewModel

import Model.GenericResponse
import Model.RecoverAccount
import Repository.Repository
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecoverAccountViewModel(private val repository: Repository):ViewModel() {

    var feedback: MutableLiveData<String> = MutableLiveData()

    fun recoverAccount(request: RecoverAccount){
        repository.recoverAccount(request).enqueue(object: Callback<GenericResponse> {

            override fun onResponse(call: Call<GenericResponse>, response: Response<GenericResponse>) {
                if (response.isSuccessful){
                    feedback.value = "success"
                }
                else{
                    if (response.code() == 400){
                        feedback.value = "No account found\n\n" +
                                "HTTP Error code: ${response.code()}"
                    }
                    else{
                        feedback.value = "failed to send request\n" +
                                "some error occurred on the server\n\n" +
                                "HTTP Error code: ${response.code()}"
                    }
                }
            }

            override fun onFailure(call: Call<GenericResponse>, t: Throwable) {
                feedback.value = "something went wrong\n ${t.message}"
            }

        })
    }
}