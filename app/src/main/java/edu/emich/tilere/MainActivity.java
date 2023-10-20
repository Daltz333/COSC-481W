package edu.emich.tilere;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

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
            }
        });
    }

    public void startImportImageIntent(View sender) {
        mPickImageLauncher.launch(mPickImageIntent);
    }
}