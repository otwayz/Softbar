package com.otway.softbar

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.otway.library.SoftBar
import kotlinx.android.synthetic.main.activity_immerse.*

class ImmerseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_immerse)

        setSupportActionBar(id_toolbar)
        supportActionBar!!.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeButtonEnabled(true)
        }

        SoftBar.with(this).invasionStatusBar().needOffsetView(id_toolbar).safeDarkFont()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }
}
