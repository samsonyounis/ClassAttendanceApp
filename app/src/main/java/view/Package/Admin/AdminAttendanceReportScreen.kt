package view.Package.Admin

import Model.Faculty_AttendanceReport
import ViewModel.AdminAttendanceReportViewModel
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.R
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import view.Package.ReusableFunctions.topRow

@Composable
fun adminAttendanceReportScreen(navController: NavController, classCode:String,
       viewmodel:AdminAttendanceReportViewModel){

    var classCode by rememberSaveable { mutableStateOf(classCode) }
    // variable to hold list of all attendance records for the student
    var attendanceRecords: List<Faculty_AttendanceReport> = rememberSaveable{ listOf() }
    var showProgress by rememberSaveable { mutableStateOf(false) }
    var serverRes by rememberSaveable { mutableStateOf("Loading report") }
    // innitailizing the lifeCycle owner of this compose screen
    val lifeCycleOwner: LifecycleOwner = LocalLifecycleOwner.current

    viewmodel.Get_FacultyAttendanceReport(classCode)
    viewmodel.feedback.observe(lifeCycleOwner){response->
        if (response.toString() == "success"){
            serverRes = response.toString()
            attendanceRecords = viewmodel.attendanceRecords
            showProgress = true
        }
        else{
            serverRes = response.toString()
            showProgress = true
        }
    }

    Scaffold(modifier = Modifier.padding(16.dp),
        topBar = {
            topRow(text = "Class Attendance Report", navController = navController)
        }
    ) {
        // showing the circular progress
        if (showProgress == false) {
            Dialog(onDismissRequest = { showProgress == false }) {
                if (showProgress == false){
                    CircularProgressIndicator(
                        color =  colorResource(id = view.Package.R.color.brand_color),
                        strokeWidth = ProgressIndicatorDefaults.StrokeWidth)
                }
            }
        }

        Column(modifier = Modifier
            .verticalScroll(rememberScrollState())
            .horizontalScroll(
                rememberScrollState()
            )
            .fillMaxSize()
            .padding(top = 20.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Text(text = "Student RegNo",
                    style = MaterialTheme.typography.h2)

                Text(text = "Days present",
                    style = MaterialTheme.typography.h2)

                Text(text = "Days absent",
                    style = MaterialTheme.typography.h2)

                Text(text = "Total days",
                    style = MaterialTheme.typography.h2)
            }
            if(!attendanceRecords.isEmpty()){
                for (i in attendanceRecords){
                    Row(modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(100.dp)) {
                        Text(text = i.Stu_RegNo)

                        Text(text = i.daysPresent)

                        Text(text = i.daysAbsent)

                        Text(text = i.totalDays)
                    }
                }
            }
            else{
                Text(text = serverRes)
            }
            /*
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(100.dp)) {
                Text(text = "j31/234/2012")

                Text(text = "20")

                Text(text = "10")

                Text(text = "40")
            }
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(100.dp)) {
                Text(text = "j32/2342/2017")

                Text(text = "100")

                Text(text = "5")

                Text(text = "40")
            }
            */
        }
    }
}