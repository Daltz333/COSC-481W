package edu.emich.thp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

import android.os.Bundle;

import edu.emich.thp.api.DbClient;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SplashScreen.installSplashScreen(this);
        setContentView(R.layout.activity_main);

        // Refresh grout
        DbClient.getInstance().refreshGroutAsync();
    }

}