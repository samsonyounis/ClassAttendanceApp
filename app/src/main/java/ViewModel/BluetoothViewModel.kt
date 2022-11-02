package ViewModel

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class BluetoothViewModel():ViewModel() {
    var devices: MutableLiveData<Set<BluetoothDevice?>> = MutableLiveData() //list of discovered devices

    @Composable
    fun bluetooth() {
        val context = LocalContext.current
        val lifeCycleOwner: LifecycleOwner =
            LocalLifecycleOwner.current// innitailizing the lifeCycle owner
        //instance of bluetooth manager
        val bluetoothManager: BluetoothManager? =
            ContextCompat.getSystemService(context, BluetoothManager::class.java)
        val bluetoothAdapter: BluetoothAdapter? =
            bluetoothManager?.getAdapter()// getting the bluetooth adapter
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
            return
        }
        if (bluetoothAdapter != null) {
            devices.value = bluetoothAdapter.bondedDevices
        }
    }

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
                        val device: BluetoothDevice? =
                            intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                        if (device != null) {
                         //   devices.plus(device)
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
}