package com.example.pressreview.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pressreview.R
import com.example.pressreview.adapter.RecyclerViewAdapter
import com.example.pressreview.constants.Resource
import com.example.pressreview.databinding.BreakingnewFragmentBinding
import com.example.pressreview.model.MyViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BreakingNewsFragment: Fragment(R.layout.breakingnew_fragment) {
    lateinit var MyAdapter:RecyclerViewAdapter
    lateinit var binding: BreakingnewFragmentBinding
     val viewModel:MyViewModel by viewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = BreakingnewFragmentBinding.inflate(layoutInflater)
        setupRV()

        MyAdapter.setOnItemClickListener {

            val bundle = Bundle().apply {
                putSerializable("article",it)
            }
            findNavController().navigate(
                    R.id.action_breakingNewsFragment_to_newsDetails,
                    bundle
            )

        }


    viewModel.myBreakingNews.observe(viewLifecycleOwner, Observer { response ->
        when(response){
            is Resource.Loading -> {showProgressBar()}

            is Resource.Error ->
            {
                 hideProgressBar()
               Log.d("MainActivity", "An Error occurred: {$response.Message}")
            }

            is Resource.Success ->
            {
                hideProgressBar()
                response.data?.let {
                    MyAdapter.differ.submitList(it.articles)
                }
            }

        }
    })





        return binding.root
    }

fun showProgressBar(){
    binding.progressBar2.visibility = View.VISIBLE
}
    fun hideProgressBar(){
    binding.progressBar2.visibility = View.INVISIBLE
}
    fun setupRV(){
        MyAdapter = RecyclerViewAdapter(this@BreakingNewsFragment.requireContext())
        binding.breakingNewsRv.apply {
            adapter = MyAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}