package com.example.diplom.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavGraph
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.diplom.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        bottomNavigationView = findViewById(R.id.bottom_nav_bar)
       /* bottomNavigationView.setOnItemSelectedListener { it ->
            val selectedGraph = navController.graph.findNode(it.itemId) as NavGraph
            selectedGraph.let {
                navController.popBackStack(it.startDestinationId, false)
            }
            //navController.popBackStack()
        }
        bottomNavigationView.setOnItemSelectedListener {
            navController.popBackStack()
        }*/
        NavigationUI.setupWithNavController(bottomNavigationView, navController)

    }
}