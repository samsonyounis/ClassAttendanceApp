package view.Package

import Model.AccountRequest
import Repository.Repository
import ViewModel.RequestAccountViewModel
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
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsWithImePadding
import okio.ByteString.Companion.decodeBase64
import view.Package.ReusableFunctions.circularProgress
import view.Package.ReusableFunctions.commonButton
import view.Package.ReusableFunctions.outlinedTextField
import view.Package.ReusableFunctions.topRow
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun requestAccountScreen(navController: NavController){
    var regNo by rememberSaveable { mutableStateOf("") }
    var firstName by rememberSaveable { mutableStateOf("") }
    var lastName by rememberSaveable { mutableStateOf("") }
    var emailAddress by rememberSaveable { mutableStateOf("") }
    var phone by rememberSaveable { mutableStateOf("") }
    var accountType by rememberSaveable { mutableStateOf("") }
    var toplabel by rememberSaveable { mutableStateOf("Account Request") }
    var feedback by rememberSaveable { mutableStateOf("Request successfully sent") }
    var expanded by rememberSaveable { mutableStateOf(false) }
    var showProgress by rememberSaveable { mutableStateOf(false) }
    // creating arrow icon
   val icon = if (expanded) Icons.Filled.KeyboardArrowUp
    else Icons.Filled.KeyboardArrowDown
    val repository:Repository = Repository()
    val viewmodel = RequestAccountViewModel(repository)
    // innitailizing the lifeCycle owner of this compose screen
    val lifeCycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    val obj = LocalContext.current
    Scaffold(modifier = Modifier.padding(16.dp),
        topBar = {
            topRow(text = "Request Account", navController = navController)
        }
    ) {
        if (showProgress == true) {
            Dialog(onDismissRequest = { showProgress == true }) {
                circularProgress(showProgress = showProgress)
            }
        }
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
                    labelText = "Institution Identification number i.e. Student ID or Staff ID",
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
                    valueText =emailAddress , onValueChange = {emailAddress = it}, isError = false ,
                    labelText = "Email Address", placeholderText = "",
                    keyboardType = KeyboardType.Email, imeAction = ImeAction.Next
                )
                outlinedTextField(
                    valueText =phone , onValueChange = {phone = it}, isError = false ,
                    labelText = "Phone number", placeholderText = "",
                    keyboardType = KeyboardType.Phone, imeAction = ImeAction.Next
                )
                // Create an Outlined Text Field
                // with icon and not expanded
                Column(modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    OutlinedTextField(
                        value = accountType,
                        onValueChange = { accountType = it },
                        modifier = Modifier
                            .fillMaxWidth(),
                        label = {Text("select account type")},
                        trailingIcon = {
                            Icon(icon,"contentDescription",
                                Modifier.clickable { expanded = !expanded })
                        }, readOnly = true
                    )
                    // drop down menu to select from
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.fillMaxWidth()) {
                        DropdownMenuItem(onClick = {
                            accountType = "student account"
                            expanded = false
                        }) {
                            Text("Student account")
                        }
                        Divider()
                        DropdownMenuItem(onClick = {
                            accountType = "staff account"
                            expanded = false
                        }) {
                            Text("staff account")
                        }
                    }
                }
                commonButton(onClick = {
                    showProgress = true
                    val request = AccountRequest(regNo,firstName,lastName, emailAddress,phone,accountType)
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