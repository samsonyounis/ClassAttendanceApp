package view.Package

import Model.Enrollment
import Model.EnrollmentID
import Repository.Repository
import ViewModel.EnrollmentViewModel
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
fun enrollmentScreen(navController: NavController){
    var studentID by rememberSaveable { mutableStateOf("") }
    var classCode by rememberSaveable { mutableStateOf("") }
    var firstName by rememberSaveable { mutableStateOf("") }
    var middleName by rememberSaveable { mutableStateOf("") }
    var lastName by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }

    var studentIdError by rememberSaveable { mutableStateOf("") }
    var classCodeError by rememberSaveable { mutableStateOf("") }
    var firstnameError by rememberSaveable { mutableStateOf("") }
    var middlenameError by rememberSaveable { mutableStateOf("") }
    var lastnameError by rememberSaveable { mutableStateOf("") }
    var emailError by rememberSaveable { mutableStateOf("") }

    var toplabel by rememberSaveable { mutableStateOf("Enrollment") }
    var feedback by rememberSaveable { mutableStateOf("Successfully enrolled") }
    var showProgress by rememberSaveable { mutableStateOf(false) }

    val repository = Repository() // instance of the repository
    val viewmodel = EnrollmentViewModel(repository) // instance of the viewModel
    // innitailizing the lifeCycle owner of this compose screen
    val lifeCycleOwner: LifecycleOwner = LocalLifecycleOwner.current

    Scaffold(modifier = Modifier.padding(16.dp),
    topBar = {
        topRow(text = "Enrollment", navController = navController)
    }) {
        // showing the circular progress
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
                Text(text = studentIdError, color = Color.Red)
                outlinedTextField(
                    valueText = classCode, onValueChange = { classCode = it }, isError = false,
                    labelText = "Class code", placeholderText = "e.g SIT400",
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                )
                Text(text = classCodeError, color = Color.Red)

                outlinedTextField(
                    valueText = firstName, onValueChange = { firstName = it }, isError = false,
                    labelText = "First name", placeholderText = "",
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                )
                Text(text = firstnameError, color = Color.Red)
                outlinedTextField(
                    valueText = middleName, onValueChange = { middleName = it }, isError = false,
                    labelText = "Middle name", placeholderText = "",
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                )
                Text(text = middlenameError, color = Color.Red)
                outlinedTextField(
                    valueText = lastName, onValueChange = { lastName = it }, isError = false,
                    labelText = "Last name", placeholderText = "",
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                )
                Text(text = lastnameError, color = Color.Red)

                outlinedTextField(
                    valueText = email, onValueChange = { email = it }, isError = false,
                    labelText = "Email Address", placeholderText = "",
                    keyboardType = KeyboardType.Email, imeAction = ImeAction.Done
                )
                Text(text = emailError, color = Color.Red)
                Spacer(modifier = Modifier.height(10.dp))
                //onclick listener button
                commonButton(
                    onClick = {
                        //validating the user inputs here
                        if(studentID.isBlank()){
                            studentIdError = "* student ID is required"
                            classCodeError = ""; firstnameError = ""; middlenameError = "";lastnameError = "" ; emailError =""
                        }
                        else if (studentID.length<3 || studentID.length>3){
                            studentIdError = "* student ID must be 3 digits long"
                            classCodeError = ""; firstnameError = ""; middlenameError = "";lastnameError = "" ; emailError =""
                        }
                        else if(classCode.isBlank()){
                            classCodeError = "* specify the class to enroll in"
                            studentIdError = "";firstnameError = ""; middlenameError = "";lastnameError = "" ; emailError =""
                        }
                        else if(classCode.length<6 || classCode.length>6 || classCode.contains(" ")){
                            classCodeError ="* class code must be 6 alphanumeric characters long with no white spaecs"
                            studentIdError = "";firstnameError = ""; middlenameError = "";lastnameError = "" ; emailError =""
                        }
                        else if(firstName.isBlank()){
                            firstnameError = "* first name required"
                            studentIdError = ""; classCodeError = "";middlenameError = "";lastnameError = "" ; emailError =""
                        }
                        else if (!firstName.substring(0,1).matches("[a-zA-Z]".toRegex())){
                            firstnameError = "* name can not start with numbers, symbols or white spaces"
                            studentIdError = ""; classCodeError = "";middlenameError = "";lastnameError = "" ; emailError =""
                        }
                        else if(middleName.isBlank()){
                            middlenameError = "* middle name required"
                            studentIdError = ""; classCodeError = ""; firstnameError = "";lastnameError = "" ; emailError =""
                        }
                        else if (!middleName.substring(0,1).matches("[a-zA-Z]".toRegex())){
                            middlenameError = "* name can not start with numbers, symbols or white spaces"
                            studentIdError = ""; classCodeError = ""; firstnameError = "";lastnameError = "" ; emailError =""
                        }
                        else if(lastName.isBlank()){
                            lastnameError = "* last name required"
                            studentIdError = ""; classCodeError = ""; firstnameError = ""; middlenameError = "";emailError =""
                        }
                        else if (!lastName.substring(0,1).matches("[a-zA-Z]".toRegex())){
                            lastnameError = "* name can not start with numbers, symbols or white spaces"
                            studentIdError = ""; classCodeError = ""; firstnameError = ""; middlenameError = "";emailError =""
                        }
                        else if (email.isBlank()){
                            emailError = " email field is blank"
                            studentIdError = ""; classCodeError = ""; firstnameError = ""; middlenameError = "";lastnameError = ""
                        }
                        else if(!email.contains("@")){
                            emailError = "* invalid email format"
                            studentIdError = ""; classCodeError = ""; firstnameError = ""; middlenameError = "";lastnameError = ""
                        }
                        else if (!email.substring(0,1).matches("[a-zA-Z]".toRegex())){
                            emailError = "* email can not start with numbers, symbols or white spaces"
                            studentIdError = ""; classCodeError = ""; firstnameError = ""; middlenameError = "";lastnameError = ""
                        }
                        else{
                            studentIdError = ""; classCodeError = ""; firstnameError = ""; middlenameError = ""
                            lastnameError = "" ; emailError =""
                            showProgress = true
                            // creating the enrollmentID object and the enrollment Object
                            val enrollmentID = EnrollmentID(studentID,classCode.uppercase())
                            val enrollment = Enrollment(enrollmentID,firstName,middleName,lastName,email)
                            // calling the function in the view model and observing th value
                            viewmodel.AddEnrollment(enrollment)
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
                        }
                        ,
                    label = "Enroll"
                )
            }
        }
    }
}