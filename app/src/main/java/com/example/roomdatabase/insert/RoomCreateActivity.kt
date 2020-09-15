package com.example.roomdatabase.insert

import android.util.Log
import android.widget.Toast
import com.example.appchat.ui.base.BaseActivity
import com.example.roomdatabase.R
import com.example.roomdatabase.data.UserModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_room_create.*

class RoomCreateActivity : BaseActivity() {
    var myObserver: DisposableObserver<UserModel>? = null
    var myObservable: Observable<UserModel>? = null

    override fun contentView(): Int {
        return R.layout.activity_room_create
    }

    override fun eventHandle() {
        btnSubmit.setOnClickListener {
            var user = UserModel(
                null,
                edtFirstName.text.toString(),
                edtLastName.text.toString(),
                edtAge.text.toString().toInt()
            )
            insertData(user)
            Log.e("TAG", "Click")
        }
    }

    override fun init() {
    }

    private fun insertData(userModel: UserModel) {
        myObservable = Observable.just(userModel)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
        myObserver = object : DisposableObserver<UserModel>() {
            override fun onNext(t: UserModel) {
                database?.userDao()?.insert(t)
                Log.e("TAG", t.firstName)
            }

            override fun onError(e: Throwable) {
                Log.e("TAG", "ERROR: ${e.message}")
            }

            override fun onComplete() {
                Toast.makeText(this@RoomCreateActivity, "Insert Success", Toast.LENGTH_SHORT).show()
            }
        }
        (myObservable)!!.subscribe(myObserver as DisposableObserver<UserModel>)

    }
}