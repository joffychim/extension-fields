package com.moqan.extension_fileds

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.moqan.common.delegate.field

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val txtDesc = findViewById<TextView>(R.id.txt_desc)
        txtDesc?.text = "value is:${test}"
    }
}


val MainActivity.test : Int by field { 100 }