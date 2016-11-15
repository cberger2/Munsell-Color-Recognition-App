package com.munsellapp.munsellcolorrecognitionapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class DataForm extends AppCompatActivity implements View.OnClickListener {
    TextView dataListText, savedData;
    String idNumber, munsellChip, notes, dataString, dataListString, dataListStringBundle;
    ImageButton home, email;
    SharedPreferences savedValues;
    String savedDataString, restoreData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_form);
        dataListText = (TextView) findViewById(R.id.dataList);
        home = (ImageButton) findViewById(R.id.dfHome);
        home.setOnClickListener(this);
        email=(ImageButton) findViewById(R.id.dfEmail);
        email.setOnClickListener(this);

//        if(dataListText.equals("")){
            Bundle getBundle= getIntent().getExtras();
            idNumber=getBundle.getString("idNumber");
            munsellChip=getBundle.getString("munsellChip");
            notes=getBundle.getString("notes");
            dataListText.setText(idNumber+" , "+munsellChip+" , "+notes+"\n");
//        }
//        else{
//            Bundle getBundle= getIntent().getExtras();
//            idNumber=getBundle.getString("idNumber");
//            munsellChip=getBundle.getString("munsellChip");
//            notes=getBundle.getString("notes");
//            savedDataString=getBundle.getString("dataList");
//            dataListText.setText(restoreData);
//            dataListText.append(idNumber+" , "+munsellChip+" , "+notes+"\n");
//            dataListText.setText(idNumber+" , "+munsellChip+" , "+notes+"\n"+savedDataString);
        }

//        savedValues = getSharedPreferences("SavedValues", MODE_APPEND);


//    @Override
//    public void onSaveInstanceState(Bundle savedInstanceState) {
//        super.onSaveInstanceState(savedInstanceState);
//        // Save UI state changes to the savedInstanceState.
//        // This bundle will be passed to onCreate if the process is
//        // killed and restarted.
//
//        savedInstanceState.putString("dataList", dataListText.getText().toString());
//        // etc.
//    }
//
//
//    @Override
//    public void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//        // Restore UI state from the savedInstanceState.
//        // This bundle has also been passed to onCreate.
//        restoreData=savedInstanceState.getString("dataList");
//    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dfHome:
                dataListText=(TextView) findViewById(R.id.dataList);
                Intent intent= new Intent(this, MainActivity.class);
//                Bundle bundle=new Bundle();
//                bundle.putString("dataList", dataListText.getText().toString());
//                Bundle bundle2= new Bundle();
//                bundle2.putString("newText", dataList.getText().toString());
//                intent.putExtras(bundle2);
                startActivity(intent);

                break;
            case R.id.dfEmail:
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{ });
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
                emailIntent.putExtra(Intent.EXTRA_TEXT, dataListText.getText().toString());
                startActivity(emailIntent);
                break;


        }
    }

//    @Override
//    public void onPause() {
//        // save the instance variables
//        dataListString = dataList.getText().toString();
//        Bundle dataStringBundle= new Bundle();
//        dataStringBundle.putString("dataStringList",dataListString );
//
////        SharedPreferences.Editor editor = savedValues.edit();
////        editor.putString("dataList", dataListString);
//        editor.commit();
//
//        super.onPause();
//    }
//
//    @Override
//    public void onResume() {
//        dataString = savedValues.getString("dataList", dataListString);
//        dataList = (TextView) findViewById(R.id.dataList);
//        dataList.setText(dataString);
//
//        super.onResume();
//    }

}
