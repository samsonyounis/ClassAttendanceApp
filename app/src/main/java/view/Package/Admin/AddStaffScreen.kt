package view.Package.Admin

import Model.Staff
import Repository.Repository
import ViewModel.AddClassViewModel
import ViewModel.AddStaffViewModel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import view.Package.ReusableFunctions.circularProgress
import view.Package.ReusableFunctions.commonButton
import view.Package.ReusableFunctions.outlinedTextField
import view.Package.ReusableFunctions.topRow
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun addLecturerScreen(navController: NavController){
    var staffID by rememberSaveable { mutableStateOf("") }
    var staffFirstName by rememberSaveable { mutableStateOf("") }
    var staffMiddleName by rememberSaveable { mutableStateOf("") }
    var staffLastName by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var phone by rememberSaveable { mutableStateOf("") }
    var toplabel by rememberSaveable { mutableStateOf("Add Staff") }
    var feedback by rememberSaveable { mutableStateOf(" Lecturer added successfully") }
    var showProgress by rememberSaveable { mutableStateOf(false) }

    val repository = Repository()
    val viewmodel = AddStaffViewModel(repository)
    // innitailizing the lifeCycle owner of this compose screen
    val lifeCycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    Scaffold(modifier = Modifier.padding(16.dp),
        topBar = {
            topRow(text = "Add lecturer", navController = navController)
        }) {

        // showing the circular progress
        if (showProgress == true) {
            Dialog(onDismissRequest = { showProgress == true }) {
                circularProgress(showProgress = showProgress)
            }
        }

        Column(modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp)
            .verticalScroll(
                rememberScrollState()
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly) {
            outlinedTextField(
                valueText = staffID, onValueChange = {staffID = it}, isError = false,
                labelText = "Lecturer/Staff ID", placeholderText = "e.g. 452028",
                keyboardType = KeyboardType.Number, imeAction = ImeAction.Next
            )
            outlinedTextField(
                valueText = staffFirstName, onValueChange = {staffFirstName = it}, isError = false,
                labelText = "Lecturer's First name", placeholderText = "",
                keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
            )
            outlinedTextField(
                valueText = staffMiddleName, onValueChange = {staffMiddleName = it}, isError = false,
                labelText = "Lecturer's Middle name", placeholderText = "",
                keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
            )
            outlinedTextField(
                valueText = staffLastName, onValueChange = {staffLastName = it}, isError = false,
                labelText = "Lecturer's Last name", placeholderText = "e.g Duncan",
                keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
            )
            outlinedTextField(
                valueText = email, onValueChange = { email = it}, isError = false,
                labelText = "Email address", placeholderText = "e.g Jkmuriki@gmail.com",
                keyboardType = KeyboardType.Email, imeAction = ImeAction.Next
            )
            outlinedTextField(
                valueText = phone, onValueChange = { phone = it}, isError = false,
                labelText = "Phone number", placeholderText = "e.g 0708562314",
                keyboardType = KeyboardType.Phone, imeAction = ImeAction.Done
            )
            commonButton(onClick = {
                showProgress = true
                val staff =
                    Staff(staffID, staffFirstName, staffMiddleName, staffLastName, email, phone)
                viewmodel.AddStaff(staff)
                viewmodel.feedback.observe(lifeCycleOwner) { response ->
                    if (response == "success") {
                        showProgress = false
                        navController.navigate("feedback_Screen/$toplabel/$feedback")
                    } else {
                        showProgress = false
                        val encodedResponse =
                            URLEncoder.encode(response, StandardCharsets.UTF_8.toString())
                        navController.navigate("feedback_Screen/$toplabel/$encodedResponse")
                    }
                }
            },
                label = "Add")
        }
    }
}

@Preview
@Composable
fun PrevAddLec() {
    addLecturerScreen(navController = rememberNavController())
}