package ViewModel

import Model.Enrollment
import Model.ServerRes
import Repository.Repository
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EnrollmentViewModel(private val repository: Repository):ViewModel() {

    val feedback:MutableLiveData<String> = MutableLiveData()
    fun AddEnrollment(enrollment: Enrollment){
        repository.AddClassEnrollment(enrollment).enqueue(object : Callback<ServerRes>{
            override fun onResponse(call: Call<ServerRes>, response: Response<ServerRes>) {
                if (response.isSuccessful){
                    feedback.value = "success"
                }
                else{
                    feedback.value = "Enrollment failed\n${response.body()?.message}\n\n${response.code()}"
                }
            }

            override fun onFailure(call: Call<ServerRes>, t: Throwable) {
                feedback.value = "Enrollment failed, check connection\n\n${t.message}"
            }
        })
    }
}