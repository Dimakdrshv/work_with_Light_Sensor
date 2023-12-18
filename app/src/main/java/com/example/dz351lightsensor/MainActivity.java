package com.example.dz351lightsensor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.hardware.SensorEventListener;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

//import com.example.dz351lightsensor.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private SensorManager sensorManager;
    private Sensor sensorLight;
    MediaPlayer mySong;
    private boolean isPlaying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sensor);
        mySong = MediaPlayer.create(MainActivity.this, R.raw.mystic);
        textView = (TextView) findViewById(R.id.textView);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorLight = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        sensorManager.registerListener(listenerLight, sensorLight, SensorManager.SENSOR_DELAY_NORMAL);
    }
    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(listenerLight, sensorLight);
    }
    SensorEventListener listenerLight = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            textView.setText(String.valueOf(sensorEvent.values[0]));
            if (sensorEvent.values[0] < 5 && isPlaying == false) {
                mySong.start();
                isPlaying = true;
            } else if (sensorEvent.values[0] >= 5 && isPlaying == true) {
                mySong.pause();
                isPlaying = false;
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {
        }
    };
}