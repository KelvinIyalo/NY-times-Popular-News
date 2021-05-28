package com.example.pressreview.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.pressreview.R
import com.example.pressreview.databinding.BreakingnewFragmentBinding

class BreakingNewsFragment : Fragment(R.layout.breakingnew_fragment) {
    lateinit var binding: BreakingnewFragmentBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = BreakingnewFragmentBinding.inflate(layoutInflater)







        return binding.root
    }


}