package view.Package.Admin

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
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun adminHomeScreen(navController: NavController){
    var expanded by rememberSaveable { mutableStateOf(false) }
    var username by rememberSaveable { mutableStateOf("egsamgmailcom") }
    var password by rememberSaveable { mutableStateOf("123") }
    var accountType by rememberSaveable { mutableStateOf("student account") }
    var userID by rememberSaveable { mutableStateOf("j31/4532/2018") }
    val context = LocalContext.current
    val  sessionManager = SessionManager(context) // instance of session Manager

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
                        navController.navigate("addClass_Screen")
                    }) {
                        Text("Add class")
                    }
                    Divider()
                    DropdownMenuItem(onClick = {
                        navController.navigate("addStudent_Screen")
                    }) {
                        Text("Add student")
                    }
                    Divider()
                    DropdownMenuItem(onClick = {
                        navController.navigate("addLecturer_Screen")
                    }) {
                        Text("Add lecturer")
                    }
                    Divider()
                    DropdownMenuItem(onClick = {
                        navController.navigate("viewAccountRequest_Screen")
                    }) {
                        Text("View account requests")
                    }
                    Divider()
                    DropdownMenuItem(onClick = {
                        val userId = URLEncoder.encode(userID, StandardCharsets.UTF_8.toString())
                        navController.navigate("createAccount_Screen/$username/$password/$accountType/$userId")
                    }) {
                        Text("Create user account")
                    }
                    Divider()
                    DropdownMenuItem(onClick = {
                        navController.navigate("specifyAdminAttendanceReport_Screen")
                    }) {
                        Text("View attendance report")
                    }
                    Divider()
                    DropdownMenuItem(onClick = {
                        // delete the current session and return the user to the login screen
                        sessionManager.deleteUserType()
                        sessionManager.deleteUserID()
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