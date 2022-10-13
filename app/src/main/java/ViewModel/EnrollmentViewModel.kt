package ViewModel

import Model.Enrollment
import Repository.Repository
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EnrollmentViewModel(private val repository: Repository):ViewModel() {

    val feedback:MutableLiveData<String> = MutableLiveData()
    fun AddEnrollment(enrollment: Enrollment){
        repository.AddClassEnrollment(enrollment).enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful){
                    feedback.value = "success"
                }
                else{
                    feedback.value = "Enrollment failed\n\n${response.message()}\n\n${response.code()}"
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                feedback.value = "Enrollment failed, check connection\n\n${t.message}"
            }
        })
    }
}