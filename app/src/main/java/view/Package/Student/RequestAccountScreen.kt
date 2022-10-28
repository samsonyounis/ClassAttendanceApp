package view.Package

import Model.AccountRequest
import Repository.Repository
import ViewModel.RequestAccountViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
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
fun requestAccountScreen(navController: NavController){
    var regNo by rememberSaveable { mutableStateOf("") }
    var firstName by rememberSaveable { mutableStateOf("") }
    var lastName by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var phone by rememberSaveable { mutableStateOf("") }
    var toplabel by rememberSaveable { mutableStateOf("Account Request") }
    var feedback by rememberSaveable { mutableStateOf("Request successfully sent") }
    var showProgress by rememberSaveable { mutableStateOf(false) }

    val repository = Repository()
    val viewmodel = RequestAccountViewModel(repository)
    // innitailizing the lifeCycle owner of this compose screen
    val lifeCycleOwner: LifecycleOwner = LocalLifecycleOwner.current

    Scaffold(modifier = Modifier.padding(16.dp),
        topBar = {
            topRow(text = "Request Account", navController = navController)
        }
    ) {
        //showing circular progress while sending request
        if (showProgress == true) {
            Dialog(onDismissRequest = { showProgress == true }) {
                circularProgress(showProgress = showProgress)
            }
        }
        // scrollable and window inset column
        ProvideWindowInsets {
            Column(modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxSize()
                .navigationBarsWithImePadding()
                .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally) {
                outlinedTextField(
                    valueText =regNo , onValueChange = {regNo = it}, isError = false ,
                    labelText = "Student ID",
                    placeholderText = "e.g J31/123423/2019",
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                )
                outlinedTextField(
                    valueText =firstName , onValueChange = {firstName = it}, isError = false ,
                    labelText = "First name", placeholderText = "",
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                )
                outlinedTextField(
                    valueText =lastName , onValueChange = {lastName = it}, isError = false ,
                    labelText = "Last Name", placeholderText = "",
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                )
                outlinedTextField(
                    valueText =email , onValueChange = {email = it}, isError = false ,
                    labelText = "Email Address", placeholderText = "",
                    keyboardType = KeyboardType.Email, imeAction = ImeAction.Next
                )
                outlinedTextField(
                    valueText =phone , onValueChange = {phone = it}, isError = false ,
                    labelText = "Phone number", placeholderText = "",
                    keyboardType = KeyboardType.Phone, imeAction = ImeAction.Next
                )
                // send request button
                commonButton(onClick = {
                    showProgress = true
                    val request = AccountRequest(regNo,firstName,lastName, email,phone)
                    viewmodel.AddAccountRequest(request)
                    viewmodel.response1.observe(lifeCycleOwner){response->
                        if (response == "success"){
                            showProgress = false
                            navController.navigate("feedback_Screen/$toplabel/$feedback" )
                        }
                        else{
                            val string = URLEncoder.encode(response, StandardCharsets.UTF_8.toString())
                            showProgress = false
                            navController.navigate("feedback_Screen/$toplabel/$string")
                        }
                    }
                },
                    label = "Send Request")
                }

            }
        }
    }