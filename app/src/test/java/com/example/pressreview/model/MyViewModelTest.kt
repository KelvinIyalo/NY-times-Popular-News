package com.example.pressreview.model

import com.example.pressreview.repository.DefaultRepository
import com.example.pressreview.repository.FakeRepository
import org.junit.Assert.*
import org.junit.Before

class MyViewModelTest {

    private lateinit var viewModel: MyViewModel


    @Before
    fun setup(){
        viewModel = MyViewModel(FakeRepository())
    }

}