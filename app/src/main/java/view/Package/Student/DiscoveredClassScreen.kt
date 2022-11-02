package view.Package.Student

import ViewModel.BluetoothViewModel
import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import view.Package.ReusableFunctions.topRow

@Composable
fun AvialableClasses(navController: NavController){
    var label by rememberSaveable { mutableStateOf("Available classes") }
    val context = LocalContext.current
    val lifeCycleOwner: LifecycleOwner = LocalLifecycleOwner.current// innitailizing the lifeCycle owner
    //instance of bluetooth manager
    val bluetoothManager: BluetoothManager? = ContextCompat.getSystemService(context, BluetoothManager::class.java)
    val bluetoothAdapter: BluetoothAdapter? = bluetoothManager?.getAdapter()// getting the bluetooth adapter
    var devices:Set<BluetoothDevice?> =
        bluetoothAdapter?.bondedDevices as Set<BluetoothDevice?>//list of discovered devices
    var device:BluetoothDevice?//list of discovered device

    Scaffold(topBar = {
        topRow(text = label, navController = navController)
    }) {
        Column {
            for (i in devices) {
                if (i != null) {
                    if (ActivityCompat.checkSelfPermission(
                            context,
                            Manifest.permission.BLUETOOTH_CONNECT
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                    }
                    Text(text = i.name)
                    Text(text = i.address)
                    Spacer(modifier = Modifier.height(5.dp))
                }
            }
        }
    }
}