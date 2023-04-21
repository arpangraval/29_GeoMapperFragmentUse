package com.droughtgis.geomapper

import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.droughtgis.geomapper.helper.SessionManager

class SplashActivity : AppCompatActivity() {
    private var session: SessionManager? = null
    private var emailid:String?=null
     var perf:SharedPreferences?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        session = SessionManager(applicationContext)

        emailid= session!!.getEmailSession()
        Log.e("EMAIL",emailid.toString())
        Handler().postDelayed({
            if (emailid != null) {
                val intent = Intent(this, MainActivity2::class.java)
                startActivity(intent)
                finish()
            }else{
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }

        }, 3000)
    }
}
