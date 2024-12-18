package com.example.digitaltwinpersonalassist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.digitaltwinpersonalassist.services.api.ApiClient
import com.example.digitaltwinpersonalassist.services.api.ApiService
import com.example.digitaltwinpersonalassist.services.models.DayData
import com.example.digitaltwinpersonalassist.services.network.datasource.HelloDS
import com.example.digitaltwinpersonalassist.services.network.datasource.RDailyDS
import com.example.digitaltwinpersonalassist.services.network.datasource.RWeeklyDS
import com.example.digitaltwinpersonalassist.services.network.remote.HelloRDS
import com.example.digitaltwinpersonalassist.services.network.remote.RDailyRDS
import com.example.digitaltwinpersonalassist.services.network.remote.RWeeklyRDS
import com.example.digitaltwinpersonalassist.services.repository.HelloRepo
import com.example.digitaltwinpersonalassist.services.repository.RDailyRepo
import com.example.digitaltwinpersonalassist.services.repository.RWeeklyRepo
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


class RecapFragment : Fragment() {
    private lateinit var view : View
    private val simulatedData = mutableListOf<Int>()
    lateinit var barChartWeekly : BarChart
    lateinit var barChartMonthly : BarChart
    private lateinit var apiService: ApiService


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)

        view = inflater.inflate(R.layout.fragment_recap, container, false)

        barChartWeekly = view.findViewById<BarChart>(R.id.barChartWeekly)
        barChartMonthly = view.findViewById<BarChart>(R.id.barChartMonthly)

        apiService = ApiClient().getCLient().create(ApiService::class.java)
//        val helloDs = HelloRDS(apiService)
//        val helloRepo = HelloRepo(helloDs)

//        helloRepo.getData(object : HelloDS.HelloCallback {
//            override fun onLoaded(msg: String) {
//            }
//            override fun onError(msg: String) {
//            }
//        })


        val rDailyDs = RDailyRDS(apiService)
        val rDailyRepo = RDailyRepo(rDailyDs)

        val rWeeklyDs = RWeeklyRDS(apiService)
        val rWeeklyRepo = RWeeklyRepo(rWeeklyDs)

        rDailyRepo.getData("4", "11", object : RDailyDS.RDailyCallback {
            override fun onLoaded(msg: String) {

            }
            override fun onError(msg: String) {
            }
        })

        rWeeklyRepo.getData("1","12", object : RWeeklyDS.RWeeklyCallback {
            override fun onLoaded(data: Map<String, DayData>) {
                val dataMap = mutableListOf<BarEntry>()
                var i = 0
                for (k in data.keys)
                {
                    if (i == 7)
                        break
                    dataMap.add(BarEntry(k.toFloat(), data[k]?.averageHeartbeatRate ?: 0f))
                    i++;
                }

                val weeklyDataSet = BarDataSet(dataMap, "Pekan Ini")
                weeklyDataSet.color = ContextCompat.getColor(requireContext(), R.color.darkGreen)
                barChartWeekly.data = BarData(weeklyDataSet)
                barChartWeekly.invalidate()
            }
            override fun onError(msg: String) {
            }
        })

        loadSimulatedData()
        currentData()

        return view
    }

    private fun currentData(){
        val weeklyEntries = listOf(
            BarEntry(1f, 0f),
            BarEntry(2f, 0f),
            BarEntry(3f, 0f),
            BarEntry(4f, 0f),
            BarEntry(5f, 0f),
            BarEntry(6f, 0f),
            BarEntry(7f, 0f)
        )
        val monthlyEntries = listOf(
            BarEntry(1f, 70f),
            BarEntry(2f, 72f),
            BarEntry(3f, 68f),
            BarEntry(4f, 74f)
        )
        val weeklyDataSet = BarDataSet(weeklyEntries, "Pekan Ini")
        val monthlyDataSet = BarDataSet(monthlyEntries, "Bulan Ini")

        weeklyDataSet.color = ContextCompat.getColor(requireContext(), R.color.darkGreen)
        monthlyDataSet.color = ContextCompat.getColor(requireContext(), R.color.grey)

        barChartWeekly.data = BarData(weeklyDataSet)
        barChartMonthly.data = BarData(monthlyDataSet)

        val maxHeartRate = 105f

        val daysOfWeek = arrayOf("", "Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu", "Minggu")
        barChartWeekly.xAxis.apply {
            valueFormatter = IndexAxisValueFormatter(daysOfWeek)
            granularity = 1f
            position = XAxis.XAxisPosition.BOTTOM
            setDrawGridLines(false)
        }

        barChartWeekly.axisLeft.apply {
            axisMinimum = 75f
            axisMaximum = maxHeartRate
        }
        barChartWeekly.axisRight.isEnabled = false

        barChartWeekly.description.text = ""
        barChartWeekly.description.textSize = 12f

        barChartMonthly.xAxis.apply {
            valueFormatter = IndexAxisValueFormatter(arrayOf("", "Minggu 1", "Minggu 2", "Minggu 3", "Minggu 4"))
            granularity = 1f
            position = XAxis.XAxisPosition.BOTTOM
            setDrawGridLines(false)
        }

        barChartMonthly.axisLeft.apply {
            axisMinimum = 0f
            axisMaximum = maxHeartRate
        }
        barChartMonthly.axisRight.isEnabled = false
        barChartMonthly.description.text = ""
        barChartMonthly.description.textSize = 12f

        barChartWeekly.invalidate()
        barChartMonthly.invalidate()

    }

    private fun loadSimulatedData() {
        val jsonData = loadJSONFromAsset("patient_data.json")
        if (jsonData != null) {
            val jsonObject = JSONObject(jsonData)
            val simulationArray = jsonObject.getJSONArray("simulation_data")

            // Add the simulation data to the list
            for (i in 0 until simulationArray.length()) {
                simulatedData.add(simulationArray.getInt(i))
            }
        }
    }

    private fun loadJSONFromAsset(fileName: String): String? {
        var json: String? = null
        try {
            val inputStream: InputStream = requireContext().assets.open(fileName)
            val inputStreamReader = InputStreamReader(inputStream)
            val bufferedReader = BufferedReader(inputStreamReader)
            json = bufferedReader.use { it.readText() } // Safely read all text
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return json
    }
}