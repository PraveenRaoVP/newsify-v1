package android.example.newsapp

import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val navController = findNavController(R.id.nav_host_fragment)

        NavigationUI.setupActionBarWithNavController(this, navController)

        // Set up the toolbar with the navigation controller
        navController.addOnDestinationChangedListener { controller, destination, _ ->
            if(destination.id == controller.graph.startDestinationId) {
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
            } else {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        doubleBackToExitPressed = false
        return navController.navigateUp(appBarConfiguration)
    }

    private var doubleBackToExitPressed = false

    override fun onBackPressed() {
        val navController = this.findNavController(R.id.nav_host_fragment)

        if(navController.currentDestination?.id == R.id.newsDetailsFragment) {
            navController.popBackStack()
            return
        }

        if(supportFragmentManager.primaryNavigationFragment?.childFragmentManager?.backStackEntryCount!=0) {
            super.onBackPressed()
            return
        }

        if (doubleBackToExitPressed) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressed = true
        Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show()

        Handler().postDelayed({
            doubleBackToExitPressed = false
        }, 2000)
    }
}