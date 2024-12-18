package com.example.digitaltwinpersonalassist

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ismaeldivita.chipnavigation.ChipNavigationBar



class MainActivity : AppCompatActivity() {

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

    private fun navListener(): ChipNavigationBar.OnItemSelectedListener {
        return object : ChipNavigationBar.OnItemSelectedListener {
            override fun onItemSelected(id: Int) {

                val selectedFragment : Fragment = when (id) {
                    R.id.home -> HomeFragment()
                    R.id.stat -> StatFragment()
                    R.id.recap -> RecapFragment()
                    R.id.profile -> ProfileFragment()
                    else -> HomeFragment()
                }

                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, selectedFragment).commit()

            }
        }
    }


}