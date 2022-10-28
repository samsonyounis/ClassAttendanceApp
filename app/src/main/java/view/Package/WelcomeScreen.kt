package view.Package

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun welcomeScreen(navController: NavController){
    // expanded state of the drop down menu.
    val obj = LocalContext.current
    var expanded by rememberSaveable { mutableStateOf(false) }
    val  sessionManager = SessionManager(obj) // instance of session Manager
    Scaffold(
        topBar = {
            Column() {
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
                    Text(text = "Proceed as?",
                    style = MaterialTheme.typography.h2,
                    modifier = Modifier.align(Alignment.CenterHorizontally))
                    Divider()
                    DropdownMenuItem(onClick = {
                        navController.navigate("start_Screen")
                        sessionManager.saveUserType("STUDENT")
                    }) {
                        Text("Student")
                    }
                    Divider()
                    DropdownMenuItem(onClick = {
                        navController.navigate("login_Screen")
                        sessionManager.saveUserType("STAFF")
                    }) {
                        Text("Faculty staff")
                    }
                    Divider()
                    DropdownMenuItem(onClick = {
                        navController.navigate("login_Screen")
                        sessionManager.saveUserType("ADMIN")
                    }) {
                        Text("Admin/Faculty")
                    }
                }

            }
        },
        modifier = Modifier.padding(10.dp)
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.ku_logo),
                contentDescription = "School logo"
            )
            Spacer(modifier = Modifier.height(16.dp))
            Image(
                painter = painterResource(id = R.drawable.taking_attendance_picture),
                contentDescription = "Class session",
                modifier = Modifier
                    .height(400.dp)
                    .fillMaxWidth()
            )
        }
        }

}