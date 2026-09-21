package com.toletspot.houseforrent


import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.util.Random


var notificationRefernace = mutableStateOf(false)
@SuppressLint("MissingFirebaseInstanceTokenRefresh")
class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        // Check if data payload is not empty
        // Log received data payload
        println("checking--- ${remoteMessage.data}")
        if (remoteMessage.data.isNotEmpty()) {
            val message = remoteMessage.data["body"]
            val title = remoteMessage.data["title"]

            // Send the notification using the data payload
            sendNotification(title, message, remoteMessage.data)
        }


    }

    private fun sendNotification(
        title: String?,
        messageBody: String?,
        data: MutableMap<String, String>
    ) {

        // Create an intent that will open your Compose activity
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
//            putExtra("nid", "1")
            putExtra("title", data["title"])
            putExtra("body", data["body"])
            putExtra("id", data["id"])
            putExtra("type", data["type"])
        }

        val requestCode: Int = Random().nextInt()

        val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.getActivity(
                this,
                requestCode,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        } else {
            PendingIntent.getActivity(
                this,
                requestCode,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        }

        val mNotifyManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        // Set Notification Channel for Android 8.0 (Oreo) and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val mChannel = NotificationChannel(
                this.resources.getString(R.string.app_name),
                this.resources.getString(R.string.app_name),
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = messageBody
                enableLights(true)
                lightColor = Color.RED
                enableVibration(true)
                vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
            }
            mNotifyManager.createNotificationChannel(mChannel)
        }

        val notificationBuilder = NotificationCompat.Builder(this, this.resources.getString(R.string.app_name))
            .setContentTitle(title)
            .setContentText(messageBody)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setAutoCancel(true)
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setColor(Color.parseColor("#ECE36D"))
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        mNotifyManager.notify(requestCode, notificationBuilder.build())

        try{

            constants.sharedHelper.putBoolean(applicationContext, constants.notificationiconEnable,true)
            notificationRefernace.value = !notificationRefernace.value

        }catch (ee: Exception){

        }

    }

}

var deviceToken = ""
fun getDeviceToken(onTokenReceived: (String) -> Unit) {
    println("Fetching device token...")

    if (!checkForInternet(constants.activity)) {
        println("No internet connection!")
        onTokenReceived("") // return empty if offline
        return
    }

    FirebaseMessaging.getInstance().token
        .addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                println("Fetching FCM registration token failed: ${task.exception?.message}")
                onTokenReceived("")
                return@addOnCompleteListener
            }

            try {
                val token = task.result ?: ""
                println("Device Token: $token")
                onTokenReceived(token)
            } catch (e: Exception) {
                println("Token parse error: ${e.message}")
                onTokenReceived("")
            }
        }
}
