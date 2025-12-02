package com.example.digitaltwinpersonalassist

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
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
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

/**
 * Fragment utama yang menampilkan simulasi detak jantung secara realtime.
 *
 * Fragment ini menghasilkan nilai detak jantung simulasi, memperbarui UI,
 * dan menyediakan utilitas untuk membuat dan mengirim notifikasi terkait status.
 */
class HomeFragment : Fragment() {

    private lateinit var view : View
    private var currentIndex = 0

    private lateinit var handler : Handler

    private val notificationManager by lazy {
        requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    private val channelId = "heartbeat_channel"

    /**
     * Membuat view untuk fragment ini, menyiapkan handler dan meminta permission
     * notifikasi bila diperlukan (Android 13+), lalu memulai simulasi realtime.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)

        view = inflater.inflate(R.layout.fragment_home, container, false)

        handler = Handler(Looper.getMainLooper())

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    requireContext(), Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    1
                )
            }
        }

//        createNotificationChannel()
        startRealtimeSimulation()


        return view
    }

    /**
     * Membersihkan callback handler saat view dihancurkan.
     */
    override fun onDestroyView() {
        super.onDestroyView()

        handler.removeCallbacksAndMessages(null)
    }

    /**
     * Memulai simulasi detak jantung berulang setiap 1 detik.
     */
    private fun startRealtimeSimulation() {
        handler.postDelayed(object : Runnable {
            override fun run() {
                val simulatedHeartbeat = generateHeartbeat()
                updateUI(simulatedHeartbeat)
                handler.postDelayed(this, 1000)
            }
        }, 1000)
    }

    /**
     * Menghasilkan nilai detak jantung acak dalam rentang 50..150.
     *
     * @return Int nilai detak jantung ter-simulasikan.
     */
    private fun generateHeartbeat(): Int {
        return (50..150).random()
    }

    /**
     * Memperbarui elemen UI yang menampilkan detak jantung, BPM, dan status.
     *
     * @param heartbeat nilai detak jantung untuk ditampilkan.
     */
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
            else -> textViewStatus.setBackgroundColor(resources.getColor(R.color.statusWhite))
        }

        // Contoh pemberitahuan berdasarkan status, jika perlu
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


    /**
     * Mengirim notifikasi peringatan detak jantung dengan konten tergantung status.
     *
     * @param status Status detak jantung (mis. "Tinggi", "Rendah").
     * @param heartbeat Nilai detak jantung saat ini.
     */
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

    /**
     * Mengirim notifikasi peringatan aktivitas jika detak jantung terlalu tinggi.
     */
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

    /**
     * Mengirim notifikasi peringatan jika detak jantung terlalu rendah.
     */
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

    /**
     * Membuat channel notifikasi untuk Android O+.
     */
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