package view.Package.Admin

import Model.Class
import Repository.Repository
import ViewModel.AddClassViewModel
import androidx.compose.foundation.layout.*
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
                outlinedTextField(
                    valueText = classTitle, onValueChange = {classTitle = it}, isError = false,
                    labelText = "Class name/Title", placeholderText = "e.g information security",
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                )
                outlinedTextField(
                    valueText = classDuration, onValueChange = {classDuration = it}, isError = false,
                    labelText = "Class Duration in hours/semester", placeholderText = "e.g 36",
                    keyboardType = KeyboardType.Number, imeAction = ImeAction.Next
                )
                outlinedTextField(
                    valueText = classInstructorID, onValueChange = {classInstructorID = it}, isError = false,
                    labelText = "Class Instructor ID", placeholderText = "",
                    keyboardType = KeyboardType.Number, imeAction = ImeAction.Done
                )
                Spacer(modifier = Modifier.height(10.dp))
                // onclick listener button
                commonButton(onClick = {
                    showProgress = true
                    //creating class object
                    val singleClass = Class(classCode.uppercase(),classTitle,classDuration,classInstructorID)
                    // calling the function in the view model and observing the value
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
}