package com.pcs.awslogsdemo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pcs.awslogsdemo.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    private lateinit var bindingSecond: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindingSecond = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(bindingSecond.root)
//
//        Thread {
//            AWSCloudWatchLogger().createLogGroup()
//        }.start()



        bindingSecond.buttonSecond.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
//            Thread{
//                AWSCloudWatchLogger().createLogGroup("Second Activity to MAin Activity")
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