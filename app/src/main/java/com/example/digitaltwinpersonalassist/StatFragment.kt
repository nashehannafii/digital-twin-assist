package com.example.digitaltwinpersonalassist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup



class StatFragment : Fragment() {
    private lateinit var view : View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)

        view = inflater.inflate(R.layout.fragment_stat, container, false)

        return view
    }
}