package ViewModel

import Model.Student
import Repository.Repository
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddStudentViewModel(private val repository: Repository):ViewModel() {

    var feeddback:MutableLiveData<String> = MutableLiveData()

    fun AddStudent(student: Student){
        repository.AddStudent(student).enqueue(object :Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful){
                    feeddback.value = "success"
                }
                else{
                    feeddback.value = "failed to add student\n\n${response.code()}"
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                feeddback.value = "check connection\n\n${t.message}"
            }
        })
    }
}