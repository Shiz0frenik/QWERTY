package com.example.qwerty

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.qwerty.data.Point
import com.example.qwerty.data.PointViewModel
import kotlinx.android.synthetic.main.fragment_list.view.*

class SavedActivity : AppCompatActivity() {
    lateinit var actionBar: ActionBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved)
        if (supportActionBar != null) {
            actionBar = supportActionBar as ActionBar
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setTitle(getString(R.string.title_activity_settings))

        }
        supportFragmentManager.beginTransaction().replace(
            android.R.id.content,
            ReadFragment()
        ).commit()


    }

    class ReadFragment : Fragment() {

        private lateinit var mUserViewModel: PointViewModel

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            // Inflate the layout for this fragment
            val view = inflater.inflate(R.layout.fragment_list, container, false)

            // Recyclerview
            val adapter = ListAdapter()
            val recyclerView = view.recyclerview
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(requireContext())

            // UserViewModel
            mUserViewModel = ViewModelProvider(this).get(PointViewModel::class.java)
            mUserViewModel.readAllData.observe(viewLifecycleOwner, Observer { point ->
                adapter.setData(point)
            })



            return view
        }


    }
}





