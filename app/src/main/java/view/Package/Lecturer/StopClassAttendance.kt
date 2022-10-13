package view.Package.Lecturer

import Repository.Repository
import ViewModel.EnrollmentViewModel
import ViewModel.StopClassAttendanceViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
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
import view.Package.ReusableFunctions.circularProgress
import view.Package.ReusableFunctions.commonButton
import view.Package.ReusableFunctions.outlinedTextField
import view.Package.ReusableFunctions.topRow
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun StopAttendanceScreen(navController: NavController){
    var classCode by rememberSaveable { mutableStateOf("") }
    var label by rememberSaveable { mutableStateOf("Enter the class code") }
    var showProgress by rememberSaveable { mutableStateOf(false) }
    var toplabel by rememberSaveable { mutableStateOf("Stop Attendance") }
    var feedback by rememberSaveable { mutableStateOf("Class attendance closed successfully") }

    val repository = Repository()
    val viewmodel = StopClassAttendanceViewModel(repository)
    // innitailizing the lifeCycle owner of this compose screen
    val lifeCycleOwner: LifecycleOwner = LocalLifecycleOwner.current

    Scaffold(modifier = Modifier.padding(16.dp),
        topBar = {
            topRow(text = " Stop attendance" , navController = navController)
            Spacer(modifier = Modifier.height(30.dp))
        }
    ) {
        // showing the circular progress
        if (showProgress == true) {
            Dialog(onDismissRequest = { showProgress == true }) {
                circularProgress(showProgress = showProgress)
            }
        }

        Column(modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly) {
            outlinedTextField(
                valueText = classCode , onValueChange = { classCode = it}, isError = false,
                labelText = label, placeholderText ="" ,
                keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
            )

            commonButton(onClick = {
                showProgress = true
                viewmodel.DeleteAuthorization(classCode)
                viewmodel.feedback.observe(lifeCycleOwner){response->
                    if (response.toString() == "success"){
                        navController.navigate("feedback_Screen/$toplabel/$feedback" )
                    }
                    else{
                        val encodedResponse = URLEncoder.encode(response,StandardCharsets.UTF_8.toString())
                        navController.navigate("feedback_Screen/$toplabel/$encodedResponse" )
                    }
                }
            },
                label = "Stop attendance")
        }
    }
}