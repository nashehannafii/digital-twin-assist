package com.example.digitaltwinpersonalassist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Fragment sederhana yang menampilkan layar pemuatan (loader).
 *
 * Saat ini hanya meng-inflate layout `fragment_loader` dan mengembalikan view.
 */
class LoaderFragment : Fragment() {

    private lateinit var view : View

    /**
     * Meng-inflate layout `fragment_loader`.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view = inflater.inflate(R.layout.fragment_loader, container, false)
        return view
    }

}