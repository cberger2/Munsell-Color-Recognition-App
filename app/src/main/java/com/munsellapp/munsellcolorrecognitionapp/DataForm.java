package com.munsellapp.munsellcolorrecognitionapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class DataForm extends AppCompatActivity implements View.OnClickListener {
    TextView dataList, savedData;
    String idNumber, munsellChip, notes, dataString, dataListString, dataListStringBundle;
    ImageButton home, email;
    SharedPreferences savedValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_form);
        dataList = (TextView) findViewById(R.id.dataList);
        Bundle dataBundle= getIntent().getExtras();
        dataListStringBundle= dataBundle.getString("dataList");
        dataList.setText(dataListStringBundle);

        Bundle bundle = getIntent().getExtras();
        idNumber = bundle.getString("idNumber");
        munsellChip = bundle.getString("munsellChip");
        notes = bundle.getString("notes");
        dataList.append(idNumber + " , " + munsellChip + " , " + notes + "\n");
        home = (ImageButton) findViewById(R.id.dfHome);
        home.setOnClickListener(this);

        savedValues = getSharedPreferences("SavedValues", MODE_APPEND);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dfHome:
                dataList=(TextView) findViewById(R.id.dataList);
                dataListString=dataList.getText().toString();
                Intent homeIntent = new Intent(this, MainActivity.class);
                Bundle dataListBundle = new Bundle();
                dataListBundle.putString("dataList", dataListString);
                homeIntent.putExtras(dataListBundle);
                startActivity(homeIntent);

        }
    }
    @Override
    public void onPause() {
        // save the instance variables
        dataListString=dataList.getText().toString();
        SharedPreferences.Editor editor = savedValues.edit();
        editor.putString("dataList", dataListString);
        editor.commit();

        super.onPause();
    }
@Override
    public void onResume(){
    dataString= savedValues.getString("dataList", dataListString);
    dataList=(TextView) findViewById(R.id.dataList);
    dataList.setText(dataString);

    super.onResume();
}

}
