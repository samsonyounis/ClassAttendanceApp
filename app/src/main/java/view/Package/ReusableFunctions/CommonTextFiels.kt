package view.Package.ReusableFunctions

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun outlinedTextField(valueText:String, onValueChange:(String)->Unit,
                            isError:Boolean, labelText:String, placeholderText:String,
                            keyboardType: KeyboardType, imeAction: ImeAction,
){
    Column {
        Text(text = labelText)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = valueText, onValueChange = onValueChange,
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = imeAction
            ),
            placeholder = { Text(text = placeholderText) },
            isError = isError,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp)
        )
    }
}