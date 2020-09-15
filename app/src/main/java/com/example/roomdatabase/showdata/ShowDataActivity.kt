package com.example.roomdatabase.showdata

import android.app.AlertDialog
import android.content.DialogInterface
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appchat.ui.base.BaseActivity
import com.example.roomdatabase.R
import com.example.roomdatabase.data.UserModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_show_data.*
import java.util.*
import kotlin.collections.ArrayList

class ShowDataActivity : BaseActivity() {
    private var users = ArrayList<UserModel>()
    private val adapter by lazy {
        ShowDataAdapter(
            this,
            users, { it -> onClickUpdate(it) }
        ) { it -> onClickDelete(it) }

    }

    override fun contentView(): Int {
        return R.layout.activity_show_data
    }

    override fun eventHandle() {
        getAllData()
    }

    override fun init() {
        rclUser.adapter = adapter
        rclUser.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rclUser.setHasFixedSize(true)
        mViewModel.users.observe(self, androidx.lifecycle.Observer { getNewData() })
    }

    private fun getNewData() {
//for (i in 0 until mViewModel.users)
    }

    private fun onClickUpdate(model: UserModel) {
        updateData(model)
    }

    private fun onClickDelete(model: UserModel) {
        var alert: AlertDialog.Builder = AlertDialog.Builder(this)
        alert.setTitle("DELETE ?")
        alert.setMessage("DO YOU WANT DELETE IT ?")
        alert.setPositiveButton(
            "YES"
        ) { _, _ ->
            database.userDao().delete(model)
            getAllData()
        }
        alert.setNegativeButton(
            "NO"
        ) { _, _ -> }
        alert.show()
    }

    private fun getAllData() {
        users.clear()
        var list = database?.userDao()?.readAllData() as ArrayList<UserModel>
        users.addAll(list)
        adapter.notifyDataSetChanged()
    }

    private fun updateData(userModel: UserModel) {
        var myObservable = Observable.just(userModel)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
        var myObserver = object : DisposableObserver<UserModel>() {
            override fun onNext(t: UserModel) {
                userModel.id?.let { database?.userDao()?.update("LỘC", it) }
                Log.e("TAG", t.firstName)
            }

            override fun onError(e: Throwable) {
                Log.e("TAG", "ERROR: ${e.message}")
            }

            override fun onComplete() {
                Toast.makeText(this@ShowDataActivity, "Update success", Toast.LENGTH_SHORT).show()
            }
        }
        (myObservable)!!.subscribe(myObserver as DisposableObserver<UserModel>)
    }

}