package com.example.pressreview.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pressreview.R
import com.example.pressreview.adapter.RecyclerViewAdapter
import com.example.pressreview.constants.Resource
import com.example.pressreview.databinding.MajorNewsFragmentBinding
import com.example.pressreview.model.MyViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MajorNewsFragment : Fragment(R.layout.major_news_fragment) {
    lateinit var MyAdapter: RecyclerViewAdapter
    lateinit var binding: MajorNewsFragmentBinding
    val viewModel: MyViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MajorNewsFragmentBinding.inflate(layoutInflater)

        setupRV()

        MyAdapter.setOnItemClickListener {

            val bundle = Bundle().apply {
                putSerializable("article", it)
            }

            findNavController().navigate(
                R.id.action_breakingNewsFragment_to_newsDetails,
                bundle
            )
        }

        val swipe = binding.swipeToRefreshLayout
        swipe.setOnRefreshListener {
            refresh()
            swipe.isRefreshing = false
        }


        refresh()



        return binding.root
    }

    fun showProgressBar() {
        binding.shimmerLayout.visibility = View.VISIBLE
        binding.shimmerLayout.startShimmerAnimation()
    }

    fun hideProgressBar() {
        binding.shimmerLayout.visibility = View.GONE
        binding.shimmerLayout.stopShimmerAnimation()
    }

    fun setupRV() {
        MyAdapter = RecyclerViewAdapter(this@MajorNewsFragment.requireContext())
        binding.breakingNewsRv.apply {
            adapter = MyAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }


    fun refresh() {
        viewModel.myBreakingNews.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Loading -> {
                    showProgressBar()
                }

                is Resource.Error -> {
                    hideProgressBar()
                    response.Message?.let { message ->
                        Snackbar.make(
                            binding.root,
                            "An Error occurred:$message ",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }

                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let {
                        MyAdapter.differ.submitList(it.results.toList())
                    }
                }

            }
        })
    }


}