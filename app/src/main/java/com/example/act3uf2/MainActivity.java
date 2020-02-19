package com.example.act3uf2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button auxiliBtn;
    Button aparcamentBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auxiliBtn = (Button)findViewById(R.id.auxili);
        aparcamentBtn = (Button)findViewById(R.id.aparcament);

        auxiliBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Auxili.class);
                startActivity(intent);
            }
        });

        aparcamentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GoogleMaps.class);
                startActivity(intent);
            }
        });


    }
}
