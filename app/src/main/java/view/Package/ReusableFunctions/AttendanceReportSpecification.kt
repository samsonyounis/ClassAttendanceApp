package view.Package.ReusableFunctions

import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import androidx.compose.ui.*
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun getAttendanceSpecification(navController: NavController, topLabel:String){
    var regNo by rememberSaveable { mutableStateOf("") }
    var label by rememberSaveable { mutableStateOf("Registration number") }
    Scaffold(modifier = Modifier.padding(16.dp),
        topBar = {
            topRow(text =topLabel , navController = navController)
            Spacer(modifier = Modifier.height(30.dp))
        }
    ) {
       Column(modifier = Modifier.fillMaxSize(),
       horizontalAlignment = Alignment.CenterHorizontally,
       verticalArrangement = Arrangement.SpaceEvenly) {
           outlinedTextField(
               valueText =regNo , onValueChange = {regNo = it}, isError = false,
               labelText = label, placeholderText ="" ,
               keyboardType = KeyboardType.Text, imeAction = ImeAction.Done 
           )
           
           commonButton(onClick = {
               val regNo = URLEncoder.encode(regNo, StandardCharsets.UTF_8.toString())
               navController.navigate("studentAttendanceReport_Screen/$regNo")},
               label = "View")
       }
    }
}