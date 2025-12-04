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
 * Fragment that displays the user's profile.
 *
 * Currently serves as a placeholder and inflates the `fragment_profile` layout.
 */
class ProfileFragment : Fragment() {
    private lateinit var view : View

    /**
     * Create and return the view for this fragment.
     *
     * @param inflater LayoutInflater to inflate the layout.
     * @param container Parent ViewGroup or null.
     * @param savedInstanceState Bundle with previous state or null.
     * @return The inflated View for this fragment.
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