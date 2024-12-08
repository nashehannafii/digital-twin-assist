package com.example.digitaltwinpersonalassist

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.digitaltwinpersonalassist.services.api.ApiClient
import com.example.digitaltwinpersonalassist.services.api.ApiService
import com.example.digitaltwinpersonalassist.services.network.datasource.HelloDS
import com.example.digitaltwinpersonalassist.services.network.datasource.RDailyDS
import com.example.digitaltwinpersonalassist.services.network.remote.HelloRDS
import com.example.digitaltwinpersonalassist.services.network.remote.RDailyRDS
import com.example.digitaltwinpersonalassist.services.repository.HelloRepo
import com.example.digitaltwinpersonalassist.services.repository.RDailyRepo
import org.json.JSONObject
import java.io.InputStream
import java.io.InputStreamReader
import java.util.Random
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val random = Random()
    private val simulatedData = mutableListOf<Int>()
    private var currentIndex = 0
    private val handler = Handler(Looper.getMainLooper())

    private val notificationManager by lazy { getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager }
    private val channelId = "heartbeat_channel"

    private lateinit var apiService:ApiService

    @SuppressLint("MissingInflatedId")
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


        apiService = ApiClient().getCLient().create(ApiService::class.java)
        val helloDs = HelloRDS(apiService)
        val helloRepo = HelloRepo(helloDs)

        val rDailyDs = RDailyRDS(apiService)
        val rDailyRepo = RDailyRepo(rDailyDs)

        helloRepo.getData(object : HelloDS.HelloCallback {
            override fun onLoaded(msg: String) {
            }
            override fun onError(msg: String) {
            }
        })
        rDailyRepo.getData("4", "11", object : RDailyDS.RDailyCallback {
            override fun onLoaded(msg: String) {
            }
            override fun onError(msg: String) {
            }

        })

        createNotificationChannel()

        loadSimulatedData()
        startRealtimeSimulation()

        tesData()
    }

    private fun tesData(){

        val barChartWeekly = findViewById<BarChart>(R.id.barChartWeekly)
        val barChartMonthly = findViewById<BarChart>(R.id.barChartMonthly)

        val weeklyEntries = listOf(
            BarEntry(1f, 72f),
            BarEntry(2f, 75f),
            BarEntry(3f, 70f),
            BarEntry(4f, 73f),
            BarEntry(5f, 74f),
            BarEntry(6f, 71f),
            BarEntry(7f, 69f)
        )

        val monthlyEntries = listOf(
            BarEntry(1f, 70f),
            BarEntry(2f, 72f),
            BarEntry(3f, 68f),
            BarEntry(4f, 74f)
        )

        val weeklyDataSet = BarDataSet(weeklyEntries, "Pekan Ini")
        val monthlyDataSet = BarDataSet(monthlyEntries, "Bulan Ini")

        weeklyDataSet.color = ContextCompat.getColor(this, R.color.darkGreen)
        monthlyDataSet.color = ContextCompat.getColor(this, R.color.grey)

        barChartWeekly.data = BarData(weeklyDataSet)
        barChartMonthly.data = BarData(monthlyDataSet)

        val maxWeekly = weeklyEntries.maxOf { it.y }
        val maxMonthly = monthlyEntries.maxOf { it.y }
        val maxHeartRate = maxOf(maxWeekly, maxMonthly) + 10f // Tambahkan margin 10

        val daysOfWeek = arrayOf("", "Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu", "Minggu")
        barChartWeekly.xAxis.apply {
            valueFormatter = IndexAxisValueFormatter(daysOfWeek)
            granularity = 1f
            position = XAxis.XAxisPosition.BOTTOM
            setDrawGridLines(false)
        }

        barChartWeekly.axisLeft.apply {
            axisMinimum = 0f
            axisMaximum = maxHeartRate
        }
        barChartWeekly.axisRight.isEnabled = false

        barChartWeekly.description.text = ""
        barChartWeekly.description.textSize = 12f

        barChartMonthly.xAxis.apply {
            valueFormatter = IndexAxisValueFormatter(arrayOf("", "Minggu 1", "Minggu 2", "Minggu 3", "Minggu 4"))
            granularity = 1f
            position = XAxis.XAxisPosition.BOTTOM
            setDrawGridLines(false)
        }

        barChartMonthly.axisLeft.apply {
            axisMinimum = 0f
            axisMaximum = maxHeartRate // Batas maksimum detak jantung
        }
        barChartMonthly.axisRight.isEnabled = false // Nonaktifkan sumbu Y kanan

        barChartMonthly.description.text = ""
        barChartMonthly.description.textSize = 12f

        barChartWeekly.invalidate()
        barChartMonthly.invalidate()

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
        }, 1000)
    }

    private fun updateUI(heartbeat: Int) {
        val textViewHeartbeat = findViewById<TextView>(R.id.textViewHeartbeat)
        val textViewBpm = findViewById<TextView>(R.id.textViewBpm)
        val textViewStatus = findViewById<TextView>(R.id.textViewStatus)

        textViewHeartbeat.text = "Heartbeat"
        textViewBpm.text = "$heartbeat BPM"

        val status = when {
            heartbeat < 60 -> "Rendah"
            heartbeat in 60..100 -> "Normal"
            else -> "Tinggi"
        }

        textViewStatus.text = "Status: $status"

        when (status) {
            "Normal" -> textViewStatus.setBackgroundColor(resources.getColor(R.color.statusGreen)) // Normal
            "Tinggi" -> textViewStatus.setBackgroundColor(resources.getColor(R.color.statusRed))   // Tinggi
            "Rendah" -> textViewStatus.setBackgroundColor(resources.getColor(R.color.statusYellow)) // Rendah
        }

        when {
            heartbeat > 120 -> {
//                sendHeartbeatNotification("Tinggi", heartbeat)
//                sendActivityWarningNotification()
            }
            heartbeat < 60 -> {
//                sendHeartbeatNotification("Rendah", heartbeat)
//                sendLowBloodWarningNotification()
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
            .setSmallIcon(R.drawable.star)
            .setContentTitle("Peringatan Detak Jantung $status!")
            .setContentText(notificationText)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(1, notification)
    }

    private fun sendActivityWarningNotification() {
        val notification = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.star)
            .setContentTitle("Peringatan Aktivitas!")
            .setContentText("Detak jantung Anda terlalu tinggi. Kurangi intensitas aktivitas!")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(2, notification)
    }

    private fun sendLowBloodWarningNotification() {
        val notification = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.star)
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
