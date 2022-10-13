package view.Package.ReusableFunctions

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.ui.*
import androidx.compose.ui.platform.LocalContext
import view.Package.SessionManager

@Composable
fun feedbackScreen(label:String, feedbackMessage:String, navController: NavController){
    val obj = LocalContext.current
    val  sessionManager = SessionManager(obj) // instance of session Manager

    Scaffold(modifier = Modifier.padding(16.dp),
        topBar = {
            topRow(text = label, navController = navController)
            Spacer(modifier = Modifier.height(30.dp))
        }
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxSize()) {
            Text(text = feedbackMessage,
            style = MaterialTheme.typography.h2)

            commonButton(onClick = {
                 if (sessionManager.fetchUserType() == "student"){
                     navController.popBackStack()
                     navController.navigate("studentHome_Screen")
                 }
                else if (sessionManager.fetchUserType()=="staff"){
                    navController.popBackStack()
                    navController.navigate("staffHome_Screen")
                 }
                else{
                    navController.popBackStack()
                    navController.navigate("adminHome_Screen")
                 }
            }, label = "OK")
        }
    }
}