package com.munsellapp.munsellcolorrecognitionapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Submit extends AppCompatActivity implements View.OnClickListener {
Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);
        save=(Button) findViewById(R.id.saveButton);
        save.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }
}
