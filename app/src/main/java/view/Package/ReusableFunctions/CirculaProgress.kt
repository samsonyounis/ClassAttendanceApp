package view.Package.ReusableFunctions

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import view.Package.R

@Composable
fun circularProgress(showProgress:Boolean){
    if (showProgress == true){
        CircularProgressIndicator(
            color = colorResource(id = R.color.brand_color),
            strokeWidth = ProgressIndicatorDefaults.StrokeWidth)
    }
}