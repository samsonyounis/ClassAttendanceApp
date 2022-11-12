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
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsWithImePadding
import view.Package.ReusableFunctions.circularProgress
import view.Package.ReusableFunctions.commonButton
import view.Package.ReusableFunctions.outlinedTextField
import view.Package.ReusableFunctions.topRow
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*

@Composable
fun authorizeAttendanceScreen(navController: NavController, classCode:String, classDuration:String){
    var classCode by rememberSaveable { mutableStateOf(classCode) }
    var classVenue by rememberSaveable { mutableStateOf("") }
    val dateFormatter = SimpleDateFormat("dd-MM-yyyy")
    val currentDate = dateFormatter.format(Date())
    var classDate by rememberSaveable { mutableStateOf(currentDate) }

    val timeFormatter = SimpleDateFormat("HH:mm a")
    val currentTime = timeFormatter.format(Date())
    var classTime by rememberSaveable { mutableStateOf(currentTime) }
    var week by rememberSaveable { mutableStateOf("") }
    var classDuration by rememberSaveable { mutableStateOf(classDuration) }
    var instructorID by rememberSaveable { mutableStateOf("") }

    var classVenueError by rememberSaveable { mutableStateOf("") }
    var weekError by rememberSaveable { mutableStateOf("") }
    var instructorIDError by rememberSaveable { mutableStateOf("") }

    val toplabel by rememberSaveable { mutableStateOf("Attendance authorization") }
    val feedback by rememberSaveable {  mutableStateOf("Authorization done succesfully") }
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
                Text(text = classVenueError, color = Color.Red)
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
                Column {
                    Text(text = "Time")
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(value = classTime, onValueChange = {classTime = it},
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
                Text(text = weekError, color = Color.Red)

                outlinedTextField(
                    valueText = instructorID, onValueChange = { instructorID = it}, isError = false,
                    labelText = "Staff ID", placeholderText = "",
                    keyboardType = KeyboardType.Number, imeAction = ImeAction.Done
                )
                Text(text = instructorIDError, color = Color.Red)

                Spacer(modifier = Modifier.height(10.dp))
                commonButton(onClick = {
                    //validating inputs here
                    if(classVenue.isBlank()){
                        classVenueError = "* class venue field is required"
                        weekError = ""; instructorIDError = ""
                    }
                    else if (week.isBlank()){
                        weekError = "* specify the semester week"
                        classVenueError = ""; instructorIDError = ""
                    }
                    else if (week.toInt()<1){
                        weekError = "* semester week can not be less than one"
                        classVenueError = ""; instructorIDError = ""
                    }
                    else if (instructorID.isBlank()){
                        instructorIDError = "*staff ID field is required"
                        classVenueError = ""; weekError = ""
                    }
                    else if (instructorID.length<3 || instructorID.length>3){
                        instructorIDError = "* staff ID must be 3 digits long"
                        classVenueError = ""; weekError = ""
                    }
                    else{
                        classVenueError = ""; weekError = ""; instructorIDError = ""
                        showProgress = false
                        val authorization = AttendanceAuthorization(
                            classCode.uppercase(),classVenue.uppercase(),classDuration.toInt(),
                            classDate, classTime, week,instructorID)
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
                    }
                    },
                    label = "Authorize")
            }
        }

    }
}