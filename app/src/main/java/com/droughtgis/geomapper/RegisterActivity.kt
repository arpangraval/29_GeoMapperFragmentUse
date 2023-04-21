package com.droughtgis.geomapper

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar

import com.droughtgis.geomapper.helper.SQLiteHandler
import com.droughtgis.geomapper.helper.SessionManager
import com.droughtgis.geomapper.utils.TextValidation
import kotlinx.android.synthetic.main.activity_register.*


class RegisterActivity : AppCompatActivity() {
    private var session: SessionManager? = null
    private var db: SQLiteHandler? = null
    private var pDialog: ProgressDialog? = null
    private var textValidation: TextValidation? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        // initializing the views
        initViews()
        //load data
        loadData()
    }
    fun initViews(){
        session = SessionManager(applicationContext)
        db = SQLiteHandler(applicationContext)
        textValidation = TextValidation(applicationContext)
        // Progress dialog
        pDialog = ProgressDialog(this)
        pDialog!!.setCancelable(false)
    }
    fun loadData(){
        // Check if user is already logged in or not
        if (session!!.isLoggedIn) {
            // User is already logged in. Take him to main activity
            val intent = Intent(
                this@RegisterActivity,
                MainActivity::class.java
            )
            startActivity(intent)
            finish()
        }

        // Register Button Click event
        signup_btn!!.setOnClickListener {
             postDataToSQLite()

        }
    }


    /**
     * This method is to validate the input text fields and post data to SQLite
     */
    private fun postDataToSQLite() {
        if (!textValidation!!.isInputEditTextFilled(signup_uname_et, signup_uname_tl, getString(R.string.error_message_name))) {
            return
        }
        if (!textValidation!!.isInputEditTextEmail(signup_email_et, signup_email_tl, getString(R.string.error_message_email))) {
            return
        }
        if (!textValidation!!.isInputEditTextFilled(signup_pwd_et, signup_pwd_tl, getString(R.string.error_message_password))) {
            return
        }

        if (!db!!.checkUser(signup_email_et!!.text.toString().trim())) {
            db!!.addUser(
                signup_uname_et!!.text.toString().trim(),
                signup_email_et!!.text.toString().trim(),
                signup_pwd_et!!.text.toString().trim()
            )

            // Snack Bar to show success message that record saved successfully
             Snackbar.make(signup_linear!!, getString(R.string.success_message), Snackbar.LENGTH_LONG).show()

            val intent = Intent(
                this@RegisterActivity,
                MainActivity2::class.java
            )
            startActivity(intent)
        }else{
              Snackbar.make(signup_linear!!, getString(R.string.error_email_exists), Snackbar.LENGTH_LONG).show()
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}