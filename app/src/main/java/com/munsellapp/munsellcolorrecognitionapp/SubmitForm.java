package com.munsellapp.munsellcolorrecognitionapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class SubmitForm extends AppCompatActivity implements View.OnClickListener,GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    ImageButton save, email;
    EditText idNumber, notes;
    TextView munsell, munsellValueText, updatedText;
    String munsellChip;
    TextView location;
    private GoogleApiClient googleApiClient;
    private static final int PERMISSION_ACCESS_COARSE_LOCATION = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_form);
        save = (ImageButton) findViewById(R.id.sfSaveButton);
        save.setOnClickListener(this);
        Bundle getBundle= getIntent().getExtras();
        munsellChip=getBundle.getString("MunsellChip");



        munsellValueText = (TextView) findViewById(R.id.sfMunsellChip);
        munsellValueText.setText(munsellChip);
        location = (TextView) findViewById(R.id.textView6);
        googleApiClient = new GoogleApiClient.Builder(this, this, this).addApi(LocationServices.API).build();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    PERMISSION_ACCESS_COARSE_LOCATION);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_ACCESS_COARSE_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "All good.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Need your location!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


    @Override
    public void onClick(View v) {
        idNumber=(EditText) findViewById(R.id.sfIdEdit);
        notes=(EditText) findViewById(R.id.sfNotesEdit);
        updatedText=(TextView) findViewById(R.id.sfInfoStorage);
        Intent intent=new Intent(this, DataForm.class);
        Bundle bundle=new Bundle();
        bundle.putString("idNumber", idNumber.getText().toString());
        bundle.putString("munsellChip", munsellValueText.getText().toString());
        bundle.putString("notes", notes.getText().toString());
        bundle.putString("location", location.getText().toString());

            intent.putExtras(bundle);
            startActivity(intent);

        }

protected String cityName;

    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
    }
    @Override
    public void onConnected(Bundle bundle) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            double lat = lastLocation.getLatitude(), lon = lastLocation.getLongitude();
            String units = "imperial";
            System.out.println(lat);
            Geocoder gcd = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = null;
            try {
                addresses = gcd.getFromLocation(lat, lon, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (addresses.size() > 0) {
                System.out.println(addresses.get(0).getLocality());
                cityName = addresses.get(0).getLocality();
            }}
        location.setText(cityName);
    }
    @Override
    public void onConnectionSuspended(int cause) {
        googleApiClient.connect();
    }
    protected String getCityName(){
        return cityName;
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}






