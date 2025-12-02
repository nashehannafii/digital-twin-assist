package com.example.digitaltwinpersonalassist

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

/**
 * BaseActivity adalah activity dasar yang digunakan oleh activity lain di aplikasi.
 *
 * Activity ini mengatur flag window agar layout dapat mengambil area layar penuh
 * (mis. menampilkan konten di balik status bar / navigation bar) sehingga
 * child activities dapat mengimplementasikan tampilan edge-to-edge.
 */
open class BaseActivity : AppCompatActivity() {
    /**
     * Dipanggil saat Activity dibuat.
     *
     * Fungsi ini hanya memanggil implementasi parent lalu mengatur flag window
     * `FLAG_LAYOUT_NO_LIMITS` sehingga layout tidak dibatasi oleh area sistem.
     *
     * @param savedInstanceState Bundle berisi status sebelumnya, atau null.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }
}