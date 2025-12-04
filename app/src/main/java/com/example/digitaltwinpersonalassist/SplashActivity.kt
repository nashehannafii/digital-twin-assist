package com.example.digitaltwinpersonalassist

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.digitaltwinpersonalassist.databinding.ActivitySplashBinding

/**
 * Simple splash Activity that shows a button to enter `MainActivity`.
 *
 * Uses view binding `ActivitySplashBinding` to bind the layout.
 */
class SplashActivity : BaseActivity() {
    private lateinit var binding: ActivitySplashBinding

    /**
     * Initialize the splash view and set the "Start" button listener.
     *
     * @param savedInstanceState Bundle with previous state, or null.
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