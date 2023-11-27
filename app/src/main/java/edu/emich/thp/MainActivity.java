package edu.emich.thp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.widget.Toolbar;

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
}