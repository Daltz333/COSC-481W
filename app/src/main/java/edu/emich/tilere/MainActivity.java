package edu.emich.tilere;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void startImportImageIntent(View sender) {
        Intent imageImportIntent = new Intent();
        imageImportIntent.setType("image/*");
        imageImportIntent.setAction(Intent.ACTION_GET_CONTENT);

        startActivity(imageImportIntent);
    }
}