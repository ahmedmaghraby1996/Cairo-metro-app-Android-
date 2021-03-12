package com.example.metroapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity3 extends AppCompatActivity {
TextView detail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        detail=findViewById(R.id.details);
        String data = getIntent().getStringExtra("data");
        String details=TripDatabase.getInstance(this).TripDAO().selectDetails(data);
        String note=TripDatabase.getInstance(this).TripDAO().selectNotes(data);
        detail.setText(details);
        if(!(note==null))
        detail.append("\n Note: "+note);
        else
            detail.append("\n no notes");
    }
}