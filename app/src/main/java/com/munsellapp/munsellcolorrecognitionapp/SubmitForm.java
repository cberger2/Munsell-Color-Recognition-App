package com.munsellapp.munsellcolorrecognitionapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;


public class SubmitForm extends AppCompatActivity implements View.OnClickListener {
    ImageButton save, email;
    EditText idNumber, notes;
    TextView munsell, munsellValueText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_form);
        Bundle bundle = getIntent().getExtras();
        String munsellValueString = bundle.getString("MunsellValue");
        munsellValueText = (TextView) findViewById(R.id.sfMunsellChip);
        munsellValueText.setText(munsellValueString);
        save = (ImageButton) findViewById(R.id.sfSaveButton);
        save.setOnClickListener(this);
        email=(ImageButton) findViewById(R.id.emailButton);
        email.setOnClickListener(this);
    }

    //    public void save (View v) throws IOException{
//        idNumber=(EditText) findViewById(R.id.sfIdEdit);
//        notes=(EditText) findViewById(R.id.sfNotesEdit);
//        munsell=(TextView) findViewById(R.id.sfMunsellChip);
//        String csv = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
//        CSVWriter writer = new CSVWriter(new FileWriter(csv));
//        List<String[]> data = new ArrayList<String[]>();
//        data.add(new String[] {idNumber.getText().toString(), ", ",munsell.getText().toString(), ", ", notes.getText().toString(), "/n" });
//
//        writer.writeAll(data);
//
//        Toast toast= Toast.makeText(this, "INFORMATION SAVED", Toast.LENGTH_LONG);
//        toast.show();
//
//        writer.close();
//
//
//
//
//    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sfSaveButton:
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
                break;
            case R.id.emailButton:
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{ });
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "");
                startActivity(emailIntent);
                break;


        }
    }
}
