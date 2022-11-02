package view.Package

import Model.RecoverAccount
import Repository.Repository
import ViewModel.EnrollmentViewModel
import ViewModel.RecoverAccountViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
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
import view.Package.ReusableFunctions.circularProgress
import view.Package.ReusableFunctions.commonButton
import view.Package.ReusableFunctions.outlinedTextField
import view.Package.ReusableFunctions.topRow
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun recoverAccountScreen(navController: NavController){
    var userID by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var showProgress by rememberSaveable { mutableStateOf(false) }
    var toplabel by rememberSaveable { mutableStateOf("Recover account") }
    var feedback by rememberSaveable { mutableStateOf("An email has been sent: check your email to get your" +
            " account credentials") }
    val repository = Repository() // instance of the repository
    val viewmodel = RecoverAccountViewModel(repository) // instance of the viewModel
    // innitailizing the lifeCycle owner of this compose screen
    val lifeCycleOwner: LifecycleOwner = LocalLifecycleOwner.current

    Scaffold(modifier = Modifier.padding(16.dp),
        topBar = {
            Column() {
                topRow(text = "Recover your Account", navController = navController)
            }
        }
    ) {
        // showing the circular progress
        if (showProgress == true) {
            Dialog(onDismissRequest = { showProgress == true }) {
                circularProgress(showProgress = showProgress)
            }
        }
        Column(modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.height(40.dp))
            outlinedTextField(valueText = userID , onValueChange = {userID = it}, isError = false,
                labelText = "user ID i.e registration number/staff ID", placeholderText = "",
                keyboardType = KeyboardType.Text, imeAction = ImeAction.Next 
            )

            Spacer(modifier = Modifier.height(16.dp))
            outlinedTextField(valueText = email , onValueChange = {email = it}, isError = false,
                labelText = "Email address", placeholderText = "",
                keyboardType = KeyboardType.Email, imeAction = ImeAction.Done
            )
            Spacer(modifier = Modifier.height(50.dp))
            commonButton(onClick = {
                showProgress = true
                val request = RecoverAccount(userID,email)
                viewmodel.recoverAccount(request)
                viewmodel.feedback.observe(lifeCycleOwner){response->
                    if (response == "success"){
                        showProgress = false
                        navController.navigate("feedback_Screen/$toplabel/$feedback" )
                    }
                    else
                    {
                        showProgress = false
                        val encodedResponse = URLEncoder.encode(response, StandardCharsets.UTF_8.toString())
                        navController.navigate("feedback_Screen/$toplabel/$encodedResponse" )
                    }

                }
                 },
                label = "Recover")
        }
    }
}