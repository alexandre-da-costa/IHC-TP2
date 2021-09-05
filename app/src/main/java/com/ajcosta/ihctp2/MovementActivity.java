package com.ajcosta.ihctp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MovementActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movement);
    }

    public void voltar(View view) {
        Intent i = new Intent(this, SensorActivity.class);
        startActivity(i);
    }

}