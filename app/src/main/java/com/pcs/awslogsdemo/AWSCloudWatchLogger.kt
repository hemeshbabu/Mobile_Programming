package com.pcs.awslogsdemo

import android.util.Log
import com.amazonaws.auth.AWSCredentials
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.regions.Region
import com.amazonaws.regions.Regions
import com.amazonaws.services.logs.AmazonCloudWatchLogsClient
import com.amazonaws.services.logs.model.*
import org.json.JSONObject
import java.util.*


class AWSCloudWatchLogger {

    companion object {
        var stream1 = ""
        var awsLogsClient12: AmazonCloudWatchLogsClient? = null
    }
    //var stream1 = ""
    var logStreamMsg = ""
    var logStreamMsg1 = ""
    var logStreamMsg2 = ""
    var logStreamMsg3 = ""
    var logStreamMsg4 = ""
    var sequenceToken: String? = null
    val logGroupName = "MP_Arria_Test"

    private val accessKey: String = "AKIA5UW3IC636ANG26NI"
    private val secretKey: String = "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"

    fun init(){
        val credential: AWSCredentials = BasicAWSCredentials(accessKey, secretKey)
        val awsLogsClient = AmazonCloudWatchLogsClient(credential)
        awsLogsClient.setRegion(Region.getRegion(Regions.US_WEST_2))
        awsLogsClient12 = awsLogsClient
    }


    fun createLogGroup(levelInfo: String, messageInfo: String, contextInfo: String,email: String, role: String) {
//        val credential: AWSCredentials = BasicAWSCredentials(accessKey, secretKey)
//        val awsLogsClient = AmazonCloudWatchLogsClient(credential)
//        awsLogsClient.setRegion(Region.getRegion(Regions.US_WEST_2))

        //awsLogsClient12 = awsLogsClient
        logStreamMsg = levelInfo
        logStreamMsg1 = messageInfo
        logStreamMsg2 = contextInfo
        logStreamMsg3 = email
        logStreamMsg4 = role

        if (stream1.isEmpty()) {
            createLogStream()
        } else {
//            val logStreamsRequest = DescribeLogStreamsRequest()
//            logStreamsRequest.logGroupName = logGroupName
//
//            val logStreamList = awsLogsClient12?.describeLogStreams(logStreamsRequest)?.logStreams
//
//            if (logStreamList != null) {
//                for (logStream in logStreamList) {
//                    if(stream1 == logStream.logStreamName){
//                        Log.e("AWS", "token 53 $sequenceToken   LogStream $logStream")
//                        sequenceToken = logStream.uploadSequenceToken
//                    }
//
//                }
//            }
            putLogEvent(stream1, awsLogsClient12)
//            if (sequenceToken.isNullOrEmpty()) {
//                createLogStream()
//                Log.e("AWS", "60")
//            } else {
//                Log.e("AWS", "62")
//                Log.e("AWS", "62  stream $stream1")
//                putLogEvent(stream1, awsLogsClient12)
//            }
        }


    }

    private fun createLogStream() {
        val createLogStreamRequest = CreateLogStreamRequest()
        createLogStreamRequest.logStreamName = "${Calendar.getInstance().timeInMillis} Android Demo"
        stream1 = createLogStreamRequest.logStreamName
        createLogStreamRequest.logGroupName = logGroupName

        try {
            Thread {
                awsLogsClient12?.createLogStream(createLogStreamRequest)
            }.start()
            putLogEvent(stream1, awsLogsClient12)
            Log.e("AWS", "created Log Stream")

        } catch (e: Exception) {
            Log.e("AWS", "Creating Logs Stream Exp $e")
        }
    }


    private fun putLogEvent(
        requestedStream: String,
        awsLogsClient2: AmazonCloudWatchLogsClient?
    ) {
        try {

            val logEvents1 = ArrayList<InputLogEvent>()
            val logInputEvent1 = InputLogEvent()
            logInputEvent1.timestamp = Calendar.getInstance().timeInMillis
            Log.e("AWS", "MSG ${awsJsonStringConversion(logStreamMsg,logStreamMsg1,logStreamMsg2,logStreamMsg3,logStreamMsg4)}")
            logInputEvent1.message = awsJsonStringConversion(logStreamMsg,logStreamMsg1,logStreamMsg2,logStreamMsg3,logStreamMsg4).toString()
            logEvents1.add(logInputEvent1)
            var token: String? = null
            val logStreamsRequest1 = DescribeLogStreamsRequest().withLogGroupName(logGroupName)
                .withLogStreamNamePrefix(requestedStream)


            val result = awsLogsClient12?.describeLogStreams(logStreamsRequest1)
            for (stream in result?.logStreams!!) {
                if (requestedStream == stream.logStreamName) {
                    token = stream.uploadSequenceToken


                }
            }
            Log.e("AWS", "Token sequenceToken")
            val putLogEventsRequest = PutLogEventsRequest()

            if (token != null) {
                putLogEventsRequest.logGroupName = logGroupName
                putLogEventsRequest.logStreamName = requestedStream
                putLogEventsRequest.setLogEvents(logEvents1)
                putLogEventsRequest.sequenceToken = token

                awsLogsClient2?.putLogEvents(putLogEventsRequest)
                Log.e("AWS", "127 Token in PUT fun $token")
            } else {

                putLogEventsRequest.logGroupName = logGroupName
                putLogEventsRequest.logStreamName = requestedStream
                putLogEventsRequest.setLogEvents(logEvents1)
                awsLogsClient2?.putLogEvents(putLogEventsRequest)
                Log.e("AWS", "134 Token in PUT fun $token")

            }
        } catch (e: Exception) {
            Log.e("AWS", "Execption PUt $e")
        }
    }


   private fun awsJsonStringConversion(levelInfo: String, messageInfo: String, contextInfo: String,email: String, role: String): JSONObject {
       val stringJson =
            "{\"level\": [{\"locations\": [{\"lat\": \"14.2294625\",\"lng\": \"121.1509005\",\"time\": 1560262643000,\"speed\": 0,\"speedLimit\": 0},{\"lat\": \"14.2294576\",\"lng\": \"121.1509498\",\"time\": 1560262713000,\"speed\": 0,\"speedLimit\": 0},{\"lat\": \"14.2294576\",\"lng\": \"121.1509498\",\"time\": 1560262714000,\"speed\": 0,\"speedLimit\": 0}],\"name\": \"1.5645220491E12\"}  ]}"
        val stringJson1 = "{\"level\": " +
                "[{\"locations\": " +
                "[{\"lat\": \"14.2294625\"," +
                "\"lng\": \"121.1509005\"," +
                "\"time\": 1560262643000," +
                "\"speed\": 0," +
                "\"speedLimit\": 0}," +
                "{\"lat\": \"14.2294576\"," +
                "\"lng\": \"121.1509498\"," +
                "\"time\": 1560262713000," +
                "\"speed\": 0," +
                "\"speedLimit\": 0}," +
                "{\"lat\": \"14.2294576\"," +
                "\"lng\": \"121.1509498\"," +
                "\"time\": 1560262714000," +
                "\"speed\": 0," +
                "\"speedLimit\": 0}]," +
                "\"name\": \"1.5645220491E12\"}" +
                "  ]" +
                "}"

        val stringJson2 = "{\"level\":\"$levelInfo\", \"message\": \"$messageInfo\", \"context\": \"$contextInfo\",\"payloads\": {\"Email\": \"$email\",\"Role\": \"$role\"}}"

        val obj = JSONObject(stringJson2)
        return obj

    }


}


/*
* 1. https://www.tabnine.com/code/java/classes/com.amazonaws.services.logs.model.CreateLogStreamRequest
* 2. https://stackoverflow.com/questions/36876563/amazon-cloud-watch-log-putlogeventsrequest-the-given-sequencetoken-is-invali
* 3. https://www.google.com/search?q=CreateLogStreamRequest+aws+java&oq=CreateLogStreamRequest+&aqs=chrome.0.69i59j69i57j69i60l2.2577j0j7&sourceid=chrome&ie=UTF-8
* 4. https://docs.aws.amazon.com/AmazonCloudWatchLogs/latest/APIReference/cwl-api.pdf#API_CreateLogStream
* 5. https://sdk.amazonaws.com/java/api/latest/software/amazon/awssdk/services/cloudwatchlogs/model/CreateLogStreamRequest.Builder.html
*
*
*
*  */