package view.Package.ReusableFunctions

import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun getAttendanceSpecification(navController: NavController, topLabel:String){
    var regNo by rememberSaveable { mutableStateOf("") }
    var regNoError by rememberSaveable { mutableStateOf("") }
    var label by rememberSaveable { mutableStateOf("Enter your registration number") }
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
               keyboardType = KeyboardType.Number, imeAction = ImeAction.Done
           )
           Text(text = regNoError, color = Color.Red)
           
           commonButton(onClick = {
               if (regNo.isBlank()){
                   regNoError = "*Registration number field is blank"
               }
               else if (regNo.length<3 || regNo.length>3){
                   regNoError = "Registration number must be 3 digits long"
               }
               else
               {
                   regNoError = ""
                   val regNo = URLEncoder.encode(regNo, StandardCharsets.UTF_8.toString())
                   navController.navigate("studentAttendanceReport_Screen/$regNo")
               }
                                  },
               label = "View")
       }
    }
}