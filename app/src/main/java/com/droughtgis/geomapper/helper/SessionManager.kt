package com.droughtgis.geomapper.helper

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import com.droughtgis.geomapper.LoginActivity
import org.json.JSONObject
import java.util.ArrayList
import java.util.HashMap


class SessionManager(var _context: Context?) {
    // Shared Preferences
    var pref: SharedPreferences
    var editor: SharedPreferences.Editor

    // Shared pref mode
    var PRIVATE_MODE = 0
    init {
        pref = _context!!?.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = pref.edit()
    }
    companion object {
        // LogCat tag
        private val TAG = SessionManager::class.java.simpleName

        // Shared preferences file name
        const val PREF_NAME = "GeomapperPref"
        const val KEY_IS_LOGGED_IN = "isLoggedIn"
        const val EMAIL = "Emailkey"
    }
    fun setLogin(isLoggedIn: Boolean) {
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn)

        // commit changes
        editor.commit()
        Log.d(TAG, "User login session modified!")
    }
    fun createLoginSession(emailid: String?) {
        editor.putString(EMAIL, emailid)
        editor.commit()
    }
    fun getEmailSession():String? {
        var email=pref.getString(EMAIL,null)
        return email
    }
    val isLoggedIn: Boolean
        get() = pref.getBoolean(KEY_IS_LOGGED_IN, false)

    fun logoutUser() {
        editor.clear()
        editor.commit()
        val i = Intent(_context, LoginActivity::class.java)
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        _context?.startActivity(i)
    }


}



