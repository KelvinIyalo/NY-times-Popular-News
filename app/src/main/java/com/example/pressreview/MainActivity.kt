package com.example.pressreview

import android.os.Bundle
import android.view.Menu
import androidx.activity.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.pressreview.model.MyViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    val viewModel: MyViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val  navController = Navigation.findNavController(this,R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.breakingNewsFragment, R.id.newsDetailsFragment))

        setupActionBarWithNavController(navController, appBarConfiguration)

    }





    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.bottom_nav_menu, menu)
        return true
    }
}