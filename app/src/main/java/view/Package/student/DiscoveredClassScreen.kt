package view.Package.student

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
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
import view.Package.ReusableFunctions.commonButton
import view.Package.ReusableFunctions.topRow

@Composable
fun AvialableClasses(navController: NavController){
    var label by rememberSaveable { mutableStateOf("Available classes") }
    val context = LocalContext.current
    val lifeCycleOwner: LifecycleOwner = LocalLifecycleOwner.current// innitailizing the lifeCycle owner
    //instance of bluetooth manager
    val bluetoothManager: BluetoothManager? = ContextCompat.getSystemService(context, BluetoothManager::class.java)
    val bluetoothAdapter: BluetoothAdapter? = bluetoothManager?.getAdapter()// getting the bluetooth adapter
    var discoveredDevices:Set<BluetoothDevice?> = emptySet() // list of discovered devices

    DisposableEffect(lifeCycleOwner){
        val intentFilter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        val intentFilter1 = IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_STARTED)
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
                        Toast.makeText(context,"Device found",Toast.LENGTH_LONG).show()
                        Log.d("Bluetooth", "onReceive: Device found")
                    }
                    BluetoothAdapter.ACTION_DISCOVERY_STARTED->{
                        Toast.makeText(context,"started discovery",Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
        context.registerReceiver(receiver,intentFilter)
        context.registerReceiver(receiver,intentFilter1)

        onDispose {
            context.unregisterReceiver(receiver)
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
                        Text(text = i.name)
                        Text(text = i.address)
                        Spacer(modifier = Modifier.height(5.dp))
                    }

                }
            }
        }
    }
}