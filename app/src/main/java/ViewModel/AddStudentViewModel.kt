package ViewModel

import Model.GenericResponse
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
        repository.AddStudent(student).enqueue(object :Callback<GenericResponse>{
            override fun onResponse(call: Call<GenericResponse>, response: Response<GenericResponse>) {
                if (response.isSuccessful){
                    feeddback.value = "success"
                }
                else{
                    if (response.code() == 409){
                        feeddback.value = "failed to add student\n" +
                                "Student with ID ${student.studentID} already exists\n\n" +
                                "HTTP Error code: ${response.code()}"
                    }
                    else{
                        feeddback.value = "failed to add student\n" +
                                "Some error occurred on the server\n\n" +
                                "HTTP Error code: ${response.code()}"
                    }
                }
            }

            override fun onFailure(call: Call<GenericResponse>, t: Throwable) {
                feeddback.value = "Something went wrong with your connection\n\n${t.message}"
            }
        })
    }
}