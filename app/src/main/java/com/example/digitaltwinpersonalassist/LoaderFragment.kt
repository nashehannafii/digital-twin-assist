package com.example.digitaltwinpersonalassist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Simple fragment that displays a loading screen (loader).
 *
 * Currently it only inflates the `fragment_loader` layout and returns the view.
 */
class LoaderFragment : Fragment() {

    private lateinit var view : View

    /**
     * Inflate the `fragment_loader` layout.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view = inflater.inflate(R.layout.fragment_loader, container, false)
        return view
    }

}