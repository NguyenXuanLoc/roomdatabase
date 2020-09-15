package com.example.roomdatabase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.appchat.ui.base.BaseActivity
import com.example.roomdatabase.insert.RoomCreateActivity
import com.example.roomdatabase.showdata.ShowDataActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun contentView(): Int {
        return R.layout.activity_main
    }

    override fun eventHandle() {
        btnCreate.setOnClickListener {
            startActivity(Intent(this, RoomCreateActivity::class.java))
        }
        btnView.setOnClickListener {
            startActivity(Intent(this, ShowDataActivity::class.java))
        }
    }

    override fun init() {
    }

}