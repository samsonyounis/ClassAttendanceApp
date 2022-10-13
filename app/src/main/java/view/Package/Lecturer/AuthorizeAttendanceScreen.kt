package view.Package.Lecturer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
fun authorizeAttendanceScreen(navController: NavController){
    var classCode by rememberSaveable { mutableStateOf("") }
    var instructorDeviceID by rememberSaveable { mutableStateOf("") }
    var classDurationHrs by rememberSaveable { mutableStateOf("") }
    var semesterWeek by rememberSaveable { mutableStateOf("") }
    var date by rememberSaveable { mutableStateOf("") }
    var instructorID by rememberSaveable { mutableStateOf("") }
    var toplabel by rememberSaveable { mutableStateOf("Attendance authorization") }
    var feedback by rememberSaveable { mutableStateOf("Authorization done succesfully") }
    Scaffold(modifier = Modifier.padding(16.dp),
        topBar = {
            topRow(text = "Authorize class Attendance", navController = navController)
        }) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp)
            .verticalScroll(
                rememberScrollState()
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly) {
            outlinedTextField(
                valueText = classCode, onValueChange = {classCode = it}, isError = false,
                labelText = "Class code", placeholderText = "e.g. SIT400",
                keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
            )
            outlinedTextField(
                valueText = instructorDeviceID, onValueChange = {instructorDeviceID = it}, isError = false,
                labelText = "Lecturer's Device ID", placeholderText = "",
                keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
            )
            outlinedTextField(
                valueText = classDurationHrs, onValueChange = {classDurationHrs = it}, isError = false,
                labelText = "Class duration in hours", placeholderText = "e.g 3",
                keyboardType = KeyboardType.Number, imeAction = ImeAction.Next
            )
            outlinedTextField(
                valueText = semesterWeek, onValueChange = {semesterWeek = it}, isError = false,
                labelText = "Semester week", placeholderText = "e.g 4",
                keyboardType = KeyboardType.Number, imeAction = ImeAction.Next
            )
            outlinedTextField(
                valueText = date, onValueChange = { date = it}, isError = false,
                labelText = "Date", placeholderText = "",
                keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
            )
            outlinedTextField(
                valueText = instructorID, onValueChange = { instructorID = it}, isError = false,
                labelText = "Staff ID", placeholderText = "e.g 62314",
                keyboardType = KeyboardType.Number, imeAction = ImeAction.Done
            )
            commonButton(onClick = { navController.navigate("feedback_Screen/$toplabel/$feedback" ) },
                label = "Authorize")
        }
    }
}