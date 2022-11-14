package view.Package

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import view.Package.ReusableFunctions.commonButton

@Composable
fun startScreen(navController: NavController){

    Scaffold(modifier = Modifier.padding(16.dp)) {
        Column(modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.ku_logo),
                contentDescription = "School logo"
            )
            //Spacer(modifier = Modifier.height(16.dp))
            Image(
                painter = painterResource(id = R.drawable.taking_attendance_picture),
                contentDescription = "Class session",
                modifier = Modifier
                    .height(400.dp)
                    .fillMaxWidth()
            )
            commonButton(onClick = {
                // Navigating to login screen
                navController.navigate("login_Screen/Enter your student ID")
            }, label = "Login")

            Text(text = "Don't have an account ?")

            commonButton(onClick = {
                // Navigating to request account screen
                navController.navigate("requestAccount_Screen")
            }, label = "Request account")
        }
    }
}