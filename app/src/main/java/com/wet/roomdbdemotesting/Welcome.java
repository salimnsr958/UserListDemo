package com.wet.roomdbdemotesting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Welcome extends AppCompatActivity {

    TextView text_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        text_name = findViewById(R.id.text_name);

        if (getIntent()!=null)
        {
            String name = getIntent().getStringExtra("name");
            text_name.setText(name);
        }
    }
}