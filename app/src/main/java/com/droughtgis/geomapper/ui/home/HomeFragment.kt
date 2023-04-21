package com.droughtgis.geomapper.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.support.v4.app.Fragment
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import android.support.v7.widget.CardView
import android.util.Log
import android.widget.Button


import com.droughtgis.geomapper.R
import kotlinx.android.synthetic.main.fragment_project.*
import kotlinx.android.synthetic.main.project_item.*


class HomeFragment : Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater!!.inflate(R.layout.fragment_home,container,false)
        Log.e("tag","Home")



        return root
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        Log.e("tag","project attach")
    }

    override fun onDestroyView() {
        super.onDestroyView()

    }


}