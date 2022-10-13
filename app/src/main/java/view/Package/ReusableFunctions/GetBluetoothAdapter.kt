package view.Package.ReusableFunctions

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Intent
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService

@Composable
fun GetBluetoothAdapter(){
    val context = LocalContext.current
    val bluetoothManager: BluetoothManager? =
        getSystemService(context, BluetoothManager::class.java)
    val bluetoothAdapter: BluetoothAdapter? = bluetoothManager?.getAdapter()
    if (bluetoothAdapter == null) {
        Toast.makeText(context,"Device does not support bluetooth",Toast.LENGTH_LONG)
    }
    else{
        Toast.makeText(context,"Device supports bluetooth",Toast.LENGTH_LONG)
    }

}