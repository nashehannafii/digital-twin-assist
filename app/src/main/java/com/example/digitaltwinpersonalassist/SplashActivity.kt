package com.example.digitaltwinpersonalassist

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.digitaltwinpersonalassist.databinding.ActivitySplashBinding

/**
 * Activity splash sederhana yang menampilkan tombol untuk masuk ke `MainActivity`.
 *
 * Menggunakan view binding `ActivitySplashBinding` untuk menghubungkan layout.
 */
class SplashActivity : BaseActivity() {
    private lateinit var binding: ActivitySplashBinding

    /**
     * Inisialisasi tampilan splash dan mengatur listener tombol "Start".
     *
     * @param savedInstanceState Bundle berisi status sebelumnya, atau null.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            startBtn.setOnClickListener {
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            }
        }
    }
}