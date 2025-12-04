package com.example.digitaltwinpersonalassist

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ismaeldivita.chipnavigation.ChipNavigationBar



/**
 * Main activity that hosts the bottom navigation and loads the main fragments.
 *
 * Uses `ChipNavigationBar` to switch between fragments such as
 * `HomeFragment`, `StatFragment`, `RecapFragment`, `ProfileFragment`, and `LoaderFragment`.
 */
class MainActivity : AppCompatActivity() {

    /**
     * Activity initialization: enables edge-to-edge, sets up the bottom menu,
     * and loads `HomeFragment` as the initial fragment.
     *
     * @param savedInstanceState Bundle with previous state, or null.
     */
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val menu: ChipNavigationBar = findViewById(R.id.menu_helper)
        menu.setItemSelected(R.id.home)
        menu.setOnItemSelectedListener(navListener())

        val selectedFragment : Fragment = HomeFragment()
        supportFragmentManager.beginTransaction().replace(
            R.id.fragment_container, selectedFragment
        ).commit()

    }

    /**
     * Return a listener that handles item selection on the `ChipNavigationBar`.
     * The listener swaps the displayed fragment based on the selected item.
     *
     * @return `ChipNavigationBar.OnItemSelectedListener` used by the menu.
     */
    private fun navListener(): ChipNavigationBar.OnItemSelectedListener {
        return object : ChipNavigationBar.OnItemSelectedListener {
            override fun onItemSelected(id: Int) {

                val selectedFragment : Fragment = when (id) {
                    R.id.home -> HomeFragment()
                    R.id.stat -> StatFragment()
                    R.id.recap -> RecapFragment()
                    R.id.profile -> ProfileFragment()
                    R.id.loader -> LoaderFragment()
                    else -> HomeFragment()
                }

                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, selectedFragment).commit()

            }
        }
    }


}