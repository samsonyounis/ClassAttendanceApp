package view.Package

import Model.RecoverAccount
import Repository.Repository
import ViewModel.RecoverAccountViewModel
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

    var userIdError by rememberSaveable { mutableStateOf("") }
    var emailError by rememberSaveable { mutableStateOf("") }

    var showProgress by rememberSaveable { mutableStateOf(false) }
    val toplabel by rememberSaveable { mutableStateOf("Recover account") }
    val feedback by rememberSaveable { mutableStateOf("An email has been sent: check your email to get your" +
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
                keyboardType = KeyboardType.Number, imeAction = ImeAction.Next
            )
            Text(text = userIdError, color = Color.Red)

            Spacer(modifier = Modifier.height(16.dp))
            outlinedTextField(valueText = email , onValueChange = {email = it}, isError = false,
                labelText = "Email address", placeholderText = "",
                keyboardType = KeyboardType.Email, imeAction = ImeAction.Done
            )
            Text(text = emailError, color = Color.Red)
            Spacer(modifier = Modifier.height(50.dp))
            commonButton(onClick = {
                //validating the user inputs here
                if (userID.isBlank()){
                    userIdError ="* User ID is required"
                    emailError = ""
                }
                else if (userID.length<3 || userID.length>3){
                    userIdError = "* User ID must be 3 digits long"
                    emailError = ""
                }
                else if (email.isBlank()){
                    emailError = " email field is blank"
                    userIdError = ""
                }
                else if(!email.contains("@")){
                    emailError = "* invalid email format"
                    userIdError = ""
                }
                else if (!email.substring(0,1).matches("[a-zA-Z]".toRegex())){
                    emailError = "* email can not start with numbers, symbols or white spaces"
                    userIdError = ""
                }
                else{
                    userIdError = ""; emailError = ""
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
                }
                 },
                label = "Recover")
        }
    }
}