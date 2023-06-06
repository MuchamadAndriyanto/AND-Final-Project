package com.finalproject.tiketku

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.finalproject.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var navController : NavController
    private var isLoggedIn = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.mainContainer) as NavHostFragment
        navController = navHostFragment.navController
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        setupWithNavController(bottomNavigationView, navController)

//        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
//            when (item.itemId) {
//                R.id.homeFragment -> {
//                    // Cek apakah pengguna sudah berada di halaman Home saat ini
//                    if (navController.currentDestination?.id != R.id.homeFragment) {
//                        navController.navigate(R.id.homeFragment)
//                    }
//                    true
//                }
//                R.id.historyFragment -> {
//                    // Cek apakah pengguna sudah berada di halaman History saat ini
//                    if (navController.currentDestination?.id != R.id.historyFragment) {
//                        navController.navigate(R.id.historyFragment)
//                    }
//                    true
//                }
//                R.id.historyFragment,
//                R.id.notificationsFragment,
//                R.id.profileFragment -> {
//                    if (isLoggedIn) {
//                        navController.navigate(item.itemId)
//                        true
//                    } else {
//                        // Alihkan pengguna ke halaman login
//                        navController.navigate(R.id.loginFragment)
//                        Toast.makeText(this, "Maaf, Anda harus login terlebih dahulu", Toast.LENGTH_SHORT).show()
//                        true
//                    }
//                }
//                else -> false
//            }
//        }
    }
}