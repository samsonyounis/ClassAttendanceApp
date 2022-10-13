package view.Package

import android.content.Context
import android.content.SharedPreferences

/* Session manager to save and fetch data from SharedPreferences */
class SessionManager (context: Context) {
    private var prefs: SharedPreferences = context.getSharedPreferences(
        "Class_Attendance_App",
        Context.MODE_PRIVATE
    )
    private var prefsAccountType: SharedPreferences = context.getSharedPreferences(
        context.getString(R.string.app_name),
        Context.MODE_PRIVATE
    )

    companion object {
        const val USER_TYPE = "user_type"
    }

    /* Function to save user type*/
    fun saveUserType(userType: String) {
        val editor = prefs.edit()
        editor.putString(USER_TYPE, userType)
        editor.apply()
    }

    /* Function to fetch user type */
    fun fetchUserType(): String? {
        return prefs.getString(USER_TYPE, null)
    }
    /* Function to delete user type*/
    fun deleteUserType(){
        val editor = prefs.edit()
        editor.remove(USER_TYPE)
    }
    /*
    /* Function to save the user selected account */
    fun saveAccountType(accountType:String){
        val editor = prefsAccountType.edit()
        editor.putString(account_Type, accountType)
        editor.apply()
    }

    /* Function to get the selected user account type */
    fun getAccountType():String?{
        return prefsAccountType.getString(account_Type, null)
    }
}

     */
}