package view.Package.Admin

import Model.Faculty_AttendanceReport
import ViewModel.AdminAttendanceReportViewModel
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import view.Package.ReusableFunctions.topRow

@OptIn(ExperimentalFoundationApi::class)
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
            attendanceRecords = viewmodel.attendanceRecords
            if (attendanceRecords.isEmpty() == true){
                serverRes = "No Attendance found for\n" +
                        "$classCode"
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

    Scaffold(modifier = Modifier.padding(top = 16.dp),
        topBar = {
            topRow(text = "Class Attendance Report\n\n" +
                    "$classCode", navController = navController)
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
        else{
            if (attendanceRecords.isEmpty() == false) {
                LazyVerticalGrid(cells = GridCells.Fixed(4),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.border(width = 3.dp, color = Color.Black, shape = RectangleShape),
                    contentPadding = PaddingValues(10.dp)) {

                    item {
                        Text(text = "student RegNo")
                    }
                    item {
                        Text(text = "student name")
                    }
                    item {
                        Text(text = "Hours attended")
                    }
                    item {
                        Text(text = "Total class hours")
                    }
                        for (i in attendanceRecords) {
                            item {
                                Text(text = i.stu_RegNo)
                            }
                            item {
                                Text(text = i.stu_name)
                            }
                            item {
                                Text(text = i.hoursAttended)
                            }
                            item {
                                Text(text = i.totalClassHours)
                            }
                        }

                }

            }
            else{
                Column(modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = serverRes)
                }
            }
            /*
            Column(modifier = Modifier
                .verticalScroll(rememberScrollState())
                .horizontalScroll(
                    rememberScrollState()
                )
                .fillMaxSize()
                .padding(top = 20.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(text = "$classCode Attendance Report", style = MaterialTheme.typography.h1)
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Text(text = "Student RegNo",
                        style = MaterialTheme.typography.h2)
                    Text(text = "Student name",
                        style = MaterialTheme.typography.h2)

                    Text(text = "Hours attended",
                        style = MaterialTheme.typography.h2)

                    Text(text = "Total class hours",
                        style = MaterialTheme.typography.h2)
                }
                if(attendanceRecords.isEmpty() == false){
                    for (i in attendanceRecords){
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Text(text = i.stu_RegNo)
                            Spacer(modifier = Modifier.width(130.dp))
                            Text(text = i.stu_name)
                            Spacer(modifier = Modifier.width(150.dp))
                            Text(text = i.hoursAttended)
                            Spacer(modifier = Modifier.width(100.dp))
                            Text(text = i.totalClassHours)
                        }
                    }
                }
                else{
                    Text(text = serverRes)
                }
            }
            */
        }


    }
}