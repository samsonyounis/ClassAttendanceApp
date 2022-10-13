package view.Package.Admin

import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

            commonButton(onClick = {
                navController.navigate("adminAttendanceReport_Screen/$classCode")},
                label = "View")
        }
    }
}