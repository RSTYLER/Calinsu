package ru.example.canlisu

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import ru.example.canlisu.databinding.ActivityMainBinding
// Если делаешь DataStore "запомнить меня":
// import ru.example.canlisu.prefs.AuthPrefs

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_content_main)
            ?.findNavController()
            ?: error("NavHostFragment not found")

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
