package view.Package.Lecturer

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.companion.CompanionDeviceManager
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import androidx.navigation.NavController
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsWithImePadding
import view.Package.ReusableFunctions.commonButton
import view.Package.ReusableFunctions.outlinedTextField
import view.Package.ReusableFunctions.topRow
import java.net.NetworkInterface

@Composable
fun input(navController: NavController){
    var classCode by rememberSaveable { mutableStateOf("") }
    var classDuration by rememberSaveable { mutableStateOf("") }

    var classCodeError by rememberSaveable { mutableStateOf("") }
    var classDurationError by rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current
    //instance of bluetooth manager and bluetooth adapter class
    val bluetoothManager: BluetoothManager? =
        ContextCompat.getSystemService(context, BluetoothManager::class.java)
    val bluetoothAdapter: BluetoothAdapter? = bluetoothManager?.getAdapter()

    val laucher = rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult(
    )){
    }

    Scaffold(
        topBar = {
            topRow(text = "Authorize class Attendance", navController = navController)
        }
    ) {
        ProvideWindowInsets {
            Column(horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, top = 32.dp)
                    .navigationBarsWithImePadding()
                    .verticalScroll(rememberScrollState())) {

                outlinedTextField(
                    valueText = classCode, onValueChange = {classCode = it}, isError = false,
                    labelText = "Class code", placeholderText = "e.g. SIT400",
                    keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                )
                Text(text = classCodeError,
                color = Color.Red)
                Column {
                    Text(text = "class Duration in hours")
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(value = classDuration, onValueChange = {classDuration = it},
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                        isError = false,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp), placeholder = { Text(text = "2")}
                    )
                }
                Text(text = classDurationError,
                color = Color.Red)
                Spacer(modifier = Modifier.height(10.dp))
                commonButton(onClick = {
                    //validating the inputs here
                    if (classCode.isBlank()){
                        classCodeError = "*Class code field is black"
                        classDurationError = ""
                    }
                    else if (classCode.length<6 || classCode.length>6 || classCode.contains(" ")){
                        classCodeError = "*class code must be 6 alphanumeric characters long with no white spaecs"
                        classDurationError = ""
                    }
                    else if (classDuration.isBlank()){
                        classDurationError = "*class duration field is black"
                        classCodeError = ""
                    }
                    else if (classDuration.toInt()<=0){
                        classDurationError = "class duration can not be less than one hour"
                        classCodeError = ""
                    }
                    else if (classDuration.toInt()>=4){
                        classDurationError = "class duration can not be more than 3 hours"
                        classCodeError = ""
                    }
                    else{
                        classCodeError = ""; classDurationError = ""
                        //if bluetooth adapter is null
                        if (bluetoothAdapter == null) {
                            Toast.makeText(context,"Device does not support bluetooth", Toast.LENGTH_LONG).show()
                        }
                        else{
                            //checking if the bluetooth is enabled or not
                            if (bluetoothAdapter?.isEnabled == false) {
                                // starting the intent to enable bluetooth
                                //checking if theses permission are allowed in manifest file.
                                if (ActivityCompat.checkSelfPermission(
                                        context,
                                        Manifest.permission.BLUETOOTH_CONNECT
                                    ) != PackageManager.PERMISSION_GRANTED
                                ) {
                                    //rename the device
                                    bluetoothAdapter?.setName(classCode.uppercase())
                                }
                                //enabling the bluetooth
                                val discoverableIntent: Intent = Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE).apply {
                                    putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 1*3600)
                                }
                                laucher.launch(discoverableIntent)
                            }
                            else{
                                //checking if theses permission are allowed in manifest file.
                                if (ActivityCompat.checkSelfPermission(
                                        context,
                                        Manifest.permission.BLUETOOTH_CONNECT
                                    ) != PackageManager.PERMISSION_GRANTED
                                ) {
                                    //rename the device
                                    bluetoothAdapter?.setName(classCode.uppercase())

                                }
                                val discoverableIntent: Intent = Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE).apply {
                                    putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 1*3600)
                                }
                                laucher.launch(discoverableIntent)

                            }
                            Log.d("name", bluetoothAdapter.name)
                            navController.navigate(
                                "authorizeAttendance_Screen/${classCode.uppercase()}/$classDuration")

                        }
                    }

                }, label = "Continue")

            }
        }
    }
}