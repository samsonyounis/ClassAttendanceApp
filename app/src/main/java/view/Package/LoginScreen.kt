package view.Package.ReusableFunctions

import Model.LoginRequest
import Repository.Repository
import ViewModel.LoginViewModel
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration.Companion.Underline
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsWithImePadding
import view.Package.SessionManager
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun loginScreen(navController: NavController) {
    val obj = LocalContext.current
    var username by rememberSaveable{ mutableStateOf("") }
    var usernameErrorMsg by rememberSaveable { mutableStateOf("") }
    var usernameIsError by rememberSaveable { mutableStateOf(false) }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordErrorMsg by rememberSaveable { mutableStateOf("") }
    var passwordIsError by rememberSaveable { mutableStateOf(false) }
    var validationError by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var showProgress by rememberSaveable { mutableStateOf(false) }
    var toplabel by rememberSaveable { mutableStateOf("Login") }
    val  sessionManager = SessionManager(obj) // instance of session Manager
    val userType by rememberSaveable { mutableStateOf(sessionManager.fetchUserType()) }
    var user_type by rememberSaveable { mutableStateOf( userType.toString())}

    val repository = Repository() // instance of repository
    val viewmodel = LoginViewModel(repository) // instance of viewModel
    // innitailizing the lifeCycle owner of this compose screen
    val lifeCycleOwner: LifecycleOwner = LocalLifecycleOwner.current

    Scaffold(modifier = Modifier
        .padding(10.dp)
        .fillMaxSize(),
        topBar = {
            topRow(text = "Enter your Account credentials", navController = navController)
        }
    ) {
        //Showing circular progress while logging in the user
        if (showProgress == true) {
            Dialog(onDismissRequest = { showProgress == true }) {
                circularProgress(showProgress = showProgress)
            }
        }
//WindowInset and scrollable column with input fields
        ProvideWindowInsets {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState()).navigationBarsWithImePadding()
                    .fillMaxSize()
                    .padding(top = 50.dp, bottom = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(text = "Username/email address", modifier = Modifier.align(Alignment.Start))
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = username, onValueChange = { username = it },
                        placeholder = { Text(text = "enter your user name") },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text
                        ),
                        isError = usernameIsError
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = usernameErrorMsg, color = Color.Red)
                    Spacer(modifier = Modifier.height(10.dp))

                    Text(text = "password",modifier = Modifier.align(Alignment.Start))
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = password, onValueChange = { password = it },
                        placeholder = { Text(text = "") },
                        isError = passwordIsError,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done
                        ),
                        visualTransformation = if (passwordVisible) VisualTransformation.None
                        else PasswordVisualTransformation(),
                        trailingIcon = {
                            var image = if (passwordVisible){
                                Icons.Filled.Visibility
                            }
                            else{ Icons.Filled.VisibilityOff}
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(imageVector = image, contentDescription = "Visibility icon")
                            }
                        }

                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = passwordErrorMsg, color = Color.Red)
                }

                Column(modifier = Modifier.padding(top = 70.dp)) {
                    //Spacer(modifier = Modifier.height(16.dp))
                    commonButton(onClick = {
                        validationError = loginValidation(username,password)
                        if(validationError.startsWith('u')){
                            usernameErrorMsg = validationError
                            passwordErrorMsg = ""
                            usernameIsError = true
                            passwordIsError = false
                        }
                        else if (validationError.startsWith('p')){
                            passwordErrorMsg = validationError
                            usernameErrorMsg = ""
                            passwordIsError = true
                            usernameIsError = false
                        }
                        else{
                            showProgress = true
                            val loginRequest = LoginRequest(username,password,user_type)
                            viewmodel.LoginUser(loginRequest)
                            viewmodel.feedback.observe(lifeCycleOwner){response->
                                if (response != "success"){
                                    if (userType == "STUDENT"){
                                        showProgress = false
                                        navController.navigate("studentHome_Screen")
                                    }
                                    else if (userType == "STAFF"){
                                        showProgress = false
                                        navController.navigate("staffHome_Screen")
                                    }
                                    else {
                                        showProgress = false
                                        navController.navigate("adminHome_Screen")
                                    }
                                }
                                else{
                                    val encodedResponse = URLEncoder.encode(response, StandardCharsets.UTF_8.toString())
                                    showProgress = false
                                    navController.navigate("feedback_Screen/$toplabel/$encodedResponse" )
                                }
                            }
                        }
                    }, label = "Login")

                    Spacer(modifier = Modifier.height(20.dp))
                    Text(text = "Forgot password?",
                        modifier = Modifier
                            .clickable {
                                navController.navigate("recoverAccount_Screen")
                            }
                            .align(Alignment.CenterHorizontally),
                        textDecoration = Underline)
                }
            }
        }

    }
}