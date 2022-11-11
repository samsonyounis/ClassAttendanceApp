package view.Package

import Model.Student_attendanceReport
import ViewModel.StudentAttendanceReportViewModel
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import view.Package.ReusableFunctions.topRow
import androidx.compose.ui.*
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.LifecycleOwner

@Composable
fun StudentAttendanceReportScreen(navController: NavController,regNo:String,
                                  viewModel: StudentAttendanceReportViewModel){
    var regNo by rememberSaveable { mutableStateOf(regNo) }
    // variable to hold list of all attendance records for the student
    var attendanceRecords: List<Student_attendanceReport> = rememberSaveable{ listOf() }
    var showProgress by rememberSaveable { mutableStateOf(false)}
    var serverRes by rememberSaveable { mutableStateOf("Loading report") }
    // innitailizing the lifeCycle owner of this compose screen
    val lifeCycleOwner: LifecycleOwner = LocalLifecycleOwner.current

    viewModel.Get_StudentAttendanceReport(regNo)
    viewModel.feedback.observe(lifeCycleOwner){response->
        if (response.toString() == "success"){
            attendanceRecords = viewModel.attendanceRecords
            if (attendanceRecords.isEmpty() == true){
                serverRes = "No Attendance found"
                showProgress = true
            }
            else{
                showProgress = true
            }
        }
        else{
            serverRes = response.toString()
            showProgress = true
        }

    }
    Scaffold(modifier = Modifier.padding(16.dp),
        topBar = {
            topRow(text = "Student Attendance Report", navController = navController)
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
                .verticalScroll(rememberScrollState())
                .horizontalScroll(
                    rememberScrollState()
                )
                .fillMaxSize()
                .padding(top = 20.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(text = "Reg/No: $regNo", style = MaterialTheme.typography.h1)
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Text(text = "Class code", style = MaterialTheme.typography.h2)
                    Text(text = "Hours attended", style = MaterialTheme.typography.h2)
                    Text(text = "Total class hours", style = MaterialTheme.typography.h2)
                }
                if(attendanceRecords.isEmpty() == false){
                    for (i in attendanceRecords){
                        Row(modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(100.dp)) {
                            Text(text = i.classCode)

                            Text(text = i.hoursAttended)
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(text = i.totalClassHours)
                        }
                    }
                }
                else{
                    Text(text = serverRes)
                }
            }
        }

    }
}