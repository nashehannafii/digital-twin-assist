package com.example.digitaltwinpersonalassist

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import java.util.Random


class StatFragment : Fragment() {
    private lateinit var view : View

    private lateinit var lineChartLC2: LineChart
    private lateinit var lineDataLC2: LineData
    private lateinit var lineDataSetLC2: LineDataSet
    private val dataPointsLC2 = mutableListOf<Entry>()
    private val handlerLC2 = Handler(Looper.getMainLooper())
    private val randomLC2 = Random()
    private var timeLC2 = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)

        view = inflater.inflate(R.layout.fragment_stat, container, false)

        lineChartLC2 = view.findViewById(R.id.lineChart)
        setupChart()
        startRealTimeUpdates()

        return view
    }

    private fun setupChart() {
        lineDataSetLC2 = createLineDataSet()
        lineDataLC2 = LineData(lineDataSetLC2)

        lineChartLC2.apply {
            data = lineDataLC2
            description.isEnabled = false
            setTouchEnabled(false)

            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                setDrawGridLines(false)
            }

            axisLeft.apply {
                setDrawGridLines(false)
            }

            axisRight.isEnabled = false
        }
    }

    private fun createLineDataSet(): LineDataSet {
        return LineDataSet(dataPointsLC2, "ECG Waveform").apply {
            lineWidth = 3f // Garis lebih tebal
            color = resources.getColor(android.R.color.holo_red_dark, null) // Warna merah
            setDrawCircles(false)
            setDrawValues(false)
        }
    }

    private fun startRealTimeUpdates() {
        handlerLC2.post(object : Runnable {
            override fun run() {
                addECGDataPoint() // Gunakan fungsi baru
                updateChart()
                handlerLC2.postDelayed(this, 50) // Interval 50ms untuk ritme lebih lambat
            }
        })
    }

    private fun stopRealTimeUpdates() {
        handlerLC2.removeCallbacksAndMessages(null)
    }

    private fun updateChart() {
        lineDataSetLC2.notifyDataSetChanged()
        lineDataLC2.notifyDataChanged()
        lineChartLC2.notifyDataSetChanged()
        lineChartLC2.invalidate()
    }

    private fun addECGDataPoint() {
        val x = timeLC2 * 0.1 // Skala waktu untuk memperlambat ritme
        val ecgValue = generateECGRhythm(x)
        dataPointsLC2.add(Entry(timeLC2++.toFloat(), ecgValue))

        // Hapus data lama untuk menjaga ukuran dataset
        if (dataPointsLC2.size > 100) { // Simpan hingga 100 data
            dataPointsLC2.removeAt(0)
            dataPointsLC2.forEachIndexed { index, entry -> entry.x = index.toFloat() }
        }
    }

    private fun generateECGRhythm(x: Double): Float {
        return when {
            // Simulasi P-wave (gelombang kecil sebelum QRS)
            x % 2.0 < 0.2 -> (Math.sin(10 * x) * 5).toFloat() // Amplitudo rendah

            // Simulasi QRS complex
            x % 2.0 < 0.5 -> when {
                x % 2.0 < 0.3 -> -30f // Q dip (negatif tajam)
                x % 2.0 < 0.4 -> 60f  // R peak (puncak tinggi)
                else -> -20f          // S dip (negatif rendah)
            }

            // Simulasi T-wave (gelombang melengkung setelah QRS)
            x % 2.0 < 1.2 -> (Math.sin(4 * x) * 15).toFloat() // T-wave amplitudo sedang

            // Baseline antara siklus
            else -> 0f // Nilai dasar
        }
    }
}