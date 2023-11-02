package edu.emich.tilere;

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

import edu.emich.tilere.colorpicker.ColorPicker;
import edu.emich.tilere.utils.ImageUtils;

public class MainActivity extends AppCompatActivity {

    private Intent mPickImageIntent;
    private ActivityResultLauncher<Intent> mPickImageLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupIntents();
    }

    private void setupIntents() {
        mPickImageIntent = new Intent(Intent.ACTION_GET_CONTENT);
        mPickImageIntent.setType("image/*");

        mPickImageLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            // Handle activity result
            var data = result.getData();
            if (data != null) {
                Uri imageUri = data.getData();
                ColorPicker colorPicker = (ColorPicker)findViewById(R.id.colorPicker);

                // handle event colorpicker is null
                // this should only ever happen if developer is bad
                if (colorPicker == null) {
                    new AlertDialog.Builder(this.getApplicationContext())
                            .setTitle("Failed to retrieve color picker instance")
                            .setMessage("ColorPicker instance was null. Please contact the application developers.")
                            .show();

                    return;
                }

                try {
                    colorPicker.setImage(ImageUtils.getBitmapFromUri(this.getApplicationContext(), imageUri));
                } catch (IOException ex) {
                    String err = "Failed to import image from gallery with exception: " + ex.getMessage();
                    Log.i(this.getClass().getName(), err);

                    new AlertDialog.Builder(this.getApplicationContext())
                            .setTitle("An error has occurred")
                            .setMessage(err)
                            .show();
                }
            }
        });
    }

    public void startImportImageIntent(View sender) {
        mPickImageLauncher.launch(mPickImageIntent);
    }
}