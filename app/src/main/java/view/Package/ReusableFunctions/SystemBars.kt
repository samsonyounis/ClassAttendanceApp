package view.Package.ReusableFunctions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.res.colorResource
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import view.Package.R

@Composable
fun changeSystemBarColors(){
    var color =  colorResource(id = R.color.brand_color)
    // instance of systemUiController
    val systemUiController = rememberSystemUiController()
    SideEffect {
        // setting the status bar colors.
        systemUiController.setStatusBarColor(
            color,
            darkIcons = false
        )
        systemUiController.setNavigationBarColor(color, darkIcons = false)
    }
}