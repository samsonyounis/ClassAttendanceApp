package view.Package

import android.Manifest
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
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import view.Package.ReusableFunctions.commonButton

@Composable
fun startScreen(navController: NavController){

    Scaffold(modifier = Modifier.padding(16.dp)) {
        Column(modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.ku_logo),
                contentDescription = "School logo"
            )
            //Spacer(modifier = Modifier.height(16.dp))
            Image(
                painter = painterResource(id = R.drawable.taking_attendance_picture),
                contentDescription = "Class session",
                modifier = Modifier
                    .height(400.dp)
                    .fillMaxWidth()
            )
            commonButton(onClick = {
                // Navigating to login screen
                navController.navigate("login_Screen")
            }, label = "Login")

            Text(text = "Don't have an account ?")

            commonButton(onClick = {
                // Navigating to request account screen
                navController.navigate("requestAccount_Screen")
            }, label = "Request account")
        }
    }
}