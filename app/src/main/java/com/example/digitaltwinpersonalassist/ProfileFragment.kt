package com.example.digitaltwinpersonalassist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader


/**
 * Fragment yang menampilkan profil pengguna.
 *
 * Saat ini berfungsi sebagai placeholder dan menyiapkan view dari layout
 * `fragment_profile`.
 */
class ProfileFragment : Fragment() {
    private lateinit var view : View

    /**
     * Membuat dan mengembalikan view untuk fragment ini.
     *
     * @param inflater LayoutInflater untuk meng-inflate layout.
     * @param container ViewGroup parent atau null.
     * @param savedInstanceState Bundle berisi state sebelumnya atau null.
     * @return View yang di-inflate untuk fragment ini.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)

        view = inflater.inflate(R.layout.fragment_profile, container, false)

        return view
    }

}