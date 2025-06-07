package com.example.lab11

import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestNotificationPermission()

        findViewById<Button>(R.id.btnBasic).setOnClickListener { showBasicNotification() }
        findViewById<Button>(R.id.btnBigText).setOnClickListener { showBigTextNotification() }
        findViewById<Button>(R.id.btnInbox).setOnClickListener { showInboxNotification() }
        findViewById<Button>(R.id.btnBigPicture).setOnClickListener { showBigPictureNotification() }
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                    1
                )
            }
        }
    }

    private fun getNotificationId(): Int = Date().time.toInt()

    private fun showBasicNotification() {
        val largeIcon = BitmapFactory.decodeResource(resources, R.drawable.notification_icon)
        val notification = NotificationCompat.Builder(this, MyApplication.CHANNEL_ID)
            .setContentTitle("FPT Send New Message")
            .setContentText("Hi, Welcome to FPT University")
            .setSmallIcon(R.drawable.notification_icon)
            .setLargeIcon(largeIcon)
            .build()

        try {
            NotificationManagerCompat.from(this).notify(getNotificationId(), notification)
        } catch (e: SecurityException) {
            // Handle missing POST_NOTIFICATIONS permission gracefully
            e.printStackTrace()
        }
    }

    private fun showBigTextNotification() {
        val largeIcon = BitmapFactory.decodeResource(resources, R.drawable.notification_icon)
        val bigText = NotificationCompat.BigTextStyle()
            .bigText("Welcome to FPT, it provides tutorials related to all technologies in software industry. Here we cover complete tutorials from basic to advanced topics.")
            .setSummaryText("By: FU")

        val notification = NotificationCompat.Builder(this, MyApplication.CHANNEL_ID)
            .setContentTitle("FPT Send New Message")
            .setContentText("Hi, Welcome to FPT University")
            .setSmallIcon(R.drawable.notification_icon)
            .setLargeIcon(largeIcon)
            .setStyle(bigText)
            .build()

        try {
            NotificationManagerCompat.from(this).notify(getNotificationId(), notification)
        } catch (e: SecurityException) {
            // Handle missing POST_NOTIFICATIONS permission gracefully
            e.printStackTrace()
        }
    }

    private fun showInboxNotification() {
        val largeIcon = BitmapFactory.decodeResource(resources, R.drawable.notification_icon)
        val inboxStyle = NotificationCompat.InboxStyle()
            .addLine("Message 1.")
            .addLine("Message 2.")
            .addLine("Message 3.")
            .addLine("Message 4.")
            .addLine("Message 5.")
            .setSummaryText("+2 more")

        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val notification = NotificationCompat.Builder(this, MyApplication.CHANNEL_ID)
            .setContentTitle("FPT Send New Message")
            .setContentText("Hi, Welcome to FPT University")
            .setSmallIcon(R.drawable.notification_icon)
            .setLargeIcon(largeIcon)
            .setStyle(inboxStyle)
            .setContentIntent(pendingIntent)
            .build()

        try {
            NotificationManagerCompat.from(this).notify(getNotificationId(), notification)
        } catch (e: SecurityException) {
            // Handle missing POST_NOTIFICATIONS permission gracefully
            e.printStackTrace()
        }
    }

    private fun showBigPictureNotification() {
        val largeIcon = BitmapFactory.decodeResource(resources, R.drawable.notification_icon)
        val bigPicture = NotificationCompat.BigPictureStyle()
            .bigPicture(BitmapFactory.decodeResource(resources, R.drawable.logo_university))

        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://dotnet.vn/"))
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val notification = NotificationCompat.Builder(this, MyApplication.CHANNEL_ID)
            .setContentTitle("FPT Send New Message")
            .setContentText("Hi, Welcome to FPT University")
            .setSmallIcon(R.drawable.notification_icon)
            .setLargeIcon(largeIcon)
            .setStyle(bigPicture)
            .addAction(R.drawable.notification_icon, "Share", pendingIntent)
            .setContentIntent(pendingIntent)
            .build()

        try {
            NotificationManagerCompat.from(this).notify(getNotificationId(), notification)
        } catch (e: SecurityException) {
            // Handle missing POST_NOTIFICATIONS permission gracefully
            e.printStackTrace()
        }
    }
}
