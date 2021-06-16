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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pressreview.R
import com.example.pressreview.adapter.RecyclerViewAdapter
import com.example.pressreview.constants.Resource
import com.example.pressreview.databinding.SearchnewFragmentBinding
import com.example.pressreview.model.MyViewModel
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

      //  var job: Job? = null
      //  binding.tvSearch.addTextChangedListener {editable ->
//
      //      job?.cancel()
      //      job = MainScope().launch {
      //      delay(500L)
//
      //          editable?.let {
      //          if (it.toString().isNotBlank()){
      //              viewModel.getQuery(editable.toString())
      //              hideLottie()
      //              showProgress()
      //              showRv()
      //          }else{
      //              hideRv()
      //              hideProgress()
      //              showLottie()
      //              binding.savedRv.clearOnChildAttachStateChangeListeners() }
      //          }
      //      }
      //  }

        viewModel.getQuery("Ronaldo")
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
                Log.d("MainActivity", "An Error occurred: {$response.Message}")
            }

            is Resource.Success ->
            {
                showRv()
               hideProgress()
                hideLottie()
                response.data?.let {
                    newsAdapter.differ.submitList(it.articles)
                }
            }

        }

    })




     return   binding.root }




    fun setupRV(){
        newsAdapter = RecyclerViewAdapter(this.requireContext())
        binding.savedRv.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

   private fun showProgress(){
       binding.progressBar.visibility = View.VISIBLE
   }
    private fun hideProgress(){
        binding.progressBar.visibility = View.INVISIBLE
    }

    private fun showLottie(){
        binding.lottieAnimationView.visibility = View.VISIBLE
    }

    private fun hideLottie(){
        binding.lottieAnimationView.visibility = View.INVISIBLE
    }

    private fun hideRv(){
        binding.savedRv.visibility = View.INVISIBLE
    }

    private fun showRv(){
        binding.savedRv.visibility = View.VISIBLE
    }



}