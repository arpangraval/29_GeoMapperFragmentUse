package com.droughtgis.geomapper.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.support.v4.app.Fragment
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.util.Log
import android.widget.Button
import com.droughtgis.geomapper.R
import kotlinx.android.synthetic.main.fragment_notifications.*
import kotlinx.android.synthetic.main.fragment_project.*
import kotlinx.android.synthetic.main.gt_collection_project_layout.*


class NotificationsFragment : Fragment(),View.OnClickListener {

     override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater!!.inflate(R.layout.fragment_notifications,container,false)
        Log.e("tag","Home")

        var btn=root.findViewById<Button>(R.id.gt_collect_project_Next)
        btn.setOnClickListener(this)

        var backbtn=root.findViewById<Button>(R.id.gt_collect_sample_back)
        backbtn.setOnClickListener(this)
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()

    }

    override fun onClick(p0: View?) {
       when(p0?.id){
           R.id.gt_collect_project_Next->{
               Log.e("tag","Next project")
               if(include_gt_project.visibility==View.VISIBLE){
                   include_gt_project.visibility=View.GONE
                   include_gt_sample.visibility=View.VISIBLE
               }else{
                   //include_expand_project.visibility=View.GONE
               }
           }

           R.id.gt_collect_sample_back->{
               if(include_gt_sample.visibility==View.VISIBLE){
                   include_gt_project.visibility=View.VISIBLE
                   include_gt_sample.visibility=View.GONE
               }else{
                   //include_expand_project.visibility=View.GONE
               }
           }
       }
    }
}