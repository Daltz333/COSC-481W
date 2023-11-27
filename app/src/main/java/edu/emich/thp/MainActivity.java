package edu.emich.thp;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.IOException;

import edu.emich.thp.api.DbClient;
import edu.emich.thp.colorpicker.ColorPicker;
import edu.emich.thp.utils.ImageUtils;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Refresh grout
        DbClient.getInstance().refreshGroutAsync();
    }

}