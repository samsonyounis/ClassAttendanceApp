package view.Package.Lecturer

import Model.AttendanceAuthorization
import Repository.Repository
import ViewModel.AttendanceAuthorizationViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
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
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun authorizeAttendanceScreen(navController: NavController, classCode:String,
                              classDuration:String,deviceID:String){
    var classCode by rememberSaveable { mutableStateOf(classCode) }
    var classVenue by rememberSaveable { mutableStateOf("") }
    val dateFormatter = SimpleDateFormat("dd-MM-yyyy")
    val currentDate = dateFormatter.format(Date())
    var classDate by rememberSaveable { mutableStateOf(currentDate) }
    var week by rememberSaveable { mutableStateOf("") }
    var classDuration by rememberSaveable { mutableStateOf(classDuration) }
    var instructorID by rememberSaveable { mutableStateOf("") }
    var instructorDeviceID by rememberSaveable { mutableStateOf(deviceID) }

    var toplabel by rememberSaveable { mutableStateOf("Attendance authorization") }
    var feedback by rememberSaveable {  mutableStateOf("Authorization done succesfully") }
    var showProgress by rememberSaveable { mutableStateOf(false) }
    val repository = Repository()
    val viewmodel = AttendanceAuthorizationViewModel(repository)
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
            topRow(text = "Authorize class Attendance", navController = navController)
        }) {
        //showing circular progress here while creating the user account.
        if (showProgress == true) {
            Dialog(onDismissRequest = { showProgress == true }) {
                circularProgress(showProgress = showProgress)
            }
        }

        ProvideWindowInsets {
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(top = 20.dp)
                .navigationBarsWithImePadding()
                .verticalScroll(
                    rememberScrollState()
                ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly) {
                Column {
                    Text(text = "Class code")
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(value = classCode, onValueChange = {classCode = it},
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        ),
                        isError = false,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp), enabled = false, readOnly = true
                    )
                }
                outlinedTextField(
                    valueText = classVenue, onValueChange = {classVenue = it}, isError = false,
                    labelText = "Class venue", placeholderText = "",
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                )
                Column {
                    Text(text = "class Duration")
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(value = classDuration, onValueChange = {classDuration = it},
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        ),
                        isError = false,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp), enabled = false, readOnly = true
                    )
                }
                Column {
                    Text(text = "Date")
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(value = classDate, onValueChange = {classDate = it},
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        ),
                        isError = false,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp), enabled = false, readOnly = true
                    )
                }
                outlinedTextField(
                    valueText = week, onValueChange = {week = it}, isError = false,
                    labelText = "Semester week", placeholderText = "e.g 4",
                    keyboardType = KeyboardType.Number, imeAction = ImeAction.Next
                )

                outlinedTextField(
                    valueText = instructorID, onValueChange = { instructorID = it}, isError = false,
                    labelText = "Staff ID", placeholderText = "e.g 62314",
                    keyboardType = KeyboardType.Number, imeAction = ImeAction.Done
                )
                Column() {
                    Text(text = "Device ID")
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(value = instructorDeviceID, onValueChange = {instructorDeviceID = it},
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done
                        ),
                        isError = false,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp), enabled = false, readOnly = true
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                commonButton(onClick = {
                    showProgress = true
                    val authorization = AttendanceAuthorization(
                        classCode,classVenue,classDuration.toInt(),classDate,week,instructorID,instructorDeviceID)
                    viewmodel.addAuthorization(authorization)
                    viewmodel.feedback.observe(lifeCycleOwner){response->
                        if (response == "success"){
                            navController.navigate("feedback_Screen/$toplabel/$feedback" )
                        }
                        else{
                            val encodedResponse =
                                URLEncoder.encode(response, StandardCharsets.UTF_8.toString())
                            showProgress = false
                            navController.navigate("feedback_Screen/$toplabel/$encodedResponse")
                        }

                    }
                    },
                    label = "Authorize")
            }
        }

    }
}