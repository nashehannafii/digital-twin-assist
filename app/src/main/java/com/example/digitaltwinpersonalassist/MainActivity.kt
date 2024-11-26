package com.example.digitaltwinpersonalassist

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.Manifest
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.json.JSONObject
import java.io.InputStream
import java.io.InputStreamReader
import java.util.Random

class MainActivity : AppCompatActivity() {

    private val random = Random()
    private val simulatedData = mutableListOf<Int>()
    private var currentIndex = 0
    private val handler = Handler(Looper.getMainLooper())

    private val notificationManager by lazy { getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager }
    private val channelId = "heartbeat_channel"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS), 1)
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        createNotificationChannel()

        loadSimulatedData()
        startRealtimeSimulation()
    }


    private fun loadSimulatedData() {
        val jsonData = loadJSONFromAsset("patient_data.json")
        if (jsonData != null) {
            val jsonObject = JSONObject(jsonData)
            val simulationArray = jsonObject.getJSONArray("simulation_data")

            // Add the simulation data to the list
            for (i in 0 until simulationArray.length()) {
                simulatedData.add(simulationArray.getInt(i))
            }
        }
    }

    private fun loadJSONFromAsset(fileName: String): String? {
        var json: String? = null
        try {
            val inputStream: InputStream = assets.open(fileName)
            val inputStreamReader = InputStreamReader(inputStream)
            val bufferedReader = inputStreamReader.readLines()
            json = bufferedReader.joinToString("\n")
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return json
    }

    private fun startRealtimeSimulation() {
        handler.postDelayed(object : Runnable {
            override fun run() {
                if (currentIndex >= simulatedData.size) {
                    currentIndex = 0
                }

                val currentHeartbeat = simulatedData[currentIndex]
                updateUI(currentHeartbeat)

                currentIndex++
                handler.postDelayed(this, 1000)
            }
        }, 500)
    }

    private fun updateUI(heartbeat: Int) {
        val textViewHeartbeat = findViewById<TextView>(R.id.textViewHeartbeat)
        val textViewBpm = findViewById<TextView>(R.id.textViewBpm)
        val textViewStatus = findViewById<TextView>(R.id.textViewStatus)

        textViewHeartbeat.text = "Heartbeat"
        textViewBpm.text = "$heartbeat BPM"

        // Menentukan status berdasarkan detak jantung
        val status = when {
            heartbeat < 60 -> "Rendah"
            heartbeat in 60..100 -> "Normal"
            else -> "Tinggi"
        }

        textViewStatus.text = "Status: $status"

        // Menentukan warna latar belakang berdasarkan status
        when (status) {
            "Normal" -> textViewStatus.setBackgroundColor(resources.getColor(R.color.statusGreen)) // Normal
            "Tinggi" -> textViewStatus.setBackgroundColor(resources.getColor(R.color.statusRed))   // Tinggi
            "Rendah" -> textViewStatus.setBackgroundColor(resources.getColor(R.color.statusYellow)) // Rendah
        }

        when {
            heartbeat > 120 -> {
                sendHeartbeatNotification("Tinggi", heartbeat)
                sendActivityWarningNotification()
            }
            heartbeat < 60 -> {
                sendHeartbeatNotification("Rendah", heartbeat)
                sendLowBloodWarningNotification()
            }
            else -> {
            }
        }
    }

    private fun sendHeartbeatNotification(status: String, heartbeat: Int) {
        val notificationText = when (status) {
            "Tinggi" -> "Detak jantung tinggi!! $heartbeat BPM.\nHarap kurangi intensitas aktivitas Anda!"
            "Rendah" -> "Detak jantung rendah!! $heartbeat BPM.\nPastikan untuk istirahat dan periksa kondisi Anda."
            else -> ""
        }

        val notification = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.star) // Anda bisa mengganti dengan ikon yang sesuai
            .setContentTitle("Peringatan Detak Jantung $status!")
            .setContentText(notificationText)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(1, notification)
    }

    private fun sendActivityWarningNotification() {
        val notification = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.star) // Anda bisa mengganti dengan ikon yang sesuai
            .setContentTitle("Peringatan Aktivitas!")
            .setContentText("Detak jantung Anda terlalu tinggi. Kurangi intensitas aktivitas!")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(2, notification)
    }

    private fun sendLowBloodWarningNotification() {
        val notification = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.star) // Anda bisa mengganti dengan ikon yang sesuai
            .setContentTitle("Peringatan Detak Jantung Rendah!")
            .setContentText("Detak jantung Anda rendah: Pastikan untuk beristirahat dan periksa kondisi Anda!")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(3, notification)
    }

    private fun createNotificationChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val name = "Heartbeat Notifications"
            val descriptionText = "Notifications for heartbeat exceeding 140 BPM"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = descriptionText
            }
            notificationManager.createNotificationChannel(channel)
        }
    }
}
