package com.example.region24sensorspr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SensorActivity extends AppCompatActivity {

    private SensorManager sensorManager;
    private Sensor sensor;
    private SensorEventListener sensorEventListener;

    ListView sensorValueList;
    TextView sensorName;

    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        sensorValueList = findViewById(R.id.sensor_value_list);
        sensorName = findViewById(R.id.sensor);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        int type = Integer.parseInt(getIntent().getStringExtra("sensorType"));

        if (sensorManager != null){
            sensor = sensorManager.getDefaultSensor(type);
            sensorName.setText("сенсор: " + sensor.getName() + " " + sensor.getType());
        }

        List<String> list = new ArrayList<>();
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        sensorValueList.setAdapter(adapter);

        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                list.clear();
                float[] nums = sensorEvent.values;
                for (int i = 0; i < nums.length; i++) {
                    list.add(String.valueOf(nums[i]));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(sensorEventListener);
    }
}