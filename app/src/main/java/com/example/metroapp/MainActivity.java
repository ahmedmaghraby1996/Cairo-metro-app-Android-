package com.example.metroapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MainActivity extends AppCompatActivity implements LocationListener {
    Spinner from, to;
    List<String> line = Arrays.asList("Please Select", "helwan", "ain helwan", "helwan university", "wadi hof", "hadayeq helwan", "el-maasara", "tora el-asmant", "kozzika", "tora el-balad", "thakanat el-maadi", "maadi", "hadayeq el-maadi", "dar el-salam", "el-zahraa", "mar girgis", "el-malek el-saleh", "alSayyeda zeinab", "saad zaghloul", "sadat", "gamal abdal", "urabi", "al shohadaa", "ghamra", "el-demerdash", "manshiet el-sadr", "kobri el-qobba", "saray", "el-qobba", "hadayeq el-zaitoun", "helmeyet el-zaitoun", "el-matareyya", "ain shams", "ezbet el-nakhl", "el-marg", "new el-marg", "el mounib", "sakiat mekki", "omm el misryeen", "giza", "faisal", "cairo university", "bohooth", "dokki", "gezira", "sadat", "naguib", "ataba", "al shohadaa", "massara", "road el-farag", "sainte teresa", "khalafawy", "mezallat", "koliet el-zeraa", "shobra el kheima", "Airport", "Omar Ibn Al Khattab", "Ain Shams 2", "Ain Shams 1", "El Arab", "Alf Maskan", "Heliopolis Square", "Haroun", "Al Ahram", "Koleyet El Banat", "Cairo Stadium", "Cairo Fairground", "Abbassiya", "Fair Zone", "Abdou Pasha", "El Geish", "Bab El Shaaria", "Ataba", "Nasser", "Maspero", "Zamalek", "Kit Kat", "Sudan St", "Imbaba", "El Moneera", "El Bohy", "El Tawfikeya", "Wdi El Nil", "Mustafa Mahmoud", "Shehab");
    String address = "";
    PendingIntent pe;
    Button calculate;
    Location loc1;
    LocationManager manager;
    double longitude;
    double latitude;
    SharedPreferences pref;
    int indexfrom;
    int indexto;
    List<Trip> trip;
    Location loc2;
    Location location1;
    String s;
    List<com.example.metroapp.Location> locs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calculate = findViewById(R.id.Calculate);
        from = findViewById(R.id.from);
        to = findViewById(R.id.to);


        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, line);
        to.setAdapter(adapter);

        from.setAdapter(adapter);
        pref = getPreferences(MODE_PRIVATE);
        from.setSelection(pref.getInt("from", indexfrom));
        to.setSelection(pref.getInt("to", indexto));
    }

    public void calc(View view) {

        ArrayList temp7 = new ArrayList<String>();
        ArrayList temp6 = new ArrayList<String>();
        ArrayList temp5 = new ArrayList<String>();
        ArrayList temp8 = new ArrayList<String>();
        int direction1 = 0;
        int direction2 = 0;
        int x = 0;
        temp5.clear();
        temp6.clear();
        temp7.clear();
        temp8.clear();


        indexfrom = line.indexOf(from.getSelectedItem().toString());
        indexto = line.indexOf(to.getSelectedItem().toString());

        if (from.getSelectedItem().toString() == to.getSelectedItem().toString() || from.getSelectedItem().toString() == line.get(0).toString() || to.getSelectedItem().toString() == line.get(0).toString()) {

            YoYo.with(Techniques.Shake).duration(500).repeat(2).playOn(calculate);
            return;
        }

        if ((indexfrom <= 54) && (indexfrom > 35) && (indexto == 19))
            indexto = 44;
        if ((indexto <= 55) && (indexto > 35) && (indexfrom == 19))
            indexfrom = 45;

        if ((indexfrom <= 54) && (indexfrom > 35) && (indexto == 22))
            indexto = 48;
        if ((indexto <= 55) && (indexto > 35) && (indexfrom == 22))
            indexfrom = 48;

        if ((indexfrom > 55) && (indexto == 47))
            indexto = 73;
        if ((indexto > 55) && (indexfrom == 47))
            indexfrom = 73;

        if ((indexfrom <= 35 && indexto <= 35) || ((indexfrom <= 55 && indexto <= 55) && (indexfrom > 35 && indexto > 35)) || (indexfrom > 55 && indexto > 55)) {


            if (indexfrom < indexto) {
                if (indexfrom <= 35)
                    direction1 = 2;
                if (indexfrom <= 55 && indexfrom > 35)
                    direction1 = 4;
                if (indexfrom <= 72 && indexfrom > 55)
                    direction1 = 6;

                for (int i = indexfrom; i <= indexto; i++) {
                    temp5.add(line.get(i));
                }
            }
            if (indexfrom > indexto) {
                if (indexfrom <= 35)
                    direction1 = 1;
                if (indexfrom <= 55 && indexfrom > 35)
                    direction1 = 3;
                if (indexfrom <= 72 && indexfrom > 55)
                    direction1 = 5;
                for (int i = indexfrom; i >= indexto; i--) {
                    temp5.add(line.get(i));


                }
            }

//
//        return;
        } else {

            if (indexto < 23) {
                direction2 = 1;
                for (int i = 21; i >= indexto; i--) {
                    temp7.add(line.get(i));
                }

                for (int i = 18; i >= indexto; i--) {
                    temp8.add(line.get(i));
                }
                if (indexto >= 19)

                    for (int i = 20; i < indexto + 1; i++) {
                        temp8.add(line.get(i));


                    }
            }

            if (indexfrom < 23) {
                direction1 = 2;
                for (int i = indexfrom; i < 23; i++) {
                    temp5.add(line.get(i));
                }

                for (int i = indexfrom; i < 20; i++) {
                    temp6.add(line.get(i));
                }
                if (indexfrom >= 20) {

                    for (int i = indexfrom; i > 18; i--)
                        temp6.add(line.get(i));

                }
                if (indexto > 55) {
                    temp5.add("ataba");
                    temp6.add("naguib");
                    temp6.add("ataba");
                }
            }


            if (indexto >= 23 && indexto <= 35) {
                direction2 = 2;

                for (int i = 23; i < indexto + 1; i++) {
                    temp7.add(line.get(i));
                }

                for (int i = 20; i < indexto + 1; i++) {
                    temp8.add(line.get(i));
                }
            }


            if ((indexfrom >= 23) && (indexfrom <= 35)) {
                direction1 = 1;
                for (int i = indexfrom; i > 21; i--) {
                    temp5.add(line.get(i));
                }

                for (int i = indexfrom; i > 18; i--) {
                    temp6.add(line.get(i));
                }
                if (indexto > 55) {
                    temp5.add("ataba");
                    temp6.add("naguib");
                    temp6.add("ataba");
                }
            }


            if ((indexfrom > 35) && (indexfrom < 48)) {
                direction1 = 4;
                if (indexto > 55)
                    x = 48;
                else
                    x = 49;
                for (int i = indexfrom; i < x; i++) {
                    temp5.add(line.get(i));
                }
                if (x == 49) {
                    for (int i = indexfrom; i < x - 3; i++) {
                        temp6.add(line.get(i));
                    }
                    if (indexfrom >= 46)
                        for (int i = indexfrom; i > x - 5; i--) {
                            temp6.add(line.get(i));

                        }
                }
            }
            if (indexfrom >= 48 && indexfrom <= 55) {
                direction1 = 3;
                if (indexto >= 55)
                    x = 46;
                else
                    x = 47;
                for (int i = indexfrom; i > x; i--) {
                    temp5.add(line.get(i));
                }
                if (x == 47)
                    for (int i = indexfrom; i > x - 3; i--) {
                        temp6.add(line.get(i));
                    }
            }

            if ((indexto < 48 && indexto > 35)) {
                direction2 = 3;
                if (indexfrom >= 55)
                    x = 46;
                else
                    x = 47;

                for (int i = x; i > indexto - 1; i--) {
                    temp7.add(line.get(i));
                }
                if (x == 47) {
                    for (int i = x - 3; i > indexto - 1; i--) {
                        temp8.add(line.get(i));
                    }
                    if (indexto >= 46)

                        for (int i = 46; i < indexto + 1; i++) {
                            temp8.add(line.get(i));
                        }

                }
            }

            if (indexto >= 48 && indexto <= 55) {
                direction2 = 4;
                if (indexfrom > 55)
                    x = 48;
                else
                    x = 49;

                for (int i = x; i < indexto + 1; i++) {
                    temp7.add(line.get(i));
                }
                if (x == 49) {
                    for (int i = x - 3; i < indexto + 1; i++) {
                        temp8.add(line.get(i));
                    }
                    if (indexto < 46)
                        for (int i = indexfrom; i > x - 3; i--) {
                            temp8.add(line.get(i));
                        }
                }
            }

            if (indexfrom > 55 && indexfrom < 73) {
                direction1 = 6;
                for (int i = indexfrom; i < 74; i++) {
                    temp5.add(line.get(i));
                    if (indexto < 35) {

                        temp6.add(line.get(i));
                    }
                }
                if (indexto < 35) {
                    temp5.add("al shohadaa");
                    temp6.add("naguib");
                    temp6.add("sadat");
                }
            }
            if (indexfrom >= 73) {
                direction1 = 5;
                for (int i = indexfrom; i > 72; i--) {
                    temp5.add(line.get(i));
                    if (indexto < 35) {
                        temp6.add(line.get(i));
                    }
                }
                if (indexto < 35) {
                    temp5.add("al shohadaa");
                    temp6.add("naguib");
                    temp6.add("sadat");
                }
            }
            if (indexto > 55 && indexto < 73) {
                direction2 = 5;
                for (int i = 72; i > indexto - 1; i--) {
                    temp7.add(line.get(i));
                    if (indexfrom < 35)
                        temp8.add(line.get(i));
                }
            }
            if (indexto >= 73) {
                direction2 = 6;
                for (int i = 74; i <= indexto; i++) {
                    temp7.add(line.get(i));

                    if (indexfrom < 35)
                        temp8.add(line.get(i));
                }
            }
        }
        Intent in = new Intent(this, Info.class);
        in.putExtra("temp5", temp5);
        in.putExtra("temp7", temp7);
        in.putExtra("temp6", temp6);
        in.putExtra("temp8", temp8);
        in.putExtra("direction1", direction1);
        in.putExtra("direction2", direction2);
        in.putExtra("fromto", from.getSelectedItem().toString() + " to " + to.getSelectedItem().toString());
        startActivity(in);


    }

    public void map(View view) {

        if (view.getId() == R.id.fromloc) {
            address = from.getSelectedItem().toString() + "+metro+station+egypt";
            if (from.getSelectedItem().toString() == line.get(0).toString()) {
                Toast.makeText(this, "Choose a station first", Toast.LENGTH_SHORT).show();
                return;
            }
        } else if (view.getId() == R.id.toloc) {
            address = to.getSelectedItem().toString() + "+metro+station+egypt";
            if (to.getSelectedItem().toString() == line.get(0).toString()) {
                Toast.makeText(this, "Choose a station first", Toast.LENGTH_SHORT).show();
                return;
            }
        }
//        getLocation(address + "+metro+station+egypt");

        Intent in = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=" + address + "z=16"));
        startActivity(in);
    }


    public void remind(View view) {
        Geocoder geocoder = new Geocoder(this);
        try {
            List<Address> addressList = geocoder.getFromLocationName(to.getSelectedItem().toString(), 1);
            if (addressList != null && !addressList.isEmpty()) {
                latitude = addressList.get(0).getLatitude();
                longitude = addressList.get(0).getLongitude();

                Intent in = new Intent(this, MainActivity2.class);
                pe = PendingIntent.getActivity(this, 0, in, 0);

                manager = (LocationManager) getSystemService(LOCATION_SERVICE);
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    String[] perm = {Manifest.permission.ACCESS_FINE_LOCATION};
                    ActivityCompat.requestPermissions(this, perm, 1);
                } else {
                    manager.addProximityAlert(latitude, longitude, 50, -1, pe);
                }


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 2) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                try {
                    manager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, this, null);
                } catch (SecurityException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(this, "feature not supported", Toast.LENGTH_SHORT).show();
            }
        }


        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                try {
//                    manager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, this, null);
                    manager.addProximityAlert(latitude, longitude, 50, -1, pe);
                } catch (SecurityException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(this, "feature not supported", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void near(View view) {
        manager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            String[] perm = {Manifest.permission.ACCESS_FINE_LOCATION};
            ActivityCompat.requestPermissions(this, perm, 2);
        } else {
            manager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, this, null);

        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

        s = getLocation(location);
        Intent in = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=" + s + "metro station &z=16"));
        startActivity(in);

    }

    @Override
    public void onBackPressed() {
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("from", indexfrom);
        editor.putInt("to", indexto);
        editor.apply();
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void showHistory(MenuItem item) {
        Intent in = new Intent(this, MainActivity2.class);
        startActivity(in);
    }

    public void clear(MenuItem item) {
        trip = TripDatabase.getInstance(this).TripDAO().selectall();
        for (Trip trip1 : trip) {
            int delete = TripDatabase.getInstance(this).TripDAO().delete(trip1);
        }

    }


    private String getLocation(Location location) {


        float min = 100000000;
        double lat = 0;
        double longi = 0;
        float distance = 0;
        loc1 = new Location("");
        loc2 = new Location("");
//        Geocoder geocoder = new Geocoder(this);
//        try {
        locs = TripDatabase.getInstance(this).LocationDAO().selectalllocation();
        Log.d("xxx", "getLocation: " + locs.size());

        for (int i = 0; i < line.size(); i++) {
//            List<Address> addressList = geocoder.getFromLocationName(line.get(i).toString() + "metro egypt", 1);
//            if (addressList != null && !addressList.isEmpty()) {
//                latitude = addressList.get(0).getLatitude();
//                longitude = addressList.get(0).getLongitude();
//                loc1.setLatitude(latitude);
//                loc1.setLongitude(longitude);
//                com.example.metroapp.Location loc = new com.example.metroapp.Location();
//                loc.name=line.get(i).toString();
//                loc.latitude=String.valueOf(latitude);
//                loc.longtiude=String.valueOf(longitude);
//                Long insert=TripDatabase.getInstance(this).LocationDAO().insert(loc);
//                if(insert>0)
//                    Log.d("xxx", "getLocation: "+loc.name);
//                if(insert==0)
//                    Toast.makeText(this, "location not saved", Toast.LENGTH_SHORT).show();


            loc1.setLatitude((Double.parseDouble(locs.get(i).latitude)));
            loc1.setLongitude(Double.parseDouble(locs.get(i).longtiude));
            distance = location.distanceTo(loc1);
            if (distance < min) {
                min = distance;
                lat = loc1.getLatitude();
                longi = loc1.getLongitude();
                Log.d("xxx", "getLocation: " + locs.get(i).name);
                loc2.setLatitude(lat);
                loc2.setLongitude(longi);

                s = locs.get(i).name.toString();


            }
        }


//        return s;
//    } catch (IOException e) {
//            e.printStackTrace();
//        }
        return s;


    }


}



