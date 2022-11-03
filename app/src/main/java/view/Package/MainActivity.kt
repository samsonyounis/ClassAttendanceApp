package view.Package

import Repository.Repository
import ViewModel.AdminAttendanceReportViewModel
import ViewModel.StudentAttendanceReportViewModel
import ViewModel.ViewAccountRequestViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import view.Package.Admin.*
import view.Package.Lecturer.*
import view.Package.ReusableFunctions.changeSystemBarColors
import view.Package.ReusableFunctions.loginScreen
import view.Package.Student.AvialableClasses
import view.Package.Student.signAttendanceScreen
import view.Package.ui.theme.Class_Attendance_AppTheme

class MainActivity : ComponentActivity() {
    // creating Variable of type navController
    private lateinit var navController: NavHostController
    //instance of repository
    val repository = Repository()
    // instance of viewAccountRequestViewmodel
    val viewmodel = ViewAccountRequestViewModel(repository)
    //instance of students attendance report viewModel
    val StudentReportViewModel = StudentAttendanceReportViewModel(repository)
    //instance of admin attendance report viewModel
    val AdminReportViewModel = AdminAttendanceReportViewModel(repository)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Class_Attendance_AppTheme {
                // A surface container using the 'background' color from the theme
                changeSystemBarColors()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    // initializing the navController
                    navController = rememberNavController()
                    // setting up the NavHost
                    NavHost(
                        navController = navController,
                        startDestination = "welcome_Screen"
                    ){
                        composable("welcome_Screen"
                        ) {
                            welcomeScreen(navController)
                        }
                        composable("start_Screen"
                        ) {
                            startScreen(navController)
                        }
                        composable("login_Screen"
                        ) {
                            loginScreen(navController)
                        }
                        composable("requestAccount_Screen"
                        ) {
                            requestAccountScreen(navController)
                        }
                        composable("studentHome_Screen"
                        ) {
                            studentHomeScreen(navController)
                        }
                        composable("recoverAccount_Screen"
                        ) {
                            recoverAccountScreen(navController)
                        }

                        composable("enrollment_Screen"
                        ) {
                            enrollmentScreen(navController)
                        }
                        composable("specifyStudentAttendanceReport_Screen"
                        ) {
                            SpecifyStudentAttendanceReportScreen(navController)
                        }
                        composable("studentAttendanceReport_Screen/{regNo}",
                            arguments = listOf(
                                navArgument("regNo"){type = NavType.StringType}
                            )
                        ) {
                            val regNo = it.arguments?.getString("regNo").toString()
                            StudentAttendanceReportScreen(navController, regNo,StudentReportViewModel)
                        }
                        composable("staffHome_Screen"
                        ) {
                            staffHomeScreen(navController)
                        }
                        composable("adminHome_Screen"
                        ) {
                            adminHomeScreen(navController = navController)
                        }
                        composable("addClass_Screen"
                        ) {
                            addClassScreen(navController = navController)
                        }
                        composable("addStudent_Screen"
                        ) {
                            addStudentScreen(navController = navController)
                        }
                        composable("addLecturer_Screen"
                        ) {
                            addLecturerScreen(navController = navController)
                        }
                        composable("createAccount_Screen/{username}/{password}/{accountType}/{userID}",
                            arguments = listOf(
                                navArgument("username"){type = NavType.StringType},
                                navArgument("password"){type = NavType.StringType},
                                navArgument("accountType"){type = NavType.StringType},
                                navArgument("userID"){type = NavType.StringType}
                            )
                        ) {
                            val username = it.arguments?.getString("username").toString()
                            val password = it.arguments?.getString("password").toString()
                            val accountType = it.arguments?.getString("accountType").toString()
                            val userId = it.arguments?.getString("userID").toString()
                            createAccountScreen(navController = navController,username,password,accountType,userId)
                        }
                        composable("specifyAdminAttendanceReport_Screen"
                        ) {
                            SpecifyAdminAttendanceReportScreen(navController = navController)
                        }
                        composable("adminAttendanceReport_Screen/{classCode}",
                            arguments = listOf(
                                navArgument("classCode"){type = NavType.StringType}
                            )
                        ) {
                            val classCode = it.arguments?.getString("classCode").toString()
                            adminAttendanceReportScreen(navController = navController,classCode,AdminReportViewModel)
                        }
                        composable("viewAccountRequest_Screen"
                        ) {
                            viewAccountRequestScreen(navController = navController,viewmodel)
                        }
                        composable("authorizeAttendance_Screen/{classCode}/{classDuration}/{deviceID}",
                            arguments = listOf(
                                navArgument("classCode"){type = NavType.StringType},
                                navArgument("classDuration"){type = NavType.StringType},
                                navArgument("deviceID"){type = NavType.StringType}
                            )
                        ) {
                            val classCode = it.arguments?.getString("classCode").toString()
                            val classDuration = it.arguments?.getString("classDuration").toString()
                            val deviceID = it.arguments?.getString("deviceID").toString()
                            authorizeAttendanceScreen(navController = navController,classCode,classDuration,deviceID)
                        }
                        composable("stopAttendance_Screen"
                        ) {
                            StopAttendanceScreen(navController = navController)
                        }
                        composable("feedback_Screen/{topLabel}/{feedbackMsg}",
                            arguments = listOf(
                                navArgument("topLabel"){type = NavType.StringType},
                                navArgument("feedbackMsg"){type = NavType.StringType}
                            )
                        ) {
                            val label = it.arguments?.getString("topLabel").toString()
                            val feedbackMsg = it.arguments?.getString("feedbackMsg").toString()
                            FeedbackScreen(label, feedbackMsg, navController = navController)
                        }
                        composable("avialableClasses_Screen"
                        ) {
                            AvialableClasses(navController = navController)
                        }
                        composable("AttendanceAuthInput_Screen"
                        ) {
                            input(navController = navController)
                        }
                        composable("signAttendance_Screen/{stu_DeviceID}/{lec_DeviceID}",
                            arguments = listOf(
                                navArgument("stu_DeviceID"){type = NavType.StringType},
                                navArgument("lec_DeviceID"){type = NavType.StringType}
                            )
                        ) {
                            val stu_DeviceID = it.arguments?.getString("stu_DeviceID").toString()
                            val lec_DeviceID = it.arguments?.getString("lec_DeviceID").toString()
                            signAttendanceScreen(
                                navController = navController, stu_DeviceID = stu_DeviceID, lec_DeviceID = lec_DeviceID
                            )
                        }
                    }
                }
            }
        }
    }
}