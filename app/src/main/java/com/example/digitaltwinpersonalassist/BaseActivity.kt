package com.example.digitaltwinpersonalassist

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

/**
 * BaseActivity is the base activity used by other activities in the app.
 *
 * This activity sets a window flag so the layout can take the full screen
 * area (e.g. show content behind status/navigation bars) enabling child
 * activities to implement edge-to-edge UI.
 */
open class BaseActivity : AppCompatActivity() {
    /**
     * Called when the Activity is created.
     *
     * This method calls the parent implementation and sets the window flag
     * `FLAG_LAYOUT_NO_LIMITS` so the layout is not constrained by system
     * bars.
     *
     * @param savedInstanceState Bundle with previous state, or null.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }
}