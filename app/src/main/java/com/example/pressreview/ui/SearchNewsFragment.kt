package com.example.pressreview.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pressreview.R
import com.example.pressreview.adapter.RecyclerViewAdapter
import com.example.pressreview.constants.Resource
import com.example.pressreview.databinding.SearchnewFragmentBinding
import com.example.pressreview.model.MyViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchNewsFragment : Fragment(R.layout.searchnew_fragment) {
lateinit var binding: SearchnewFragmentBinding
lateinit var newsAdapter: RecyclerViewAdapter
 val viewModel:MyViewModel by viewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    binding = SearchnewFragmentBinding.inflate(layoutInflater)
        setupRV()

        newsAdapter.setOnItemClickListener {

            val bundle = Bundle().apply {
                putSerializable("article",it)
            }
            findNavController().navigate(
                R.id.action_searchNewsFragment_to_newsDetails,
                bundle
            )

        }



      var job: Job? = null
      binding.tvSearch.addTextChangedListener {editable ->

          job?.cancel()
          job = MainScope().launch {
          delay(500L)

              editable?.let {
              if (it.toString().isNotBlank()){
                  viewModel.getQuery(editable.toString())
                  hideLottie()
                  showProgress()
                  showRv()
              }else{
                  hideRv()
                  hideProgress()
                  showLottie()
                  binding.searchRv.clearOnChildAttachStateChangeListeners() }
              }
          }
      }


        viewModel.myQuery.observe(viewLifecycleOwner, Observer { response ->
        when(response){
            is Resource.Loading -> {
                hideRv()
               showProgress()
                hideLottie()
            }

            is Resource.Error ->
            {
                hideRv()
               hideProgress()
                showLottie()
                response.Message?.let { message ->
                    Snackbar.make(binding.root,"An Error occurred:$message ", Snackbar.LENGTH_SHORT).show()
                }
                Log.d("MainActivity", "An Error occurred: {$response.Message}")
            }

            is Resource.Success ->
            {
                showRv()
               hideProgress()
                hideLottie()
                response.data?.let {
                    newsAdapter.differ.submitList(it.articles?.toList())
                }
            }

        }

    })




     return   binding.root }




    fun setupRV(){
        newsAdapter = RecyclerViewAdapter(this.requireContext())
        binding.searchRv.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    fun showProgress(){
        binding.shimmerLayout.visibility = View.VISIBLE
        binding.shimmerLayout.startShimmerAnimation()
    }
    fun hideProgress(){
        binding.shimmerLayout.visibility = View.GONE
        binding.shimmerLayout.stopShimmerAnimation()
    }

    private fun showLottie(){
        binding.lottieAnimationView.visibility = View.VISIBLE
    }

    private fun hideLottie(){
        binding.lottieAnimationView.visibility = View.GONE
    }

    private fun hideRv(){
        binding.searchRv.visibility = View.GONE
    }

    private fun showRv(){
        binding.searchRv.visibility = View.VISIBLE
    }



}