package ViewModel

import Model.Enrollment
import Model.GenericResponse
import Repository.Repository
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EnrollmentViewModel(private val repository: Repository):ViewModel() {

    val feedback:MutableLiveData<String> = MutableLiveData()
    fun AddEnrollment(enrollment: Enrollment){
        repository.AddClassEnrollment(enrollment).enqueue(object : Callback<GenericResponse>{
            override fun onResponse(call: Call<GenericResponse>, response: Response<GenericResponse>) {
                if (response.isSuccessful){
                    feedback.value = "success"
                }
                else{
                    if (response.code() == 400){
                        feedback.value = "Enrollment failed\n" +
                                "Class with code ${enrollment.enrollmentID.classCode} does not exist or" +
                                "Student with ID ${enrollment.enrollmentID.studentID} does not exist\n" +
                                "${response.code()}"
                    }
                    else if (response.code() == 409){
                        feedback.value = "Enrollment failed\n" +
                                "you have already enrolled in ${enrollment.enrollmentID.classCode}\n" +
                                "${response.code()}"
                    }
                    else{
                        feedback.value = "Enrollment failed\n" +
                                "some error occurred on the server" +
                                "\n\n${response.code()}"
                    }
                }
            }

            override fun onFailure(call: Call<GenericResponse>, t: Throwable) {
                feedback.value = "something went wrong with your connection\n\n${t.message}"
            }
        })
    }
}