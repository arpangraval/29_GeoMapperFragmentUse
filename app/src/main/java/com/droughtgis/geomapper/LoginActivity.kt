package com.droughtgis.geomapper

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import com.droughtgis.geomapper.helper.SQLiteHandler
import com.droughtgis.geomapper.helper.SessionManager
import com.droughtgis.geomapper.utils.TextValidation
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private var session: SessionManager? = null
    private var db: SQLiteHandler? = null
    private var pDialog: ProgressDialog? = null
    private var textValidation: TextValidation? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initViews()
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
            val intent = Intent(this@LoginActivity, MainActivity2::class.java)
            startActivity(intent)
            finish()
        }


        // Login button Click Event
        login_signin_btn!!.setOnClickListener {
            verifyFromSQLite()
        }

        // Link to Register Screen
        login_signup_tv!!.setOnClickListener {
            val i = Intent(
                applicationContext,
                RegisterActivity::class.java
            )
            startActivity(i)
            //finish()
        }



    }
    /**
     * This method is to validate the input text fields and verify login credentials from SQLite
     */
    private fun verifyFromSQLite(){
        if (!textValidation!!.isInputEditTextEmail(login_email_et, login_email_tl, getString(R.string.error_message_email))) {
            return
        }
        if (!textValidation!!.isInputEditTextFilled(login_pwd_et, login_pwd_tl, getString(R.string.error_message_password))) {
            return
        }
        if (db!!.checkUser(login_email_et!!.text.toString().trim { it <= ' ' }, login_pwd_et!!.text.toString().trim { it <= ' ' })) {

            val intent = Intent(this@LoginActivity, MainActivity2::class.java)
            startActivity(intent)
            session?.createLoginSession(login_email_et!!.text.toString().trim { it <= ' ' })
            finish()
        } else {

            // Snack Bar to show success message that record is wrong
            Snackbar.make(login_linear!!, getString(R.string.error_valid_email_password), Snackbar.LENGTH_LONG).show()
        }
    }



    companion object {
        private val TAG: String = RegisterActivity::class.java.getSimpleName()
    }
}