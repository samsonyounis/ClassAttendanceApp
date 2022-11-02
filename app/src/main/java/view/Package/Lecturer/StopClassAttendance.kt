package view.Package.Lecturer

import Repository.Repository
import ViewModel.StopClassAttendanceViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
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
    var classCodeError by rememberSaveable { mutableStateOf("") }
    var label by rememberSaveable { mutableStateOf("Enter the class code") }
    var showProgress by rememberSaveable { mutableStateOf(false) }
    var toplabel by rememberSaveable { mutableStateOf("Stop Attendance") }
    var feedback by rememberSaveable { mutableStateOf("Class attendance closed successfully") }

    val repository = Repository() // instance of the repository
    val viewmodel = StopClassAttendanceViewModel(repository) // instance of viewModel
    // innitailizing the lifeCycle owner of this compose screen
    val lifeCycleOwner: LifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifeCycleOwner) {
        // Create an observer that triggers our remembered callbacks
        // for sending analytics events
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_PAUSE) {
                showProgress = false
            } else if (event == Lifecycle.Event.ON_STOP) {
            }
        }

        // Add the observer to the lifecycle
        lifeCycleOwner.lifecycle.addObserver(observer)

        // When the effect leaves the Composition, remove the observer
        onDispose {
            lifeCycleOwner.lifecycle.removeObserver(observer)
        }
    }

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
            Text(text = classCodeError,
            color = Color.Red)

            commonButton(onClick = {
                //validating the class code here
                if (classCode.isBlank()){
                    classCodeError = "*Class code field is black"
                }
                else if (classCode.length<6){
                    classCodeError = "*class code must consist of 6 Alphanumeric characters"
                }
                else if(classCode.length>6){
                    classCodeError = "*class code can not contain white spaces and must be 6 characters long"
                }
                else if (classCode.contains(" ")){
                    classCodeError = "*Class code can't contain white spaces"
                }
                else{
                    showProgress = true
                    viewmodel.DeleteAuthorization(classCode.uppercase())
                    viewmodel.feedback.observe(lifeCycleOwner){response->
                        if (response == "success"){
                            navController.navigate("feedback_Screen/$toplabel/$feedback" )
                        }
                        else{
                            val encodedResponse = URLEncoder.encode(response,StandardCharsets.UTF_8.toString())
                            navController.navigate("feedback_Screen/$toplabel/$encodedResponse" )
                        }
                    }
                }
            },
                label = "Stop attendance")
        }
    }
}