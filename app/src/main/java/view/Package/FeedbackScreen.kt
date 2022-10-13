package view.Package

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import view.Package.ReusableFunctions.feedbackScreen

@Composable
fun FeedbackScreen(topLabel:String, feedbackMessage:String,navController: NavController){
    //var topLabel by rememberSaveable { mutableStateOf("Stop attendance") }
   // var feebackMessage by rememberSaveable { mutableStateOf("Class attendance closed successfully") }
    feedbackScreen(label = topLabel, feedbackMessage = feedbackMessage, navController = navController)
}