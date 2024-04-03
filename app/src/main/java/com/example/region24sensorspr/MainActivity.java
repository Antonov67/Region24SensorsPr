package com.example.region24sensorspr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView sensorsList;
    TextView sensorsCount;
    List<Sensor> deviceSensors;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorsList = findViewById(R.id.sensors_list);
        sensorsCount = findViewById(R.id.sensors_count);

        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        deviceSensors = sensorManager.getSensorList(Sensor.TYPE_ALL);

        List<String> sensors = new ArrayList<>();
        for (Sensor s : deviceSensors) {
            sensors.add(s.getName() + " " + s.getType());
        }

        sensorsCount.setText("Количество сенсоров " + deviceSensors.size());


        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, sensors);
        sensorsList.setAdapter(adapter);

        sensorsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, SensorActivity.class);
                intent.putExtra("sensorType", deviceSensors.get(i).getType() + "");
                startActivity(intent);
            }
        });

    }
}