package com.pcs.awslogsdemo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import com.pcs.awslogsdemo.databinding.ActivityMainBinding
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Thread {
            AWSCloudWatchLogger().init()
        }.start()

        Log.e("zzz","data ${parseJson()}")



        binding.buttonMain.setOnClickListener {
            startActivity(Intent(this,FirstActivity::class.java))
            //Log.e("json data","data ${parseJson()}")

            Thread{
                AWSCloudWatchLogger().createLogGroup("Info","Main Activity","Next screen button clciked","test@gmail.com","CEO")
            }.start()
//            Thread {
//                AWSCloudWatchLogger().putLogEvent(
//                    AWSCloudWatchLogger.stream,
//                    AWSCloudWatchLogger.awsLogsClient12
//                )
//            }.start()
        }

    }

    fun parseJson(): JSONObject {
        val str = "\"level\":\"Info\", \"message\": \"CEO Yesterday KPI Screen\", \"context\":\"KPIS\""
        val strParam = "\"areaCode\": \"\", \"stateCode\": \"4566\", \"supervisorNumbers\": \"$7899\", \"storeNumbers\": \"$0122\""
        val userInfo = "\"email\":\"t@gamil.com\", \"role\":\"CEO\""
        val stringJson = "{$str,\"payloads\":{$userInfo,$strParam}}"

        val obj = JSONObject(stringJson)
         return obj

    }
}