package view.Package.Student

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothStatusCodes
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
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
    var devices:Set<BluetoothDevice?> = bluetoothAdapter?.bondedDevices as Set<BluetoothDevice?>//list of discovered devices
    var discoveredDevices:Set<BluetoothDevice?> = emptySet() // list of discovered devices
    DisposableEffect(lifeCycleOwner){
        val intentFilter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        // Create a BroadcastReceiver for ACTION_FOUND.
        val receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                // retrieving the action to be performed and storing it in action val
                val action: String? = intent.action
                // when the action retrieved from the broadcast is == Bluetooth action found
                when (action) {
                    BluetoothDevice.ACTION_FOUND -> {
                        // Discovery has found a device.
                        // Get the BluetoothDevice object and its info from the Intent.
                        val device: BluetoothDevice? = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                        if (device != null) {
                            discoveredDevices.plus(device)
                        }
                        Toast.makeText(context,"Devidce found",Toast.LENGTH_LONG)
                        Log.d("Bluetooth", "onReceive: Device found")
                    }
                }
            }
        }
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_CREATE) {
                context.registerReceiver(receiver,intentFilter)
            } else if (event == Lifecycle.Event.ON_STOP) {
                context.unregisterReceiver(receiver)
            }
        }
        lifeCycleOwner.lifecycle.addObserver(observer)
        context.registerReceiver(receiver,intentFilter)
        onDispose {
            lifeCycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Scaffold(topBar = {
        topRow(text = label, navController = navController)
    }) {
        Column {
            for (i in discoveredDevices) {
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