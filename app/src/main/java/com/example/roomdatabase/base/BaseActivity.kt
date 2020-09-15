package com.example.appchat.ui.base

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import com.example.fcm.common.ext.gone
import com.example.fcm.common.ext.visible
import com.example.roomdatabase.R
import com.example.roomdatabase.data.UserDatabase
import com.example.roomdatabase.data.UserViewModel
import kotlinx.android.synthetic.main.activity_base.*

abstract class BaseActivity : AppCompatActivity() {
    open val self by lazy { this }
    val database: UserDatabase by lazy { UserDatabase.getInstance(this) }
     val mViewModel: UserViewModel by lazy {
        ViewModelProviders.of(this).get(UserViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        contentView()?.run {
            setContentView(R.layout.activity_base)
            layoutInflater.inflate(this, frlBase)
        }
        getExtra()
        init()
        eventHandle()
    }

    abstract fun contentView(): Int
    abstract fun init()
    abstract fun eventHandle()
    open fun getExtra() {}

    // Hide base toolbar
    protected fun hideToolbarBase() {
        toolbarBase.gone()
    }

    // Show base toolbar
    protected fun showToolbarBase() {
        toolbarBase.visible()
    }

    // Using toolbar
    protected fun showTitle(title: Any? = null) {
        // Set title
        when (title) {
            is CharSequence -> toolbarBase.title = title
            is String -> toolbarBase.title = title
            is Int -> toolbarBase.title = getString(title)
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    protected fun applyToolbar(
        toolbar: Toolbar = toolbarBase,
        background: Int? = null,
        removeElevation: Boolean = false
    ) {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        background?.run {
            toolbar.setBackgroundColor(ContextCompat.getColor(self, background))
        }

        if (removeElevation) {
            toolbar.elevation = 0f
        }
    }


    // Show Back icon
    protected fun enableHomeAsUp(
        toolbar: Toolbar = toolbarBase,
        backArrowColorResId: Int? = null,
        up: () -> Unit
    ) {
        toolbar.run {
            navigationIcon = backArrowColorResId?.run {
                DrawerArrowDrawable(self).apply {
                    progress = 1f
                    color = ContextCompat.getColor(self, backArrowColorResId)
                }
            } ?: DrawerArrowDrawable(self).apply { progress = 1f }
            setNavigationOnClickListener { up() }
        }
    }

}