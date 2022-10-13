package view.Package

import Model.DiscoveredDevices
import android.Manifest
import android.app.Activity.RESULT_OK
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.NavController
import view.Package.ReusableFunctions.topRow

@Composable
fun studentHomeScreen(navController: NavController){
    var expanded by rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current
    val  sessionManager = SessionManager(context) // instance of session Manager
    // innitailizing the lifeCycle owner of this compose screen
    val lifeCycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    val bluetoothManager: BluetoothManager? =
        ContextCompat.getSystemService(context, BluetoothManager::class.java)
    val bluetoothAdapter: BluetoothAdapter? = bluetoothManager?.getAdapter()

    val laucher = rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult(
    )){
    }

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
                    DropdownMenuItem(onClick = {navController.navigate("enrollment_Screen") }) {
                        Text("Enroll into class")
                    }
                    Divider()
                    DropdownMenuItem(onClick = {
                        if (bluetoothAdapter == null) {
                            Toast.makeText(context,"Device does not support bluetooth", Toast.LENGTH_LONG).show()
                        }
                        else{
                            Toast.makeText(context,"Device supports bluetooth", Toast.LENGTH_LONG).show()
                            if (bluetoothAdapter?.isEnabled == false) {
                                val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                                laucher.launch(enableBtIntent)
                            }
                            navController.navigate("avialableClasses_Screen")
                        }

                    }) {
                        Text("Sign class attendance")
                    }
                    Divider()
                    DropdownMenuItem(onClick = {
                        navController.navigate("specifyStudentAttendanceReport_Screen") }) {
                        Text("View attendance report")
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