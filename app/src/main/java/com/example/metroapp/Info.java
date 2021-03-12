package com.example.metroapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.github.nisrulz.sensey.Sensey;
import com.github.nisrulz.sensey.ShakeDetector;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Info extends AppCompatActivity implements ShakeDetector.ShakeListener, TextWatcher {
    TextView info;
    int staions = 0;
    String date;
    EditText note;
    Button submit;
    String notes;
    Trip trip;
    String fromto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        info = findViewById(R.id.tvinfo);
        note=findViewById(R.id.note);
        submit=findViewById(R.id.submit);
        StringBuilder builder = new StringBuilder();
        note.addTextChangedListener(this);
        Boolean check = false;

        trip=new Trip();
        Sensey.getInstance().init(this);
        Sensey.getInstance().startShakeDetection(this);
        ArrayList temp7 = (ArrayList<String>) getIntent().getSerializableExtra("temp7");
        ArrayList temp6 = (ArrayList<String>) getIntent().getSerializableExtra("temp6");
        ArrayList temp5 = (ArrayList<String>) getIntent().getSerializableExtra("temp5");
        ArrayList temp8 = (ArrayList<String>) getIntent().getSerializableExtra("temp8");
        int direction1 = getIntent().getIntExtra("direction1", 0);
        int direction2 = getIntent().getIntExtra("direction2", 0);
    fromto= getIntent().getStringExtra("fromto");


        builder.append("Route : ");

        for (Object o : temp5) {
            builder.append(o + "-");
        }
        for (Object o : temp7) {
            builder.append(o + "-");
        }

        if (!temp7.isEmpty()) {
            info.setText(builder);
            info.append(" \n -number of stations : " + ((temp5.size()) + (temp7.size()) - 1));
        }
        if (!temp6.isEmpty()) {

            builder.append(" \n\n or : ");

            for (Object o : temp6) {
                builder.append(o + "-");
            }
            for (Object o : temp8) {
                builder.append(o + "-");
            }


            info.setText(builder);

            if ((temp5.size() + temp7.size()) < (temp6.size() + temp8.size())) {
                info.append("  \n -First route is the shortest \n number of stations are  " + ((temp5.size()) + (temp7.size()) - 1));
            } else {
                check = true;
                info.append(" \n -Second route is the shortest \n number of stations are  " + (temp6.size() + temp8.size() - 1));
            }
        }


//
//}
        else if (temp7.isEmpty()) {
            info.setText(builder);
            info.append(" \n -number of stations : " + (temp5.size() - 1));
        }
        if (direction1 == 2 || direction2 == 2)
            info.append("\n -Direction of line 1 is to El-marg");
        if (direction1 == 1 || direction2 == 1)
            info.append("\n -Direction of line 1 is to Helwan");
        if (direction1 == 4 || direction2 == 4)
            info.append("\n -Direction of line 2 is to Shobra El khemia");
        if (direction1 == 3 || direction2 == 3)
            info.append("\n -Direction of line 2 is to El mounib");
        if (direction1 == 6 || direction2 == 6)
            info.append("\n -Direction of line 3 is to Shehab");
        if (direction1 == 5 || direction2 == 5)
            info.append("\n -Direction of line 3 is to Airport");

        if (check == false)
            staions = temp5.size() + (temp7.size()) - 1;
        else
            staions = temp6.size() + temp8.size() - 1;
        if (staions <= 9)
            info.append("\n-Tickt price is 5 L.E ");
        if (staions > 9 && staions <= 16)
            info.append("\n-Tickt price is 7.5 L.E ");
        if (staions > 16)
            info.append("\n-Tickt price is 10 L.E ");
        info.append("\n-Arrival time : " + (staions * 2)+"mins");
//        Date c = Calendar.getInstance().getTime();
//        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy  'at' HH:mm:ss ", Locale.getDefault());
//        String formattedDate = df.format(c);

//        trip= new Trip();
//        trip.fromto=fromto+" "+formattedDate;
//        trip.details=info.getText().toString();
//        long insert=TripDatabase.getInstance(this).TripDAO().insert(trip);

    }

    @Override
    public void onShakeDetected() {

    }

    @Override
    public void onShakeStopped() {
        Intent in = new Intent(this,MainActivity.class);
        startActivity(in);
    }


    public void submit(View view) {
        submit.setEnabled(false);

        trip.notes=note.getText().toString();

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if(note.getText().toString().equals(""))
    submit.setEnabled(false);
        else
            submit.setEnabled(true);
    }

    @Override
    public void onBackPressed() {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy  'at' HH:mm:ss ", Locale.getDefault());
        String formattedDate = df.format(c);


        trip.fromto=fromto+" "+formattedDate;
        trip.details=info.getText().toString();
        long insert=TripDatabase.getInstance(this).TripDAO().insert(trip);

        super.onBackPressed();
    }
}

