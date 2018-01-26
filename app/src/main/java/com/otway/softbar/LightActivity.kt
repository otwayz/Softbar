package com.otway.softbar

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.otway.library.SoftBar

class LightActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_light)

        SoftBar.with(this).statusBarBackground(Color.WHITE).statusBarDarkFont()
    }
}
