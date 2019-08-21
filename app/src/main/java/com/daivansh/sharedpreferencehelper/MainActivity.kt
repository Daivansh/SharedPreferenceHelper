package com.daivansh.sharedpreferencehelper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private val APP_SHARED_PREF = "app_shared_pref"
    private val LOGIN_PREF_KEY = "login_pref"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AppSharedPreference.putValue(this@MainActivity, APP_SHARED_PREF,LOGIN_PREF_KEY,true)
        val loginStatus = AppSharedPreference.getValue(this@MainActivity, APP_SHARED_PREF,LOGIN_PREF_KEY,false)
        Toast.makeText(this@MainActivity,"$loginStatus", Toast.LENGTH_LONG).show()
    }
}
