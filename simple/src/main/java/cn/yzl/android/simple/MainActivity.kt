package cn.yzl.android.simple

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.orhanobut.logger.Logger
import com.orhanobut.logger.kt.KLog
import com.orhanobut.logger.kt.LogLevel
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        Demo.logger()
        KLog.init("AAA").methodCount(3).logLevel(LogLevel.FULL)

        Logger.e(null, Exception())
        Logger.e("aaa", Exception())
        Logger.e("aaa")
        but1.setOnClickListener {
            KLog.e("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
        }
        val a = JSONObject()
        a.put("ccc",11)
        a.put("d",13)
        a.put("a",33)
        a.put("e",44)
        a.put("a",55)
        a.put("b",66)
        but2.setOnClickListener {
            KLog.json(a.toString())
        }
    }
}
