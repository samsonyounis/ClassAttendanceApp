package view.Package.Student

import Model.DiscoveredDevices
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
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.NavController
import view.Package.ReusableFunctions.topRow

@Composable
fun AvialableClasses(navController: NavController){
    var label by rememberSaveable { mutableStateOf("Available classes") }
    var devices:Set<BluetoothDevice?> = rememberSaveable { emptySet() }//list of discovered devices
    val context = LocalContext.current
    val lifeCycleOwner: LifecycleOwner = LocalLifecycleOwner.current// innitailizing the lifeCycle owner
    //instance of bluetooth manager
    val bluetoothManager: BluetoothManager? = ContextCompat.getSystemService(context, BluetoothManager::class.java)
    val bluetoothAdapter: BluetoothAdapter? = bluetoothManager?.getAdapter()// getting the bluetooth adapter


    // Create a BroadcastReceiver for ACTION_FOUND.
    val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            // retrieving the action to be performed and storing it in action val
            val action: String? = intent.action
            // when the action retrieved from the broadcast is == Bluetooth action found
            when(action) {
                BluetoothDevice.ACTION_FOUND -> {
                    // Discovery has found a device.
                    // Get the BluetoothDevice object and its info from the Intent.
                    val device: BluetoothDevice? = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                    if (device != null) {
                        val updated = devices.plus(device)
                        devices = devices.plus(device)
                    }
                    Log.d("Bluetooth", "onReceive: Device found")
                }
                BluetoothAdapter.ACTION_DISCOVERY_STARTED -> {
                    Log.d("Bluetooth", "onReceive: Started Discovery")
                }
                BluetoothAdapter.ACTION_DISCOVERY_FINISHED -> {
                    Log.d("Bluetooth", "onReceive: Finished Discovery")
                }
                }
            }
        }
    // Creating instance of event lifecyle owner
    DisposableEffect(lifeCycleOwner){
        val observer = LifecycleEventObserver{source, event ->
            Lifecycle.Event.ON_START
            // on below line we are registering our local broadcast manager.
            Toast.makeText(context,"BroadCast registered",Toast.LENGTH_LONG).show()
            LocalBroadcastManager.getInstance(context).registerReceiver(
                receiver, IntentFilter(BluetoothDevice.ACTION_FOUND)
            )
            if (bluetoothAdapter != null) {
                if (ActivityCompat.checkSelfPermission(
                        context, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED
                ) {
                    bluetoothAdapter.startDiscovery()
                }
            }
            Lifecycle.Event.ON_DESTROY
            //Toast.makeText(context,"BroadCast unregistered",Toast.LENGTH_LONG).show()
           // LocalBroadcastManager.getInstance(context).unregisterReceiver(receiver)
        }
        lifeCycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifeCycleOwner.lifecycle.removeObserver(observer)
        }
    }


    Scaffold(topBar = {
        topRow(text = label, navController = navController)
    }) {
        Column() {
            for (i in devices) {
                if (i != null) {
                    Text(text = i.toString())
                }
            }
        }
    }
}