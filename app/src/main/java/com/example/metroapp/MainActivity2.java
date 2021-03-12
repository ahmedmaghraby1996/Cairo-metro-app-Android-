package com.example.metroapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity implements AdapterView.OnItemClickListener {
ListView lv;
List<String> trips;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        lv=findViewById(R.id.lv);
        trips=TripDatabase.getInstance(this).TripDAO().selectTrips();
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,trips);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    String data=trips.get(position);
        Intent in = new Intent(this,MainActivity3.class);
        in.putExtra("data",data);
        startActivity(in);
    }
}