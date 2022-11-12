package view.Package

import Model.Student_attendanceReport
import ViewModel.StudentAttendanceReportViewModel
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import view.Package.ReusableFunctions.topRow
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.LifecycleOwner
import coil.compose.rememberImagePainter
import java.net.URLEncoder

@OptIn(ExperimentalFoundationApi::class)
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
                serverRes = "No Attendance found for\n\n" +
                        "$regNo"
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
            topRow(text = "Student Attendance Report\n\n" +
                    "Registration number: $regNo", navController = navController)
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
            if (attendanceRecords.isEmpty() == false) {

            LazyVerticalGrid(cells = GridCells.Fixed(3),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.border(width = 3.dp, color = Color.Black, shape = RectangleShape),
            contentPadding = PaddingValues(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)) {

                    item {
                        Text(text = "Class Code")
                    }
                    item {
                        Text(text = "Hours attended")
                    }
                    item {
                        Text(text = "Total class hours")
                    }
                    for (i in attendanceRecords) {
                        item {
                            Text(text = i.classCode)
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
            */
        }

    }
}