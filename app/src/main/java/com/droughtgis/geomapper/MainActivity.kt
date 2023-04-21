package com.droughtgis.geomapper

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.CardView
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.fragment_project.*

class MainActivity : AppCompatActivity() {
   override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var cardview=findViewById<CardView>(R.id.project_cardview_parent)
        cardview?.setOnClickListener {
            Log.e("tag","img click ")
            if(include_expand_project.visibility== View.GONE){
                include_expand_project.visibility= View.VISIBLE
            }else{
                include_expand_project.visibility= View.GONE
            }
        }
    }
}