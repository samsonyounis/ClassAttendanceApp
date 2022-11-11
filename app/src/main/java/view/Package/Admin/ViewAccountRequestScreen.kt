package view.Package.Admin

import Model.AccountRequest
import ViewModel.ViewAccountRequestViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ProgressIndicatorDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import view.Package.R
import view.Package.ReusableFunctions.commonButton
import view.Package.ReusableFunctions.topRow
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun viewAccountRequestScreen(navController: NavController,viewmodel: ViewAccountRequestViewModel){

// variable to hold list of all account requests
    var requestList: List<AccountRequest> = rememberSaveable{ listOf() }
    var serverRes = rememberSaveable {("loading requests") }
    var username by rememberSaveable { mutableStateOf("e.g. sam@gmail.com") }
    var password by rememberSaveable { mutableStateOf("57tygjg") }
    var accountType by rememberSaveable { mutableStateOf("student account") }
    var userID by rememberSaveable { mutableStateOf("j31/4532/2018") }

    var showProgress by rememberSaveable { mutableStateOf(false) }
    // innitailizing the lifeCycle owner of this compose screen
    val lifeCycleOwner: LifecycleOwner = LocalLifecycleOwner.current

    viewmodel.Get_AccountRequests()
    viewmodel.feedback.observe(lifeCycleOwner) { feedback ->
        if (feedback.toString() == "success") {
            requestList = viewmodel.requestList
            if (requestList.isEmpty() == true) {
                serverRes = "No account requests found"
                showProgress = true
            }
            else{
                showProgress = true
            }

        } else {
            serverRes = feedback.toString()
            showProgress = true
        }

    }

    Scaffold(modifier = Modifier.padding(16.dp),
    topBar = {
    topRow(text = "Account Requests", navController = navController)
    }
    ) {
        // showing the circular progress
        if (showProgress == false) {
            Dialog(onDismissRequest = { showProgress == false }) {
                if (showProgress == false){
                    CircularProgressIndicator(
                        color = colorResource(id = R.color.brand_color),
                        strokeWidth = ProgressIndicatorDefaults.StrokeWidth)
                }
            }
        }
        else{
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(top = 20.dp)
                .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)) {
                if(requestList.isEmpty() == false){
                    for (i in requestList){
                        Column(modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(8.dp)) {

                            Row(modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Image(imageVector = Icons.Filled.Person, contentDescription = "User",
                                    modifier = Modifier
                                        .align(Alignment.CenterVertically)
                                        .size(100.dp)
                                        .clip(
                                            CircleShape
                                        ))
                                Column {
                                    Text(text = "student ID: ${i.student_ID}")
                                    Text(text = "student Firstname: ${i.student_Firstname}")
                                    Text(text = "student Lastname: ${i.student_lastname}")
                                    Text(text = "Email: ${i.email}")
                                    Text(text = "Phone: ${i.phone}")
                                }
                            }
                            commonButton(onClick = {
                                val userId = URLEncoder.encode(i.student_ID, StandardCharsets.UTF_8.toString())
                                navController.navigate(
                                    "createAccount_Screen/${i.email}/$userId/${i.email}/$userId")
                            }, label = "Create Account")
                        }
                    }
                }

                else{
                    Column(modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = serverRes)
                        commonButton(onClick = {
                           // val userId = URLEncoder.encode(userID, StandardCharsets.UTF_8.toString())
                            navController.navigateUp()
                                //"createAccount_Screen/$username/$password/$accountType/$userId")
                        }, label = "Ok")
                    }
                }

            }
        }
    }
}