package view.Package.Admin

import Model.Student
import Repository.Repository
import ViewModel.AddStudentViewModel
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
fun addStudentScreen(navController: NavController){
    var studentID by rememberSaveable { mutableStateOf("") }
    var studentFirstName by rememberSaveable { mutableStateOf("") }
    var studentMiddleName by rememberSaveable { mutableStateOf("") }
    var studentLastName by rememberSaveable { mutableStateOf("") }
    var school by rememberSaveable { mutableStateOf("") }
    var department by rememberSaveable { mutableStateOf("") }
    var studentCourse by rememberSaveable { mutableStateOf("") }

    var studentIdError by rememberSaveable { mutableStateOf("") }
    var firstnameError by rememberSaveable { mutableStateOf("") }
    var middlenameError by rememberSaveable { mutableStateOf("") }
    var lastnameError by rememberSaveable { mutableStateOf("") }
    var schoolError by rememberSaveable { mutableStateOf("") }
    var departmentError by rememberSaveable { mutableStateOf("") }
    var courseError by rememberSaveable { mutableStateOf("") }

    var toplabel by rememberSaveable { mutableStateOf("Add student") }
    var feedback by rememberSaveable { mutableStateOf(" Student added successfully") }
    var showProgress by rememberSaveable { mutableStateOf(false) }

    val repository = Repository()
    val viewmodel = AddStudentViewModel(repository)
    // innitailizing the lifeCycle owner of this compose screen
    val lifeCycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    Scaffold(modifier = Modifier.padding(16.dp),
        topBar = {
            topRow(text = "Add student", navController = navController)
        }) {
        // showing the circular progress
        if (showProgress == true) {
            Dialog(onDismissRequest = { showProgress == true }) {
                circularProgress(showProgress = showProgress)
            }
        }
// WindoewInset and scrollable column with input fields
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
                    valueText = studentID, onValueChange = {studentID = it}, isError = false,
                    labelText = "Student ID", placeholderText = "e.g 1423",
                    keyboardType = KeyboardType.Number, imeAction = ImeAction.Next
                )
                Text(text = studentIdError, color = Color.Red)
                outlinedTextField(
                    valueText = studentFirstName, onValueChange = {studentFirstName = it}, isError = false,
                    labelText = "Student First name", placeholderText = "",
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                )
                Text(text = firstnameError, color = Color.Red)
                outlinedTextField(
                    valueText = studentMiddleName, onValueChange = {studentMiddleName = it}, isError = false,
                    labelText = "Student Middle name", placeholderText = "",
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                )
                Text(text = middlenameError, color = Color.Red)
                outlinedTextField(
                    valueText = studentLastName, onValueChange = {studentLastName = it}, isError = false,
                    labelText = "Student Last name", placeholderText = "e.g Duncan",
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                )
                Text(text = lastnameError, color = Color.Red)
                outlinedTextField(
                    valueText = school, onValueChange = {school = it}, isError = false,
                    labelText = "School", placeholderText ="",
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                )
                Text(text = schoolError, color = Color.Red)
                outlinedTextField(
                    valueText = department, onValueChange = {department = it}, isError = false,
                    labelText = "Department", placeholderText = "",
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                )
                Text(text = departmentError, color = Color.Red)
                outlinedTextField(
                    valueText = studentCourse, onValueChange = {studentCourse = it}, isError = false,
                    labelText = "Student's Course", placeholderText = "e.g BIT",
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
                )
                Text(text = courseError, color = Color.Red)
                Spacer(modifier = Modifier.height(10.dp))
                commonButton(onClick = {
                    //validating inputs here
                    if(studentID.isBlank()){
                        studentIdError = "* student ID field is blank"
                        firstnameError = ""; middlenameError = ""; lastnameError = "";schoolError = ""; departmentError = ""; courseError = ""
                    }
                    else if(studentID.length<3 || studentID.length>3){
                        studentIdError = "* student ID must be 3 digits long"
                        firstnameError = ""; middlenameError = ""; lastnameError = "";schoolError = ""; departmentError = ""; courseError = ""
                    }
                    else if(studentFirstName.isBlank()){
                        firstnameError = "* first name required"
                        studentIdError = ""; middlenameError = ""; lastnameError = "";schoolError = ""; departmentError = ""; courseError = ""
                    }
                    else if (!studentFirstName.substring(0,1).matches("[a-zA-Z]".toRegex())){
                        firstnameError = "* name can not start with numbers, symbols or white spaces"
                        studentIdError = ""; middlenameError = ""; lastnameError = "";schoolError = ""; departmentError = ""; courseError = ""
                    }
                    else if(studentMiddleName.isBlank()){
                        middlenameError = "* middle name required"
                        studentIdError = ""; firstnameError = ""; lastnameError = "";schoolError = ""; departmentError = ""; courseError = ""
                    }
                    else if (!studentMiddleName.substring(0,1).matches("[a-zA-Z]".toRegex())){
                        middlenameError = "* name can not start with numbers, symbols or white spaces"
                        studentIdError = ""; firstnameError = "";lastnameError = "";schoolError = ""; departmentError = ""; courseError = ""
                    }
                    else if(studentLastName.isBlank()){
                        lastnameError = "*last name required"
                        studentIdError = ""; firstnameError = ""; middlenameError = "";schoolError = ""; departmentError = ""; courseError = ""
                    }
                    else if (!studentLastName.substring(0,1).matches("[a-zA-Z]".toRegex())){
                        lastnameError = "* name can not start with numbers, symbols or white spaces"
                        studentIdError = ""; firstnameError = ""; middlenameError = ""; schoolError = ""; departmentError = ""; courseError = ""
                    }
                    else if(school.isBlank()){
                        schoolError = "* school field is blank"
                        studentIdError = ""; firstnameError = ""; middlenameError = ""; lastnameError = "";departmentError = ""; courseError = ""
                    }
                    else if (!school.substring(0,1).matches("[a-zA-Z]".toRegex())){
                        schoolError = "* school can not start with numbers, symbols or white spaces"
                        studentIdError = ""; firstnameError = ""; middlenameError = ""; lastnameError = ""; departmentError = ""; courseError = ""
                    }
                    else if(department.isBlank()){
                        departmentError = "* department field is blank"
                        studentIdError = ""; firstnameError = ""; middlenameError = ""; lastnameError = "";schoolError = ""; courseError = ""
                    }
                    else if (studentCourse.isBlank()){
                        courseError = "* course field is blank"
                        studentIdError = ""; firstnameError = ""; middlenameError = ""; lastnameError = "";schoolError = ""; departmentError = ""
                    }
                    else{
                        studentIdError = ""; firstnameError = ""; middlenameError = ""; lastnameError = ""
                        schoolError = ""; departmentError = ""; courseError = ""
                        showProgress = true
                        val student = Student(
                            studentID, studentFirstName, studentMiddleName,
                            studentLastName,school,department, studentCourse,
                        )
                        viewmodel.AddStudent(student)
                        viewmodel.feeddback.observe(lifeCycleOwner) { response ->
                            if (response == "success") {
                                showProgress = false
                                navController.navigate("feedback_Screen/$toplabel/$feedback")
                            } else {
                                val encodedResponse =
                                    URLEncoder.encode(response, StandardCharsets.UTF_8.toString())
                                showProgress = false
                                navController.navigate("feedback_Screen/$toplabel/$encodedResponse")
                            }
                        }
                    }
                },
                    label = "Add")
            }
        }

    }
}