package view.Package.Admin

import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
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
fun SpecifyAdminAttendanceReportScreen(navController: NavController){
    var classCode by rememberSaveable { mutableStateOf("") }
    var classCodeError by rememberSaveable { mutableStateOf("") }
    var label by rememberSaveable { mutableStateOf("Enter class code") }
    var topLabel by rememberSaveable { mutableStateOf("Class Attendance Report") }
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
                valueText = classCode , onValueChange = { classCode = it}, isError = false,
                labelText = label, placeholderText ="" ,
                keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
            )
            Text(text = classCodeError, color = Color.Red)

            commonButton(onClick = {
                //validating the inputs here
                if (classCode.isBlank()){
                    classCodeError = "*Class code field is black"
                }
                else if (classCode.length<6){
                    classCodeError = "*class code must consist of 6 Alphanumeric characters"
                }
                else if(classCode.length>6){
                    classCodeError = "*class code can not contain white spaces and must be 6 characters long"
                }
                else if (classCode.contains(" ")){
                    classCodeError = "*Class code can't contain white spaces"
                }
                else
                {
                    navController.navigate("adminAttendanceReport_Screen/${classCode.uppercase()}")}
                },
                label = "View")
        }
    }
}