package view.Package.Student

import Model.SignAttendanceRequest
import Repository.Repository
import ViewModel.SignAttendnaceViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsWithImePadding
import view.Package.ReusableFunctions.circularProgress
import view.Package.ReusableFunctions.commonButton
import view.Package.ReusableFunctions.outlinedTextField
import view.Package.ReusableFunctions.topRow
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun signAttendanceScreen(navController: NavController,stu_DeviceID:String,lec_DeviceID:String){
    var studentID by rememberSaveable { mutableStateOf("") }
    var stu_FirstName by rememberSaveable { mutableStateOf("") }
    var stu_LastName by rememberSaveable { mutableStateOf("") }
    var classCode by rememberSaveable { mutableStateOf("") }
    var stu_DeviceID by rememberSaveable { mutableStateOf(stu_DeviceID) }
    var lec_DeviceID by rememberSaveable { mutableStateOf(lec_DeviceID) }

    var toplabel by rememberSaveable { mutableStateOf("Enrollment") }
    var feedback by rememberSaveable { mutableStateOf("Successfully enrolled") }
    var showProgress by rememberSaveable { mutableStateOf(false) }

    val repository = Repository() // instance of the repository
    val viewmodel = SignAttendnaceViewModel(repository) // instance of the viewModel
    // innitailizing the lifeCycle owner of this compose screen
    val lifeCycleOwner: LifecycleOwner = LocalLifecycleOwner.current

    Scaffold(modifier = Modifier.padding(16.dp),
        topBar = {
            topRow(text = "Sign attendnace", navController = navController)
        }) {
        // showing the circular progress while adding the attendnace record
        if (showProgress == true) {
            Dialog(onDismissRequest = { showProgress == true }) {
                circularProgress(showProgress = showProgress)
            }
        }

        ProvideWindowInsets {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .navigationBarsWithImePadding()
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                outlinedTextField(
                    valueText = studentID, onValueChange = { studentID = it }, isError = false,
                    labelText = "registration number", placeholderText = "",
                    keyboardType = KeyboardType.Number, imeAction = ImeAction.Next
                )
                outlinedTextField(
                    valueText = stu_FirstName, onValueChange = { stu_FirstName = it }, isError = false,
                    labelText = "First name", placeholderText = "",
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                )
                outlinedTextField(
                    valueText = stu_LastName, onValueChange = { stu_LastName = it }, isError = false,
                    labelText = "Last name", placeholderText = "",
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                )
                outlinedTextField(
                    valueText = classCode, onValueChange = { classCode = it }, isError = false,
                    labelText = "Class code", placeholderText = "e.g SIT400",
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                )
                Column {
                    Text(text = "Student device ID")
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(value = stu_DeviceID, onValueChange = {stu_DeviceID = it},
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                        isError = false,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp), enabled = false, readOnly = true
                    )
                }
                Column {
                    Text(text = "Lecturer device ID")
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(value = lec_DeviceID, onValueChange = {lec_DeviceID = it},
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        isError = false,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp), enabled = false, readOnly = true
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))
                //onclick listener button
                commonButton(
                    onClick = {
                        showProgress = true
                        // creating the sign atendance request object
                        val request = SignAttendanceRequest(studentID,classCode,stu_FirstName,
                            stu_FirstName,stu_DeviceID,lec_DeviceID)
                        // calling the function in the view model and observing th value
                        viewmodel.recordAttendance(request)
                        viewmodel.feedback.observe(lifeCycleOwner){response->
                            if (response == "success"){
                                showProgress = false
                                navController.navigate("feedback_Screen/$toplabel/$feedback" )
                            }
                            else{
                                showProgress = false
                                val encodedResponse = URLEncoder.encode(response, StandardCharsets.UTF_8.toString())
                                navController.navigate("feedback_Screen/$toplabel/$encodedResponse" )
                            }
                        }
                    }
                    ,
                    label = "Sign"
                )
            }
        }
    }
}