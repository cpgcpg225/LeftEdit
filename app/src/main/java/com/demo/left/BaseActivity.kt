package com.demo.left

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentActivity
import java.text.SimpleDateFormat
import java.util.*

abstract class BaseActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        init()
    }

    /**
     * 设置layoutId
     */
    abstract fun getLayoutId(): Int

    /**
     * 初始化构造函数
     */
    abstract fun init()

    @SuppressLint("SimpleDateFormat")
    protected fun getCurrentTime(): String {
        val date = Date(System.currentTimeMillis())
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return format.format(date)
    }

    protected fun logD(msg: String) {
        Log.d("xindao_mang", msg)
    }
}