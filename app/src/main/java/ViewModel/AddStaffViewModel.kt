package ViewModel

import Model.GenericResponse
import Model.Staff
import Repository.Repository
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddStaffViewModel(private val repository: Repository):ViewModel() {

    var feedback:MutableLiveData<String> = MutableLiveData()

    fun AddStaff(staff: Staff){
        repository.AddStaff(staff).enqueue(object : Callback<GenericResponse>{
            override fun onResponse(call: Call<GenericResponse>, response: Response<GenericResponse>) {
                if (response.isSuccessful){
                    feedback.value = "success"
                }
                else{
                    if (response.code() == 409){
                        feedback.value = "failed to add staff\n" +
                                "Staff with ID ${staff.staffID} already exists\n" +
                                "${response.code()}"
                    }
                    else{
                        feedback.value = "failed to add staff\n" +
                                "Some error occured on the server\n" +
                                "${response.code()}"
                    }
                }
            }

            override fun onFailure(call: Call<GenericResponse>, t: Throwable) {
                feedback.value = "Somthing went wrong with your connection\n\n${t.message}"
            }

        })
    }
}