package com.finalproject.tiketku

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.mainContainer) as NavHostFragment
        navController = navHostFragment.navController
        bottomNavigationView = findViewById(R.id.bottomNavigation)
        setupWithNavController(bottomNavigationView, navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment, R.id.historyFragment, R.id.notificationsFragment, R.id.profileFragment -> {
                    bottomNavigationView.visibility = View.VISIBLE
                    if (!isLoggedIn() && destination.id != R.id.homeFragment) {
                        navController.navigate(R.id.akunNonLoginFragment)
                    }
                }
                else -> {
                    bottomNavigationView.visibility = View.GONE
                }
            }
        }

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            if (!isLoggedIn() && (menuItem.itemId == R.id.historyFragment || menuItem.itemId == R.id.notificationsFragment || menuItem.itemId == R.id.profileFragment)) {
                navController.navigate(R.id.akunNonLoginFragment)
                return@setOnNavigationItemSelectedListener false
            } else {
                return@setOnNavigationItemSelectedListener NavigationUI.onNavDestinationSelected(menuItem, navController)
            }
        }
    }

    private fun isLoggedIn(): Boolean {
        return false
    }
}