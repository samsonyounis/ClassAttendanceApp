package view.Package.Admin

import Model.UserAccount
import Repository.Repository
import ViewModel.CreateAccountViewModel
import ViewModel.ViewAccountRequestViewModel
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
fun createAccountScreen(navController: NavController,username:String,password:String,accountType:String,
    userID:String){
    var username by rememberSaveable { mutableStateOf(username) }
    var password by rememberSaveable { mutableStateOf(password) }
    var accountType by rememberSaveable { mutableStateOf(accountType) }
    var userID by rememberSaveable { mutableStateOf(userID) }
    var toplabel by rememberSaveable { mutableStateOf("Create account") }
    var feedback by rememberSaveable { mutableStateOf(" Accout created successfully") }
    var showProgress by rememberSaveable { mutableStateOf(false) }

    val repository = Repository()
    val viewmodel = CreateAccountViewModel(repository)
    // innitailizing the lifeCycle owner of this compose screen
    val lifeCycleOwner: LifecycleOwner = LocalLifecycleOwner.current

    Scaffold(modifier = Modifier.padding(16.dp),
        topBar = {
            topRow(text = "Create Account", navController = navController)
        }) {
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
                valueText = username, onValueChange = {username = it}, isError = false,
                labelText = "User name", placeholderText = "e.g. osman.younis",
                keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
            )
            outlinedTextField(
                valueText = password, onValueChange = {password = it}, isError = false,
                labelText = "Password", placeholderText = "",
                keyboardType = KeyboardType.Password, imeAction = ImeAction.Next
            )
            outlinedTextField(
                valueText = accountType, onValueChange = {accountType = it}, isError = false,
                labelText = "Account type i.e student/staff account", placeholderText = "",
                keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
            )
            outlinedTextField(
                valueText = userID, onValueChange = {userID = it}, isError = false,
                labelText = "User ID i.e Student RegNo/Staff ID", placeholderText = "",
                keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
            )
            commonButton(onClick = {
                showProgress = true
                val account = UserAccount(username,password,accountType,userID)
                viewmodel.CreateAccount(account)
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
                }
                ,
                label = "Create Account")
        }
    }
}