package com.example.qwerty

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.*
import com.example.qwerty.R.string.title_activity_settings
import java.util.prefs.PreferenceChangeListener

 class SettingsActivity : AppCompatActivity() {
     lateinit var actionBar:ActionBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        if (supportActionBar != null){
            actionBar = supportActionBar as ActionBar
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setTitle(getString(R.string.title_activity_settings))

        }

        supportFragmentManager.beginTransaction().replace(android.R.id.content,SettingsFragment()).commit()
    }



    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)

        }


    }

     override fun onOptionsItemSelected(item: MenuItem): Boolean {
         if(item.itemId==android.R.id.home){
             finish()
         }
         return true
     }



 }

