package com.pcs.awslogsdemo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pcs.awslogsdemo.databinding.ActivityFirstBinding

class FirstActivity : AppCompatActivity() {

    private lateinit var bindingFirst: ActivityFirstBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindingFirst = ActivityFirstBinding.inflate(layoutInflater)
        setContentView(bindingFirst.root)

//        Thread {
//            AWSCloudWatchLogger().createLogGroup()
//        }.start()



        bindingFirst.buttonFirst.setOnClickListener {
            startActivity(Intent(this,SecondActivity::class.java))
//            Thread{
//                AWSCloudWatchLogger().createLogGroup("First Activity to Second Activity")
//            }.start()

//            Thread {
//                AWSCloudWatchLogger().putLogEvent(
//                    AWSCloudWatchLogger.stream,
//                    AWSCloudWatchLogger.awsLogsClient12
//                )
//            }.start()
        }

    }
}