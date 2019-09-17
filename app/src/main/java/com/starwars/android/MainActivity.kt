package com.starwars.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.starwars.android.databinding.ActivityMainBinding
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun supportFragmentInjector() = dispatchingAndroidInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this,
                R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val host: NavHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        val navController = host.navController

        appBarConfiguration = AppBarConfiguration.Builder(R.id.homeFragment, R.id.battleHistoryFragment).build()

        setupActionBar(navController, appBarConfiguration)

        setupBottomNavMenu(navController)

    }

    private fun setupBottomNavMenu(navController: NavController) {

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        bottomNav?.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.unitDetailFragment ->
                    bottomNav.hide()
                R.id.fragmentGame ->
                    bottomNav.hide()
                else -> {
                    bottomNav.show()
                }
            }
        }
    }

    private fun setupActionBar(
            navController: NavController,
            appBarConfig: AppBarConfiguration
    ) {
        setupActionBarWithNavController(navController, appBarConfig)
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragment).navigateUp(appBarConfiguration)
    }
}