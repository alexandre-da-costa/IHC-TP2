package com.ajcosta.ihctp2;

import static java.lang.Math.abs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SensorActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor light;
    private Sensor mAccelerometer;
    private Sensor gravity;

    TextView lightValue, ax, ay, az, gravidade;
    Button btnGPS;
    float currentX, currentY, currentZ;
    boolean first = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        lightValue = (TextView) findViewById(R.id.labelLuminosidade);
        ax = (TextView) findViewById(R.id.editTextX);
        ay = (TextView) findViewById(R.id.editTextY);
        az = (TextView) findViewById(R.id.editTextZ);
        btnGPS = (Button) findViewById(R.id.buttonGPS);
        gravidade = (TextView) findViewById(R.id.editTextTemp);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        light = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        mAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        gravity = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);



        ActivityCompat.requestPermissions(SensorActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 123);
        btnGPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GPSTracker g = new GPSTracker(getApplicationContext());
                Location l = g.getLocation();
                if (l != null) {
                    double lat = l.getLatitude();
                    double longi = l.getLongitude();
                    Toast.makeText(getApplicationContext(), "Lat: " + lat + "\nLong: " + longi, Toast.LENGTH_LONG).show();
                }
            }
        });




    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor sensor = sensorEvent.sensor;
        if (sensor.getType() == Sensor.TYPE_LIGHT) {
            lightValue.setText("Instensidade luminosa: " + sensorEvent.values[0]);
        }
        if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float sensorX = sensorEvent.values[0];
            float sensorY = sensorEvent.values[1];
            float sensorZ = sensorEvent.values[2];

            if (first){
                currentX = sensorX;
                currentY = sensorY;
                currentZ = sensorZ;
                first = false;
            }

            if (abs(currentZ - sensorZ) > 1.5 || abs(currentY - sensorY) > 1.5 || abs(currentX - sensorX) > 1.5 ){
                Intent i = new Intent(this, MovementActivity.class);
                startActivity(i);
            }

            currentX = sensorX;
            currentY = sensorY;
            currentZ = sensorZ;

            ax.setText("x: " + String.valueOf(sensorX));
            ay.setText("y: " + String.valueOf(sensorY));
            az.setText("z: " + String.valueOf(sensorZ));
        }
        if (sensor.getType() == Sensor.TYPE_GRAVITY) {
            gravidade.setText(String.valueOf(sensorEvent.values[0]));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public void voltar(View view) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (light != null)
            sensorManager.registerListener(SensorActivity.this, light, SensorManager.SENSOR_DELAY_NORMAL);
        else
            lightValue.setText("Sensor de luz não suportado.");

        sensorManager.registerListener(SensorActivity.this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);

        sensorManager.registerListener(SensorActivity.this, gravity, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}