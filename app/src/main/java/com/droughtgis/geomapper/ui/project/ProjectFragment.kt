package com.droughtgis.geomapper.ui.project

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.support.v4.app.Fragment
import android.content.Context
import android.content.Intent
import android.support.design.widget.Snackbar
import android.support.v7.widget.CardView
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.droughtgis.geomapper.MainActivity2


import com.droughtgis.geomapper.R
import com.droughtgis.geomapper.helper.SQLiteHandler
import com.droughtgis.geomapper.helper.SessionManager
import com.droughtgis.geomapper.utils.TextValidation
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.fragment_project.*
import kotlinx.android.synthetic.main.project_cat_item.*
import kotlinx.android.synthetic.main.project_item.*


class ProjectFragment : Fragment(),View.OnClickListener {
    private var session: SessionManager? = null
    private var db: SQLiteHandler? = null
    private var pDialog: ProgressDialog? = null
    private var textValidation: TextValidation? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater!!.inflate(R.layout.fragment_project,container,false)
        Log.e("tag","project")

        //add project title and desc view binding
        var projectcardview=root.findViewById<CardView>(R.id.project_cardview_parent)
        projectcardview.setOnClickListener(this)

        var projectsubmit=root.findViewById<Button>(R.id.project_submit)
        projectsubmit.setOnClickListener(this)

        //add project cat title  view binding
        var projectcatcardview=root.findViewById<CardView>(R.id.project_cat_cardview_parent)
        projectcatcardview.setOnClickListener(this)


        var catsubmit=root.findViewById<Button>(R.id.project_cat_submit)
        catsubmit.setOnClickListener(this)

        initViews()

        return root
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        Log.e("tag","project attach")

    }

    override fun onDestroyView() {
        super.onDestroyView()

    }

    fun initViews(){
        session = SessionManager(context)
        db = SQLiteHandler(context)
        textValidation = TextValidation(context!!)
        // Progress dialog
        pDialog = ProgressDialog(context)
        pDialog!!.setCancelable(false)
    }
    override fun onClick(v: View?) {
        when (v?.id) {
            //project title and desc card view handling
            R.id.project_cardview_parent -> {

                if(include_expand_project.visibility==View.GONE){
                    include_expand_project.visibility=View.VISIBLE
                }else{
                    include_expand_project.visibility=View.GONE
                }
            }
            R.id.project_submit->{
                Log.e("tag","button click ")
                postDataToSQLite()

            }
            //project main category  card view handling
            R.id.project_cat_cardview_parent->{
                if(include_expand_project_cat.visibility==View.GONE){
                    include_expand_project_cat.visibility=View.VISIBLE
                }else{
                    include_expand_project_cat.visibility=View.GONE
                }
            }
            R.id.project_cat_submit->{
                Log.e("tag","button click cat  ")
                postDataToDB()
            }
            else -> {
            }
        }
    }

    private fun postDataToSQLite() {

        Log.e("tag","post click ")
        if (!textValidation!!.isInputEditTextFilled(project_title_et, project_title_tl, getString(R.string.error_project_title))) {
            return
        }
        if (!textValidation!!.isInputEditTextFilled(project_des_et, project_des_tl, getString(R.string.error_project_dec))) {
            return
        }
            db!!.addProject(
                project_title_et!!.text.toString().trim(),
                project_des_et!!.text.toString().trim()
            )

            // Snack Bar to show success message that record saved successfully
          Toast.makeText(context,"recode updated",Toast.LENGTH_LONG).show()
    }

    private fun postDataToDB() {
        initViews()
        Log.e("tag","post click ")
        if (!textValidation!!.isInputEditTextFilled(cat_project_name_et, cat_project_name_tl, getString(R.string.error_cat_title))) {
            return
        }
       // if (!db!!.checkcropname(cat_project_name_et!!.text.toString().trim())) {
            db!!.addProjectMainCat(
                cat_project_name_et!!.text.toString().trim()
            )
            // Snack Bar to show success message that record saved successfully
            Toast.makeText(context, "recode updated", Toast.LENGTH_LONG).show()
       // }else{
          //  Toast.makeText(context, "category already added ", Toast.LENGTH_LONG).show()

       // }
    }
}