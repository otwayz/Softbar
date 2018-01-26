package com.otway.softbar

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.otway.library.SoftBar

class DarkActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dark)

        SoftBar.with(this).statusBarBackground(Color.DKGRAY).statusBarLightFont()
    }
}
