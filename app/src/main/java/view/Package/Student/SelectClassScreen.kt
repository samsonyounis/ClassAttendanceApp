package view.Package.Student

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.companion.AssociationRequest
import android.companion.BluetoothDeviceFilter
import android.companion.CompanionDeviceManager
import android.content.ContentResolver
import android.content.Context
import android.content.IntentSender
import android.content.pm.PackageManager
import android.media.audiofx.BassBoost
import android.os.Build
import android.os.Handler
import android.provider.Settings
import android.telephony.TelephonyManager
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
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
import view.Package.ReusableFunctions.topRow
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SeclectClassScreen(navController: NavController) {
    val context = LocalContext.current
    val companionDeviceManager = context.getSystemService<CompanionDeviceManager>()
    //getting the unique android device ID here
    val secureId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID)

    var classCode by rememberSaveable { mutableStateOf("unknown") } // this is the lec device name
    var lec_DeviceID by rememberSaveable { mutableStateOf("unknown") }
    var stu_DeviceID by rememberSaveable { mutableStateOf("unknown") }
    var errorMessage by rememberSaveable { mutableStateOf("") }

    val bluetoothManager: BluetoothManager? = ContextCompat.getSystemService(context, BluetoothManager::class.java)
    val bluetoothAdapter: BluetoothAdapter? = bluetoothManager?.getAdapter()
    // buiding the device filter request here
    val deviceFilter:BluetoothDeviceFilter = BluetoothDeviceFilter.Builder().build()
    // creating an association request object
    val pairingRequest:AssociationRequest = AssociationRequest.Builder().addDeviceFilter(deviceFilter)
        .build()

    val contract = ActivityResultContracts.StartIntentSenderForResult()
    val activityResultLauncher =
        rememberLauncherForActivityResult(contract = contract) {
            it.data
                ?.getParcelableExtra<BluetoothDevice>(CompanionDeviceManager.EXTRA_DEVICE)
                ?.let { scanResult ->
                    val device = scanResult
                    if (ActivityCompat.checkSelfPermission(
                            context,
                            Manifest.permission.BLUETOOTH_CONNECT
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        if (device.name != null) {
                            classCode = device.name
                        }
                        lec_DeviceID = device.address
                    }
                }
            stu_DeviceID = secureId
        }
    Scaffold( modifier = Modifier.padding(16.dp),
        topBar = {
            topRow(text = "Choose available class", navController =navController)
        }
    ) {
        ProvideWindowInsets {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .navigationBarsWithImePadding(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column {
                    Text(text = "Class code/lecurer device name")
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(value = classCode, onValueChange = {classCode = it},
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
                        ),
                        isError = false,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp), enabled = false, readOnly = true
                    )
                }
                Column {
                    Text(text = "Lecturer device ID")
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(value = lec_DeviceID, onValueChange = {lec_DeviceID = it},
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text, imeAction = ImeAction.Done
                        ),
                        isError = false,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp), enabled = false, readOnly = true
                    )
                }
                Column {
                    Text(text = "Student device ID")
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(value = stu_DeviceID, onValueChange = {stu_DeviceID = it},
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
                        ),
                        isError = false,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp), enabled = false, readOnly = true
                    )
                }
                Text(text = errorMessage, color = Color.Red)
                Spacer(modifier = Modifier.height(10.dp))
                commonButton(onClick = {
                    if (companionDeviceManager != null) {
                        val list = companionDeviceManager.associations
                        Log.d("previously asscoiacted:", list.toString())
                        for (i in  list){
                            companionDeviceManager.disassociate(i)
                        }
                        companionDeviceManager.associate(
                            pairingRequest,
                            object : CompanionDeviceManager.Callback() {
                                override fun onDeviceFound(chooserLauncher: IntentSender?) {
                                    chooserLauncher?.let {
                                        val request = IntentSenderRequest.Builder(it).build()
                                        activityResultLauncher.launch(request)
                                    }

                                }

                                override fun onFailure(error: CharSequence?) {
                                    Toast.makeText(context,"No device found",Toast.LENGTH_LONG).show()
                                }
                            }, Handler()
                        )
                    }
                },
                    label = "choose class")
                commonButton(onClick = {
                    //validation here
                    /*
                    if (classCode.length<6 || classCode.length>6 || classCode.contains(" ")){
                        errorMessage = "* class code must be 6 character long with no white spaces"
                    }

                     */
                    if (classCode =="unknown" || lec_DeviceID == "unknown" || stu_DeviceID == "unknown"){
                        errorMessage = "* Choose a class before you continue"
                    }
                    else{
                        errorMessage = ""
                        //navigate to sign attendance screen
                        navController.navigate("signAttendance_Screen/$classCode/$lec_DeviceID/$stu_DeviceID")
                    }
                }, label = "Continue")
            }
            }
        }

    }