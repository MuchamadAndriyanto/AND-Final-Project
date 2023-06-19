package com.finalproject.tiketku

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPref = getSharedPreferences("dataUser", Context.MODE_PRIVATE)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.mainContainer) as NavHostFragment
        navController = navHostFragment.navController
        bottomNavigationView = findViewById(R.id.bottomNavigation)
        setupWithNavController(bottomNavigationView, navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment, R.id.historyFragment, R.id.notificationsFragment, R.id.profileFragment -> {
                    bottomNavigationView.visibility = View.VISIBLE
                    if (!isLoggedIn()) {
                        // Simpan ID halaman tujuan yang diminta pengguna ke shared preferences
                        val editor = sharedPref.edit()
                        editor.putInt("requestedDestinationId", destination.id)
                        editor.apply()

                        if (destination.id != R.id.homeFragment) {
                            navController.navigate(R.id.akunNonLoginFragment)
                        }
                    }
                }
                else -> {
                    bottomNavigationView.visibility = View.GONE
                }
            }
        }

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            if (!isLoggedIn()) {
                // Simpan ID menu yang diklik pengguna ke shared preferences
                val editor = sharedPref.edit()
                editor.putInt("clickedMenuItemId", menuItem.itemId)
                editor.apply()

                navController.navigate(R.id.akunNonLoginFragment)
                return@setOnNavigationItemSelectedListener false
            } else {
                // Simpan ID halaman terakhir yang diklik ke shared preferences
                val editor = sharedPref.edit()
                editor.putInt("clickedMenuItemId", menuItem.itemId)
                editor.apply()

                val handled = NavigationUI.onNavDestinationSelected(menuItem, navController)
                if (handled) {
                    // Ambil email pengguna yang sedang login dari shared preferences
                    val loggedInEmail = sharedPref.getString("email", "")

                    Log.d("MainActivity", "Pengguna sudah login dengan email: $loggedInEmail")
                }
                return@setOnNavigationItemSelectedListener handled
            }
        }

        val isLoggedIn = isLoggedIn()
        val clickedMenuItemId = sharedPref.getInt("clickedMenuItemId", R.id.homeFragment)

        if (isLoggedIn) {
            bottomNavigationView.menu.findItem(clickedMenuItemId).isChecked = true
        } else {
            val requestedDestinationId = sharedPref.getInt("requestedDestinationId", R.id.homeFragment)
            if (requestedDestinationId != R.id.homeFragment) {
                navController.navigate(R.id.akunNonLoginFragment)
            }
        }
    }

    override fun onStop() {
        super.onStop()

        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        // Mengatur ulang data ke nilai awal, yaitu "Jakarta (JKT)"
        editor.putString("key", "Jakarta (JKT)")
        editor.apply()
    }

    private fun isLoggedIn(): Boolean {
        return sharedPref.getBoolean("isLoggedIn", false)
    }
}

