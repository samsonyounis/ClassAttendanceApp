package view.Package

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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import view.Package.ReusableFunctions.commonButton
import view.Package.ReusableFunctions.outlinedTextField
import view.Package.ReusableFunctions.topRow

@Composable
fun recoverAccountScreen(navController: NavController){
    var regNo by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var toplabel by rememberSaveable { mutableStateOf("Recover account") }
    var feedback by rememberSaveable { mutableStateOf("We have sent password to your email. " +
            "check your email.") }
    Scaffold(modifier = Modifier.padding(16.dp),
        topBar = {
            Column() {
                topRow(text = "Recover your Account", navController = navController)
            }
        }
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.height(40.dp))
            outlinedTextField(valueText = regNo , onValueChange = {regNo = it}, isError = false,
                labelText = "Registration number", placeholderText = "e.g j31/1234/2019",
                keyboardType = KeyboardType.Text, imeAction = ImeAction.Next 
            )

            Spacer(modifier = Modifier.height(16.dp))
            outlinedTextField(valueText = email , onValueChange = {email = it}, isError = false,
                labelText = "Email address", placeholderText = "",
                keyboardType = KeyboardType.Email, imeAction = ImeAction.Done
            )
            Spacer(modifier = Modifier.height(50.dp))
            commonButton(onClick = {navController.navigate("feedback_Screen/$toplabel/$feedback" ) },
                label = "Recover")
        }
    }
}