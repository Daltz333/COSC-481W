package edu.emich.thp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavHostController;
import androidx.navigation.fragment.NavHostFragment;

import edu.emich.thp.api.DbClient;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SplashScreen.installSplashScreen(this);
        setContentView(R.layout.activity_main);

        // Setup toolbar
        Toolbar globalToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(globalToolbar);
        globalToolbar.showOverflowMenu();

        // Refresh grout
        DbClient.getInstance().refreshGroutAsync();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        var navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);

        // Handle navigation items
        if (navHostFragment != null) {
            if (item.getItemId() == R.id.action_about) {
                navHostFragment.getNavController().navigate(R.id.about);
            } else if (item.getItemId() == R.id.action_settings) {
                navHostFragment.getNavController().navigate(R.id.settings);
            } else {
                throw new RuntimeException("Unhandled option in menu!");
            }
        }

        return true;
    }
}