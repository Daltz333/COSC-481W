package edu.emich.thp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
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

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("THP - Grout Recognition");
        }

        // Setup navigation listener
        var navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        if (navHostFragment != null) {
            navHostFragment.getNavController().addOnDestinationChangedListener(this::handleDestinationChanged);
        }

        // Refresh grout
        DbClient.getInstance().refreshGroutAsync();
    }

    public void handleDestinationChanged(NavController controller, NavDestination dest, Bundle args) {
        var actionBar = getSupportActionBar();

        if (actionBar != null) {
            if (dest.getId() != R.id.startPage) {
                actionBar.setDisplayShowHomeEnabled(true);
                actionBar.setDisplayHomeAsUpEnabled(true);
            } else {
                actionBar.setDisplayHomeAsUpEnabled(false);
                actionBar.setDisplayShowHomeEnabled(false);
            }
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        var navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        // Handle navigation items
        if (navHostFragment != null) {
            navHostFragment.getNavController().popBackStack();
        }

        return super.onSupportNavigateUp();
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
                return super.onOptionsItemSelected(item);
            }
        }

        return true;
    }
}