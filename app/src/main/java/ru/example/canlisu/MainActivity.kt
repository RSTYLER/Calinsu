package ru.example.canlisu

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import ru.example.canlisu.data.UserManager
import ru.example.canlisu.databinding.ActivityMainBinding
import ru.example.canlisu.prefs.AuthPrefs

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
        val navController = navHostFragment.navController
        val navGraph = navController.navInflater.inflate(R.navigation.mobile_navigation)
        AuthPrefs.getUser(this)?.let { user ->
            UserManager.currentUser = user
            navGraph.setStartDestination(R.id.nav_home)
        }
        navController.graph = navGraph

        binding.bottomNav.setupWithNavController(navController)

        binding.bottomNav.setOnItemSelectedListener { item ->
            if (navController.currentDestination?.id != item.itemId) {
                navController.navigate(item.itemId)
            }
            true
        }

        val rootDestinations = setOf(R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            val hideBottom = destination.id == R.id.loginFragment || destination.id == R.id.registrationFragment
            binding.bottomNav.visibility = if (hideBottom) View.GONE else View.VISIBLE

            if (destination.id in rootDestinations) {
                binding.bottomNav.selectedItemId = destination.id
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        // Без AppBarConfiguration:
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
