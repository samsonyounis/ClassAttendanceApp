package view.Package.ReusableFunctions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun topRow(text:String,navController: NavController){
    Row(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start) {
        // backward arrow
        IconButton(onClick = {navController.navigateUp()}) {
            Icon(imageVector = Icons.Filled.ArrowBack,
                contentDescription = "move back")
        }
        Text(text = text,
            modifier = Modifier
                .align(Alignment.CenterVertically),
        style = MaterialTheme.typography.h2)
    }
}