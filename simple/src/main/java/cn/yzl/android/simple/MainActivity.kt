package cn.yzl.android.simple

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.orhanobut.logger.Logger

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        Demo.logger()
        Logger.e(null, Exception())
        Logger.e("aaa", Exception())
        Logger.e("aaa")
    }
}
