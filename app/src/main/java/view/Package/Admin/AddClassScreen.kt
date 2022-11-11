package view.Package.Admin

import Model.Class
import Repository.Repository
import ViewModel.AddClassViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
fun addClassScreen(navController: NavController){
    var classCode by rememberSaveable { mutableStateOf("") }
    var classTitle by rememberSaveable { mutableStateOf("") }
    var classDuration by rememberSaveable { mutableStateOf("") }
    var classInstructorID by rememberSaveable { mutableStateOf("") }

    var classCodeError by rememberSaveable { mutableStateOf("") }
    var classTitleError by rememberSaveable { mutableStateOf("") }
    var classDurationError by rememberSaveable { mutableStateOf("") }
    var classInstructorIdError by rememberSaveable { mutableStateOf("") }

    var toplabel by rememberSaveable { mutableStateOf("Add class") }
    var feedback by rememberSaveable { mutableStateOf(" Class added successfully") }
    var showProgress by rememberSaveable { mutableStateOf(false) }

    val repository = Repository() // instance of repository
    val viewmodel = AddClassViewModel(repository) // instance of view model
    // innitailizing the lifeCycle owner of this compose screen
    val lifeCycleOwner: LifecycleOwner = LocalLifecycleOwner.current

    Scaffold(modifier = Modifier.padding(16.dp),
    topBar = {
        topRow(text = "Add class", navController = navController)
    }) {
        // showing the circular progress while adding the class
        if (showProgress == true) {
            Dialog(onDismissRequest = { showProgress == true }) {
                circularProgress(showProgress = showProgress)
            }
        }

        // WindowInset and scrollable column with the input fields
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
                outlinedTextField(
                    valueText = classCode, onValueChange = {classCode = it}, isError = false,
                    labelText = "Class code", placeholderText = "e.g SIT400",
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                )
                Text(text = classCodeError, color = Color.Red)
                outlinedTextField(
                    valueText = classTitle, onValueChange = {classTitle = it}, isError = false,
                    labelText = "Class name/Title", placeholderText = "e.g information security",
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                )
                Text(text = classTitleError, color = Color.Red)
                outlinedTextField(
                    valueText = classDuration, onValueChange = {classDuration = it}, isError = false,
                    labelText = "Class Duration in hours/semester", placeholderText = "e.g 36",
                    keyboardType = KeyboardType.Number, imeAction = ImeAction.Next
                )
                Text(text = classDurationError, color = Color.Red)
                outlinedTextField(
                    valueText = classInstructorID, onValueChange = {classInstructorID = it}, isError = false,
                    labelText = "Class Instructor ID", placeholderText = "",
                    keyboardType = KeyboardType.Number, imeAction = ImeAction.Done
                )
                Text(text = classInstructorIdError, color = Color.Red)
                Spacer(modifier = Modifier.height(10.dp))
                // onclick listener button
                commonButton(onClick = {
                    //validating the inputs here
                    if (classCode.isBlank()){
                        classCodeError = "* class code field is blank"
                        classDurationError ="" ;classTitleError = "" ;classInstructorIdError =""
                    }
                    else if (classCode.length<6 || classCode.length>6 || classCode.contains(" ")){
                        classCodeError = "* class code must be 6 alphanumeric long with no white spaecs"
                        classDurationError ="" ;classTitleError = "" ;classInstructorIdError =""
                    }
                    else if (classTitle.isBlank()){
                        classTitleError = "* class title field is blank"
                        classCodeError ="" ;classDurationError ="" ;classInstructorIdError =""
                    }
                    else if (classTitle.length<10 || classTitle.length>30){
                        classTitleError = "* class title must be atleast 10 character long and at most 30 characters long"
                        classCodeError ="" ;classDurationError ="" ;classInstructorIdError =""
                    }
                    else if (!classTitle.substring(0,1).matches("[a-zA-Z]".toRegex())){
                        classTitleError = "class name/title must not start with number, whitespace or symbols"
                        classCodeError ="" ;classDurationError ="" ;classInstructorIdError =""
                    }
                    else if(classDuration.isBlank()){
                        classDurationError = "* class duration field is blank"
                        classCodeError ="" ;classTitleError = "" ;classInstructorIdError =""
                    }
                    else if (classDuration.toInt()>40 || classDuration.toInt()<30){
                        classDurationError = "* class hours per semester must be at least 30 hrs and atmost 40 hrs"
                        classCodeError ="" ;classTitleError = "" ;classInstructorIdError =""
                    }
                    else if (classInstructorID.isBlank()){
                        classInstructorIdError = "* instructor ID field is blank"
                        classCodeError ="" ;classDurationError ="" ;classTitleError = ""
                    }
                    else if (classInstructorID.length<3 || classInstructorID.length>3){
                        classInstructorIdError = "* instructor ID must be 3 digits long"
                        classCodeError ="" ;classDurationError ="" ;classTitleError = ""
                    }
                    else {
                        classCodeError ="" ;classDurationError ="" ;classTitleError = "" ;classInstructorIdError =""
                        showProgress = true // showing circular progress
                        //creating class object
                        val singleClass = Class(classCode.uppercase(), classTitle, classDuration, classInstructorID
                        )
                        // calling the function in the view model and observing the value
                        viewmodel.AddClass(singleClass)
                        viewmodel.feedback.observe(lifeCycleOwner) { response ->
                            if (response.toString() == "success") {
                                showProgress = false
                                navController.navigate("feedback_Screen/$toplabel/$feedback")
                            } else {
                                val encodedResponse =
                                    URLEncoder.encode(response, StandardCharsets.UTF_8.toString())
                                showProgress = false
                                navController.navigate("feedback_Screen/$toplabel/${encodedResponse}")
                            }
                        }
                    }
                },
                    label = "Add")
            }
        }

    }
}