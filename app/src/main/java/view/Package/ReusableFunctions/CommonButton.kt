package view.Package.ReusableFunctions

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import view.Package.R

@Composable
fun commonButton(onClick:()->Unit, label:String){
Button(onClick = onClick,
colors = ButtonDefaults.buttonColors(
    backgroundColor = colorResource(id = R.color.brand_color),
    contentColor = Color.White
),
modifier = Modifier.height(60.dp).width(200.dp),
shape = RoundedCornerShape(15.dp)) {
    Text(text = label)
}
}