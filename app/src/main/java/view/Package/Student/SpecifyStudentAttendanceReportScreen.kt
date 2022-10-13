package view.Package

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import view.Package.ReusableFunctions.getAttendanceSpecification

@Composable
fun SpecifyStudentAttendanceReportScreen(navController: NavController){
    var topLabel by rememberSaveable { mutableStateOf("View Attendance") }
    getAttendanceSpecification(navController = navController, topLabel = topLabel)
}