package ViewModel

import Model.AttendanceAuthorization
import Model.GenericResponse
import Repository.Repository
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AttendanceAuthorizationViewModel(private val repository: Repository):ViewModel() {
    var feedback: MutableLiveData<String> = MutableLiveData()
    fun addAuthorization(authorization: AttendanceAuthorization){
        repository.AuthorizeAttenance(authorization).enqueue(object : Callback<GenericResponse> {
            override fun onResponse(call: Call<GenericResponse>, response: Response<GenericResponse>) {
                if (response.isSuccessful){
                    feedback.value = "success"
                }
                else{
                    if (response.code() == 400){
                        feedback.value = "failed ro authorize attendance\n" +
                                "Class with code ${authorization.class_Code} does not exist\n" +
                                "${response.code()}"
                    }
                    else if (response.code() == 409){
                        feedback.value = "failed to authorize attendance\n" +
                                "${response.code()}"
                    }
                    else{
                        feedback.value = "failed to authorize class attendance\n" +
                                "some error occurred on the server\n" +
                                "${response.code()}"
                    }
                }
            }

            override fun onFailure(call: Call<GenericResponse>, t: Throwable) {
                feedback.value = "something went wrong with your connection\n\n${t.message}"
            }

        })
    }
}