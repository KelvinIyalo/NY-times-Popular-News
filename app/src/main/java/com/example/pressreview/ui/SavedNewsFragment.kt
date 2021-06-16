package com.example.pressreview.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pressreview.R
import com.example.pressreview.adapter.RecyclerViewAdapter
import com.example.pressreview.constants.Resource
import com.example.pressreview.databinding.BreakingnewFragmentBinding
import com.example.pressreview.databinding.SavednewsFragmentBinding
import com.example.pressreview.model.MyViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SavedNewsFragment : Fragment(R.layout.savednews_fragment) {
    lateinit var MyAdapter: RecyclerViewAdapter
    lateinit var binding: SavednewsFragmentBinding
    val viewModel: MyViewModel by viewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = SavednewsFragmentBinding.inflate(layoutInflater)
             setupRV()

        MyAdapter.setOnItemClickListener {

            val bundle = Bundle().apply {
                putSerializable("article",it)
            }
            findNavController().navigate(
                R.id.action_savedNewsFragment_to_newsDetails,
                bundle
            )

        }
        viewModel.getAllFromDB().observe(viewLifecycleOwner, Observer {response ->
            response?.let {
                MyAdapter.differ.submitList(it)
            }
        })

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                val position = viewHolder.adapterPosition
                val article = MyAdapter.differ.currentList[position]
                viewModel.delete(article)
                Snackbar.make(binding.root, "Article Successfully Deleted", Snackbar.LENGTH_LONG).apply {
                    setAction("Undo"){
                        viewModel.Updert(article)
                    }
                    show()
                }
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.savedNewsRv)
        }






        return binding.root
    }

    private fun setupRV() {
        MyAdapter = RecyclerViewAdapter(this@SavedNewsFragment.requireContext())
        binding.savedNewsRv.apply {
            adapter = MyAdapter
            layoutManager = LinearLayoutManager(activity)

        }
    }




}