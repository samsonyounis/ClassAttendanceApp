package view.Package.Admin

import Model.UserAccount
import Repository.Repository
import ViewModel.CreateAccountViewModel
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
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
fun createAccountScreen(navController: NavController,username:String,password:String,accountType:String,
    userID:String) {
    var userID by rememberSaveable { mutableStateOf(userID) }
    var user_type by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf(username) }
    var password by rememberSaveable { mutableStateOf(password) }

    var userIdError by rememberSaveable { mutableStateOf("") }
    var emailError by rememberSaveable { mutableStateOf("") }
    var passwordError by rememberSaveable { mutableStateOf("") }
    var usertypeError by rememberSaveable { mutableStateOf("") }


    var toplabel by rememberSaveable { mutableStateOf("Create account") }
    var feedback by rememberSaveable { mutableStateOf(" Account created successfully") }
    var showProgress by rememberSaveable { mutableStateOf(false) }
    var expanded by rememberSaveable { mutableStateOf(false) }
    // creating arrow icon
    val icon = if (expanded) Icons.Filled.KeyboardArrowUp
    else Icons.Filled.KeyboardArrowDown

    val repository = Repository()
    val viewmodel = CreateAccountViewModel(repository)
    // innitailizing the lifeCycle owner of this compose screen
    val lifeCycleOwner: LifecycleOwner = LocalLifecycleOwner.current

    Scaffold(modifier = Modifier.padding(16.dp),
        topBar = {
            topRow(text = "Create Account", navController = navController)
        }) {
        //showing circular progress here while creating the user account.
        if (showProgress == true) {
            Dialog(onDismissRequest = { showProgress == true }) {
                circularProgress(showProgress = showProgress)
            }
        }
        //window inset and scrollable column with input fields
        ProvideWindowInsets {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 20.dp)
                    .navigationBarsWithImePadding()
                    .verticalScroll(
                        rememberScrollState()
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                outlinedTextField(
                    valueText = userID, onValueChange = { userID = it }, isError = false,
                    labelText = "User ID i.e Student ID/Staff ID", placeholderText = "",
                    keyboardType = KeyboardType.Number, imeAction = ImeAction.Done
                )
                Text(text = userIdError, color = Color.Red)
                    outlinedTextField(
                        valueText = email, onValueChange = { email = it }, isError = false,
                        labelText = "User email", placeholderText = "e.g. john@gmail.com",
                        keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                    )
                Text(text = emailError, color = Color.Red)
                outlinedTextField(
                    valueText = password, onValueChange = { password = it }, isError = false,
                    labelText = "Password", placeholderText = "e.g @384.dojfifi",
                    keyboardType = KeyboardType.Password, imeAction = ImeAction.Next
                )
                Text(text = passwordError, color = Color.Red)
// with icon and not expanded
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    OutlinedTextField(
                        value = user_type, onValueChange = { user_type = it },
                        modifier = Modifier
                            .fillMaxWidth(),
                        label = { Text("select account type") },
                        trailingIcon = {
                            Icon(icon, "contentDescription",
                                Modifier.clickable { expanded = !expanded })
                        }, readOnly = true
                    )
                    Text(text = usertypeError, color = Color.Red)
                    // drop down menu to select from
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        DropdownMenuItem(onClick = {
                            user_type = "STUDENT"
                            expanded = false
                        }) {
                            Text("Student account")
                        }
                        Divider()
                        DropdownMenuItem(onClick = {
                            user_type = "STAFF"
                            expanded = false
                        }) {
                            Text("staff account")
                        }
                        Divider()
                        DropdownMenuItem(onClick = {
                            user_type = "ADMIN"
                            expanded = false
                        }) {
                            Text("admin account")
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    commonButton(onClick = {
                        //validating the inputs here
                        if (userID.isBlank()){
                            userIdError = "* user ID required"
                            usertypeError = ""; emailError = ""; passwordError = ""
                        }
                        else if (userID.length<3 || userID.length>3){
                            userIdError = "* user ID must be 3 digits long"
                            usertypeError = ""; emailError = ""; passwordError = ""
                        }
                        else if (user_type.isBlank()){
                            usertypeError = "* specify the user type"
                            userIdError = ""; emailError = ""; passwordError = ""
                        }
                        else if (email.isBlank()){
                            emailError = "* Email is required"
                            userIdError = ""; usertypeError = ""; passwordError = ""
                        }
                        else if (!email.contains("@")){
                            emailError = "* invalid email format"
                            userIdError = ""; usertypeError = ""; passwordError = ""
                        }
                        else if(password.isBlank()){
                            passwordError = "set password for this account"
                            userIdError = ""; usertypeError = ""; emailError = ""
                        }
                        else if(password.length<6){
                            passwordError = "* weak password. it should be atleast 6 characters long"
                            userIdError = ""; usertypeError = ""; emailError = ""
                        }
                        else{
                            userIdError = ""; usertypeError = ""; emailError = ""; passwordError = ""
                            showProgress = true
                            val account = UserAccount(user_ID = userID, user_type = user_type, username = email,
                                password = password)
                            viewmodel.CreateAccount(account)
                            viewmodel.feedback.observe(lifeCycleOwner) { response ->
                                if (response.toString() == "success") {
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
                        label = "Create Account")
                }
            }
        }

    }
}