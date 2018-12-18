package com.otway.softbar

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.otway.library.SoftBar

class DarkActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dark)

        SoftBar(this).safeLightFont(Color.DKGRAY)
    }
}
