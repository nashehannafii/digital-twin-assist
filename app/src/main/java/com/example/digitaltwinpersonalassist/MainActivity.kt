package com.example.digitaltwinpersonalassist

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ismaeldivita.chipnavigation.ChipNavigationBar



/**
 * Activity utama yang menampung navigasi bottom dan memuat fragment utama.
 *
 * Menggunakan `ChipNavigationBar` untuk berpindah antar fragment seperti
 * `HomeFragment`, `StatFragment`, `RecapFragment`, `ProfileFragment`, dan `LoaderFragment`.
 */
class MainActivity : AppCompatActivity() {

    /**
     * Inisialisasi activity: mengaktifkan edge-to-edge, men-setup menu bottom,
     * dan memuat `HomeFragment` sebagai fragment awal.
     *
     * @param savedInstanceState Bundle berisi status sebelumnya, atau null.
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
     * Mengembalikan listener yang menangani pemilihan item di `ChipNavigationBar`.
     * Listener ini mengganti fragment yang ditampilkan sesuai item yang dipilih.
     *
     * @return `ChipNavigationBar.OnItemSelectedListener` yang digunakan pada menu.
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