package com.akumakeito.ticketsales.screens

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.akumakeito.ticketsales.R
import com.akumakeito.ticketsales.TicketsApp
import com.akumakeito.ticketsales.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var navController: NavController

    private val component by lazy {
        (application as TicketsApp).component
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host) as NavHostFragment

        navController = navHostFragment.navController


        val bottomNavView = binding.bottomNavigation
        NavigationUI.setupWithNavController(bottomNavView, navController)
    }
}