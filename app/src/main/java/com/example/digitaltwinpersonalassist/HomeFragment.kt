package com.example.digitaltwinpersonalassist

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.Manifest
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.util.Random

class HomeFragment : Fragment() {

    private lateinit var view : View
    private val simulatedData = mutableListOf<Int>()
    private var currentIndex = 0
    private val handler = Handler(Looper.getMainLooper())

    private val notificationManager by lazy {
        requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    private val channelId = "heartbeat_channel"


    private lateinit var lineChartLC2: LineChart
    private lateinit var lineDataLC2: LineData
    private lateinit var lineDataSetLC2: LineDataSet
    private val dataPointsLC2 = mutableListOf<Entry>()
    private val handlerLC2 = Handler(Looper.getMainLooper())
    private val randomLC2 = Random()
    private var timeLC2 = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)

        view = inflater.inflate(R.layout.fragment_home, container, false)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    requireContext(), Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // Request the permission
                requestPermissions(
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    1
                )
            }
        }

        createNotificationChannel()

//        startRealtimeSimulation()

        lineChartLC2 = view.findViewById(R.id.lineChart)
        setupChart()
        startRealTimeUpdates()

        return view
    }

    private fun setupChart() {
        lineDataSetLC2 = createLineDataSet()
        lineDataLC2 = LineData(lineDataSetLC2)

        lineChartLC2.apply {
            data = lineDataLC2
            description.isEnabled = false
            setTouchEnabled(false)

            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                setDrawGridLines(false)
            }

            axisLeft.apply {
                setDrawGridLines(false)
            }

            axisRight.isEnabled = false
        }
    }

    private fun createLineDataSet(): LineDataSet {
        return LineDataSet(dataPointsLC2, "ECG Waveform").apply {
            lineWidth = 3f // Garis lebih tebal
            color = resources.getColor(android.R.color.holo_red_dark, null) // Warna merah
            setDrawCircles(false)
            setDrawValues(false)
        }
    }

    private fun startRealTimeUpdates() {
        handlerLC2.post(object : Runnable {
            override fun run() {
                addECGDataPoint() // Gunakan fungsi baru
                updateChart()
                handlerLC2.postDelayed(this, 50) // Interval 50ms untuk ritme lebih lambat
            }
        })
    }

    private fun stopRealTimeUpdates() {
        handlerLC2.removeCallbacksAndMessages(null)
    }

    private fun updateChart() {
        lineDataSetLC2.notifyDataSetChanged()
        lineDataLC2.notifyDataChanged()
        lineChartLC2.notifyDataSetChanged()
        lineChartLC2.invalidate()
    }

    private fun addECGDataPoint() {
        val x = timeLC2 * 0.1 // Skala waktu untuk memperlambat ritme
        val ecgValue = generateECGRhythm(x)
        dataPointsLC2.add(Entry(timeLC2++.toFloat(), ecgValue))

        // Hapus data lama untuk menjaga ukuran dataset
        if (dataPointsLC2.size > 100) { // Simpan hingga 100 data
            dataPointsLC2.removeAt(0)
            dataPointsLC2.forEachIndexed { index, entry -> entry.x = index.toFloat() }
        }
    }

    private fun generateECGRhythm(x: Double): Float {
        return when {
            // Simulasi P-wave (gelombang kecil sebelum QRS)
            x % 2.0 < 0.2 -> (Math.sin(10 * x) * 5).toFloat() // Amplitudo rendah

            // Simulasi QRS complex
            x % 2.0 < 0.5 -> when {
                x % 2.0 < 0.3 -> -30f // Q dip (negatif tajam)
                x % 2.0 < 0.4 -> 60f  // R peak (puncak tinggi)
                else -> -20f          // S dip (negatif rendah)
            }

            // Simulasi T-wave (gelombang melengkung setelah QRS)
            x % 2.0 < 1.2 -> (Math.sin(4 * x) * 15).toFloat() // T-wave amplitudo sedang

            // Baseline antara siklus
            else -> 0f // Nilai dasar
        }
    }

    private fun loadJSONFromAsset(fileName: String): String? {
        var json: String? = null
        try {
            val inputStream: InputStream = requireContext().assets.open(fileName)
            val inputStreamReader = InputStreamReader(inputStream)
            val bufferedReader = BufferedReader(inputStreamReader)
            json = bufferedReader.use { it.readText() } // Safely read all text
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return json
    }

    private fun startRealtimeSimulation() {
        handlerLC2.postDelayed(object : Runnable {
            override fun run() {
                if (currentIndex >= simulatedData.size) {
                    currentIndex = 0
                }

                val currentHeartbeat = simulatedData[currentIndex]
                updateUI(currentHeartbeat)

                currentIndex++
                handlerLC2.postDelayed(this, 1000)
            }
        }, 1000)
    }

    private fun updateUI(heartbeat: Int) {
        val textViewHeartbeat = view.findViewById<TextView>(R.id.textViewHeartbeat)
        val textViewBpm = view.findViewById<TextView>(R.id.textViewBpm)
        val textViewStatus = view.findViewById<TextView>(R.id.textViewStatus)

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

        val notification = NotificationCompat.Builder(requireContext(), channelId) // Use requireContext()
            .setSmallIcon(R.drawable.star)
            .setContentTitle("Peringatan Detak Jantung $status!")
            .setContentText(notificationText)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(1, notification)
    }

    private fun sendActivityWarningNotification() {
        val notification = NotificationCompat.Builder(requireContext(), channelId) // Use requireContext()
            .setSmallIcon(R.drawable.star)
            .setContentTitle("Peringatan Aktivitas!")
            .setContentText("Detak jantung Anda terlalu tinggi. Kurangi intensitas aktivitas!")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(2, notification)
    }

    private fun sendLowBloodWarningNotification() {
        val notification = NotificationCompat.Builder(requireContext(), channelId) // Use requireContext()
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