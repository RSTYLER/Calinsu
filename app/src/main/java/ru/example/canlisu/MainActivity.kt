package ru.example.canlisu

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
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

        // Скрывать нижнюю навигацию на экране логина
        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.bottomNav.isVisible = destination.id != R.id.loginFragment
        }

        // Если используешь флаг «залогинен» в DataStore — раскомментируй блок ниже
        /*
        lifecycleScope.launch {
            AuthPrefs.isLoggedIn(this@MainActivity).collectLatest { loggedIn ->
                if (loggedIn && navController.currentDestination?.id == R.id.loginFragment) {
                    val options = androidx.navigation.NavOptions.Builder()
                        .setPopUpTo(navController.graph.startDestinationId, true)
                        .build()
                    navController.navigate(R.id.fragment_home, null, options)
                }
            }
        }
        */

        binding.bottomNav.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        // Без AppBarConfiguration:
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp()
    }
}
