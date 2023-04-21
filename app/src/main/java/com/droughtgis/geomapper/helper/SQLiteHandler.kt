/**
 * Author: Ravi Tamada
 * URL: www.androidhive.info
 * twitter: http://twitter.com/ravitamada
 */
package com.droughtgis.geomapper.helper

import android.database.sqlite.SQLiteOpenHelper
import com.droughtgis.geomapper.helper.SQLiteHandler
import android.database.sqlite.SQLiteDatabase
import android.content.ContentValues
import android.content.Context
import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

class SQLiteHandler(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    // Creating Tables
    companion object {
        private val TAG = SQLiteHandler::class.java.simpleName

        // All Static variables
        // Database Version
        private const val DATABASE_VERSION = 1

        // Database Name
        private const val DATABASE_NAME = "droughtdb"

        // Login table name
        private const val TABLE_USER = "users"
        const val TABLE_QUICKMAPPER = "QuickMapper"
        const val TABLE_GEOMAPPER = "GeoMapper"
        const val TABLE_PROJECT = "project"
        const val TABLE_CAT = "category"
        const val TABLE_SUB_CAT = "subcategory"
        // Login Table Columns names
        private const val KEY_ID = "id"
        private const val KEY_NAME = "name"
        private const val KEY_EMAIL = "email"
        private const val KEY_PASSWORD = "password"
        private const val KEY_CREATED_AT = "created_at"
        private const val KEY_TITLE = "PTitle"
        private const val KEY_CTITLE = "CTitle"
        private const val KEY_DETAILS = "PDetail"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_LOGIN_TABLE = ("CREATE TABLE " + TABLE_USER + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT,"
                + KEY_EMAIL + " TEXT UNIQUE," + KEY_PASSWORD + " TEXT,"
                + KEY_CREATED_AT + " TEXT" + ")")
        db.execSQL(CREATE_LOGIN_TABLE)

        //create project table
        db.execSQL("CREATE TABLE " + TABLE_PROJECT + " (PID INTEGER PRIMARY KEY AUTOINCREMENT, PTitle TEXT, PDetail TEXT)")

        db.execSQL("CREATE TABLE " + TABLE_CAT +  "(CID INTEGER PRIMARY KEY AUTOINCREMENT, CTitle TEXT)")

        //create project main cat table
      //  db.execSQL("CREATE TABLE " + TABLE_SUB_CAT + " (CID INTEGER PRIMARY KEY AUTOINCREMENT, CTitle TEXT)")

        //create Quick Mapper table with col
        //db.execSQL("CREATE TABLE " + TABLE_QUICKMAPPER + " (QID INTEGER PRIMARY KEY AUTOINCREMENT, PID INTEGER, UID INTEGER, QTitle TEXT, QDetail TEXT, QLat TEXT, QLong TEXT, QAccu TEXT, QAlt TEXT, QImg TEXT, QTimeStamp DateTime)")
        db.execSQL("CREATE TABLE " + TABLE_GEOMAPPER + " (GID INTEGER PRIMARY KEY AUTOINCREMENT, PID INTEGER, UID INTEGER, GTitle TEXT, GDetail TEXT, GMapMain TEXT, GMapType TEXT, GLat TEXT, GLong TEXT, GAccu TEXT, GAlt TEXT, GImg TEXT, GTimeStamp DateTime)")
        Log.d(TAG, "All tables has created")
    }

    // Upgrading database
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUICKMAPPER)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GEOMAPPER)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CAT)

        // Create tables again
        onCreate(db)
    }

    /**
     * Storing project main category details in database
     */
    fun addProjectMainCat(title: String?) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_CTITLE, title) // title

        //values.put(KEY_CREATED_AT, getDateTime()) // Created At

        // Inserting Row
        val id = db.insert(TABLE_CAT, null, values)
        db.close() // Closing database connection
        Log.d(TAG, "New project category inserted into sqlite: $id")
    }
    /**
     * Storing project details in database
     */
    fun addProject(title: String?, details: String?) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_TITLE, title) // title
        values.put(KEY_DETAILS, details) // des
        //values.put(KEY_CREATED_AT, getDateTime()) // Created At

        // Inserting Row
        val id = db.insert(TABLE_PROJECT, null, values)
        db.close() // Closing database connection
        Log.d(TAG, "New project inserted into sqlite: $id")
    }

    /**
     * Storing user details in database
     */
    fun addUser(name: String?, email: String?, password: String?) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_NAME, name) // Name
        values.put(KEY_EMAIL, email) // Email
        values.put(KEY_PASSWORD, password) // password
        values.put(KEY_CREATED_AT, getDateTime()) // Created At

        // Inserting Row
        val id = db.insert(TABLE_USER, null, values)
        db.close() // Closing database connection
        Log.d(TAG, "New user inserted into sqlite: $id")
    }
    private fun getDateTime(): String? {
        val dateFormat = SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss", Locale.getDefault()
        )
        val date = Date()
        return dateFormat.format(date)
    }
    /**
     * This method to check user exist or not
     *
     * @param email
     * @return true/false
     */
    fun checkUser(email: String): Boolean {

        // array of columns to fetch
        val columns = arrayOf(KEY_ID)
        val db = this.readableDatabase

        // selection criteria
        val selection = "$KEY_EMAIL = ?"

        // selection argument
        val selectionArgs = arrayOf(email)

        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        val cursor = db.query(TABLE_USER, //Table to query
            columns,        //columns to return
            selection,      //columns for the WHERE clause
            selectionArgs,  //The values for the WHERE clause
            null,  //group the rows
            null,   //filter by row groups
            null)  //The sort order


        val cursorCount = cursor.count
        cursor.close()
        db.close()

        if (cursorCount > 0) {
            return true
        }

        return false
    }
    /**
     * This method to check user exist or not
     *
     * @param email
     * @param password
     * @return true/false
     */
    fun checkUser(email: String, password: String): Boolean {

        // array of columns to fetch
        val columns = arrayOf(KEY_ID)

        val db = this.readableDatabase

        // selection criteria
        val selection = "$KEY_EMAIL = ? AND $KEY_PASSWORD = ?"

        // selection arguments
        val selectionArgs = arrayOf(email, password)

        // query user table with conditions
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com' AND user_password = 'qwerty';
         */
        val cursor = db.query(TABLE_USER, //Table to query
            columns, //columns to return
            selection, //columns for the WHERE clause
            selectionArgs, //The values for the WHERE clause
            null,  //group the rows
            null, //filter by row groups
            null) //The sort order

        val cursorCount = cursor.count
        cursor.close()
        db.close()

        if (cursorCount > 0)
            return true

        return false

    }
    fun checkcropname(title: String): Boolean {

        // array of columns to fetch
        val columns = arrayOf(KEY_ID)
        val db = this.readableDatabase

        // selection criteria
        val selection = "$KEY_EMAIL = ?"

        // selection argument
        val selectionArgs = arrayOf(title)

        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        val cursor = db.query(
            TABLE_CAT, //Table to query
            columns,        //columns to return
            selection,      //columns for the WHERE clause
            selectionArgs,  //The values for the WHERE clause
            null,  //group the rows
            null,   //filter by row groups
            null)  //The sort order


        val cursorCount = cursor.count
        cursor.close()
        db.close()

        if (cursorCount > 0) {
            return true
        }

        return false
    }
    // Insert Data for Quick Mapper
    fun insertQuickMapperData(
        pid: String?,
        uid: String?,
        qtitle: String?,
        qdetail: String?,
        qlat: String?,
        qlong: String?,
        qaccu: String?,
        qalt: String?,
        qimg: String?
    ): Boolean {
        val db = this.writableDatabase
        Log.d(TAG, "Now inserting data into Quick Mapper")
        val contentValues = ContentValues()
        contentValues.put("PID", pid)
        contentValues.put("UID", uid)
        contentValues.put("QTitle", qtitle)
        contentValues.put("QDetail", qdetail)
        contentValues.put("QLat", qlat)
        contentValues.put("QLong", qlong)
        contentValues.put("QAccu", qaccu)
        contentValues.put("QAlt", qalt)
        contentValues.put("QImg", qimg)
        // insert Quick mapper data
        val result = db.insert(TABLE_QUICKMAPPER, null, contentValues)
        return if (result == -1L) false else {
            Log.d(TAG, "Data inserted:$result")
            quickMapperData
            true
        }
    }

    // Insert Geomapper Data
    fun insertGeoMapperData(
        pid: String?,
        uid: String?,
        gtitle: String?,
        gdetail: String?,
        gmainmap: String?,
        gmaptype: String?,
        glat: String?,
        glong: String?,
        gaccu: String?,
        galt: String?,
        gimg: String?
    ): Boolean {
        val db = this.writableDatabase
        Log.d(TAG, "Now inserting data into Geo Mapper")
        val contentValues = ContentValues()
        contentValues.put("PID", pid)
        contentValues.put("UID", uid)
        contentValues.put("GTitle", gtitle)
        contentValues.put("GDetail", gdetail)
        contentValues.put("GMapMain", gmainmap)
        contentValues.put("GMapType", gmaptype)
        contentValues.put("GLat", glat)
        contentValues.put("GLong", glong)
        contentValues.put("GAccu", gaccu)
        contentValues.put("GAlt", galt)
        contentValues.put("GImg", gimg)
        // insert Geo mapper data
        val result = db.insert(TABLE_GEOMAPPER, null, contentValues)
        return if (result == -1L) false else {
            Log.d(TAG, "Data inserted:$result")
            geoMapperData
            true
        }
    }// Move to first row
    // return user
    /**
     * Getting user data from database
     */
    val userDetails: HashMap<String, String>
        get() {
            val user = HashMap<String, String>()
            val selectQuery = "SELECT  * FROM " + TABLE_USER
            val db = this.readableDatabase
            val cursor = db.rawQuery(selectQuery, null)
            // Move to first row
            cursor.moveToFirst()
            if (cursor.count > 0) {
                user["name"] = cursor.getString(1)
                user["email"] = cursor.getString(2)
                user["uid"] = cursor.getString(3)
                user["created_at"] = cursor.getString(4)
            }
            cursor.close()
            db.close()
            // return user
            Log.d(TAG, "Fetching user from user db: $user")
            return user
        }//cursor.moveToNext();

    //}
    // return user
// Move to first row
    // PRINT quick mapper data
    val quickMapperData: HashMap<String, String>
        get() {
            val quickmapper = HashMap<String, String>()
            val selectQuery = "SELECT  * FROM " + TABLE_QUICKMAPPER
            val db = this.readableDatabase
            val cursor = db.rawQuery(selectQuery, null)
            // Move to first row
            cursor.moveToLast()
            if (cursor.count > 0) {
                quickmapper["PID"] = cursor.getString(1)
                quickmapper["UID"] = cursor.getString(2)
                quickmapper["QTitle"] = cursor.getString(3)
                quickmapper["QDetail"] = cursor.getString(4)
                quickmapper["QLat"] = cursor.getString(5)
                quickmapper["QLong"] = cursor.getString(6)
                quickmapper["QAccu"] = cursor.getString(7)
                quickmapper["QAlt"] = cursor.getString(8)
                quickmapper["Qimg"] = cursor.getString(9)
                Log.d(TAG, "Fetching user from id: " + cursor.getString(0))
                Log.d(TAG, "Fetching user from pid: " + cursor.getString(1))
                Log.d(TAG, "Fetching user from uid: " + cursor.getString(2))
                Log.d(TAG, "Fetching user from Sqlite: " + cursor.getString(3))
                Log.d(TAG, "Fetching user from Sqlite: " + cursor.getString(4))
                Log.d(TAG, "Fetching user from Sqlite: " + cursor.getString(5))
                Log.d(TAG, "Fetching user from Sqlite: " + cursor.getString(6))
                Log.d(TAG, "Fetching user from Sqlite: " + cursor.getString(7))
                Log.d(TAG, "Fetching user from Sqlite: " + cursor.getString(8))
                Log.d(TAG, "Fetching user from image: " + cursor.getString(9))
                //cursor.moveToNext();
            }
            //}
            cursor.close()
            db.close()
            // return user
            Log.d(TAG, "Fetching user from Sqlite: $quickmapper")
            return quickmapper
        }//cursor.moveToNext();

    //}
    // return user
// Move to first row
    // PRINT geo mapper data
    val geoMapperData: HashMap<String, String>
        get() {
            val geomapper = HashMap<String, String>()
            val selectQuery = "SELECT  * FROM " + TABLE_GEOMAPPER
            val db = this.readableDatabase
            val cursor = db.rawQuery(selectQuery, null)
            // Move to first row
            cursor.moveToLast()
            if (cursor.count > 0) {
                geomapper["PID"] = cursor.getString(1)
                geomapper["UID"] = cursor.getString(2)
                geomapper["GTitle"] = cursor.getString(3)
                geomapper["GDetail"] = cursor.getString(4)
                geomapper["GMapMain"] = cursor.getString(5)
                geomapper["GMapType"] = cursor.getString(6)
                geomapper["GLat"] = cursor.getString(7)
                geomapper["GLong"] = cursor.getString(8)
                geomapper["GAccu"] = cursor.getString(9)
                geomapper["GAlt"] = cursor.getString(10)
                geomapper["GImg"] = cursor.getString(11)
                Log.d(TAG, "Fetching user from id: " + cursor.getString(0))
                Log.d(TAG, "Fetching user from pid: " + cursor.getString(1))
                Log.d(TAG, "Fetching user from uid: " + cursor.getString(2))
                Log.d(TAG, "Fetching user from Sqlite: " + cursor.getString(3))
                Log.d(TAG, "Fetching user from Sqlite: " + cursor.getString(4))
                Log.d(TAG, "Fetching user from Sqlite: " + cursor.getString(5))
                Log.d(TAG, "Fetching user from Sqlite: " + cursor.getString(6))
                Log.d(TAG, "Fetching user from Sqlite: " + cursor.getString(7))
                Log.d(TAG, "Fetching user from Sqlite: " + cursor.getString(8))
                Log.d(TAG, "Fetching user from Sqlite: " + cursor.getString(9))
                Log.d(TAG, "Fetching user from Sqlite: " + cursor.getString(10))
                Log.d(TAG, "Fetching user from image: " + cursor.getString(11))
                //cursor.moveToNext();
            }
            //}
            cursor.close()
            db.close()
            // return user
            Log.d(TAG, "Fetching Geomapper from Sqlite: $geomapper")
            return geomapper
        }

    // PRINT Crop Observation data
    // return user user detail
    val uID: String?
        get() {
            val user = userDetails
            return user["uid"]
        }

    /**
     * Re crate database Delete all tables and create them again
     */
    fun deleteUsers() {
        val db = this.writableDatabase
        // Delete All Rows
        db.delete(TABLE_USER, null, null)
        db.delete(TABLE_GEOMAPPER, null, null)
        db.delete(TABLE_QUICKMAPPER, null, null)
        db.close()
        Log.d(TAG, "Deleted all user info from sqlite")
    }


}