package ViewModel

import Model.ServerRes
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
        repository.AddStudent(student).enqueue(object :Callback<ServerRes>{
            override fun onResponse(call: Call<ServerRes>, response: Response<ServerRes>) {
                if (response.isSuccessful){
                    feeddback.value = "success"
                }
                else{
                    feeddback.value = "failed to add student\n${response.body()?.message}\n${response.code()}"
                }
            }

            override fun onFailure(call: Call<ServerRes>, t: Throwable) {
                feeddback.value = "Something went wrong with your connection\n\n${t.message}"
            }
        })
    }
}