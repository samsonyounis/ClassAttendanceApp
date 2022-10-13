package view.Package.Admin

import Model.Class
import Repository.Repository
import ViewModel.AddClassViewModel
import ViewModel.EnrollmentViewModel
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
fun addClassScreen(navController: NavController){
    var classCode by rememberSaveable { mutableStateOf("") }
    var classTitle by rememberSaveable { mutableStateOf("") }
    var classDuration by rememberSaveable { mutableStateOf("") }
    var classInstructor by rememberSaveable { mutableStateOf("") }
    var toplabel by rememberSaveable { mutableStateOf("Add class") }
    var feedback by rememberSaveable { mutableStateOf(" Class added successfully") }
    var showProgress by rememberSaveable { mutableStateOf(false) }

    val repository = Repository()
    val viewmodel = AddClassViewModel(repository)
    // innitailizing the lifeCycle owner of this compose screen
    val lifeCycleOwner: LifecycleOwner = LocalLifecycleOwner.current

    Scaffold(modifier = Modifier.padding(16.dp),
    topBar = {
        topRow(text = "Add class", navController = navController)
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
                valueText = classCode, onValueChange = {classCode = it}, isError = false,
                labelText = "Class code", placeholderText = "e.g SIT400",
                keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
            )
            outlinedTextField(
                valueText = classTitle, onValueChange = {classTitle = it}, isError = false,
                labelText = "Class name/Title", placeholderText = "e.g information security",
                keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
            )
            outlinedTextField(
                valueText = classDuration, onValueChange = {classDuration = it}, isError = false,
                labelText = "Class Duration in hours/semester", placeholderText = "e.g 36",
                keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
            )
            outlinedTextField(
                valueText = classInstructor, onValueChange = {classInstructor = it}, isError = false,
                labelText = "Class Instructor", placeholderText = "e.g Duncan",
                keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
            )
            commonButton(onClick = {
                showProgress = true
                val singleClass = Class(classCode,classTitle,classDuration,classInstructor)
                viewmodel.AddClass(singleClass)
                viewmodel.feedback.observe(lifeCycleOwner){response->
                    if (response == "success"){
                        showProgress = false
                        navController.navigate("feedback_Screen/$toplabel/$feedback" )
                    }
                    else{
                        val encodedResponse = URLEncoder.encode(response, StandardCharsets.UTF_8.toString())
                        showProgress = false
                        navController.navigate("feedback_Screen/$toplabel/$encodedResponse" )
                    }
                }
                 },
                label = "Add")
        }
    }
}

@Preview
@Composable
fun PreviewAddClassScreen() {
    addClassScreen(navController = rememberNavController())
}