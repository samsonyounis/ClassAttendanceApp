package view.Package.Lecturer

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import view.Package.ReusableFunctions.topRow
import view.Package.SessionManager

@Composable
fun staffHomeScreen(navController: NavController){
    var expanded by rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current
    val  sessionManager = SessionManager(context) // instance of session Manager class

    Scaffold(
        topBar = {
            Column(modifier = Modifier.padding(16.dp)) {
                topRow(text = "Select Options", navController = navController)
                Spacer(modifier = Modifier.height(15.dp))
                Divider(thickness = 2.dp, color = Color.Black)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    IconButton(
                        onClick = { expanded = !expanded},
                        modifier = Modifier.size(70.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Menu, contentDescription = "Menu",
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = "Options",
                        style = MaterialTheme.typography.h1,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    Divider()
                    DropdownMenuItem(onClick = {
                        navController.navigate("AttendanceAuthInput_Screen")
                    }) {
                        Text("Authorize class attendance")
                    }
                    Divider()
                    DropdownMenuItem(onClick = {
                        navController.navigate("stopAttendance_Screen")
                    }) {
                        Text("Stop class attendnace")
                    }
                    Divider()
                    DropdownMenuItem(onClick = {
                        navController.navigate("specifyAdminAttendanceReport_Screen")
                    }) {
                        Text("View attendnace report")
                    }
                    Divider()
                    DropdownMenuItem(onClick = {
                        // delete the current session and return the user to the login screen
                        sessionManager.deleteUserType()
                        navController.navigate("welcome_Screen")
                    }) {
                        Text("Logout")
                    }
                }

            }
        }
    ) {

    }
}