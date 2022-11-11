package ViewModel

import Model.Class
import Model.GenericResponse
import Repository.Repository
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddClassViewModel(private val repository: Repository):ViewModel() {
    
    var feedback:MutableLiveData<String> = MutableLiveData()
    fun AddClass(singleClass:Class){
            repository.AddClass(singleClass).enqueue(object : Callback<GenericResponse> {
                override fun onResponse(call: Call<GenericResponse>, response: Response<GenericResponse>) {
                    if (response.isSuccessful) {
                        feedback.value = "success"
                    } else {
                        if (response.code() == 409){
                            feedback.value = "failed to add class\n" +
                                    "Class with the code ${singleClass.classCode} already exists\n\n" +
                                    "HTTP Error code: ${response.code()}"
                        }
                        else if (response.code() == 400){
                            feedback.value = "failed to add class\n" +
                                    "Bad request, instructor's ID ${singleClass.classInstructorID} does not exist\n\n" +
                                    "HTTP Error code: ${response.code()}"
                        }
                        else{
                            feedback.value = "failed to add class\n${response.body()?.response}\n${response.code()}"
                        }
                    }
                }
                override fun onFailure(call: Call<GenericResponse>, t: Throwable) {
                    feedback.value = "something went wrong with your connection\n\n${t.message}"
                }

            })
    }
}