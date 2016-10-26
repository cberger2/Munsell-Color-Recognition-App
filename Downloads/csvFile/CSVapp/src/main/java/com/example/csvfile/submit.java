package com.example.csvfile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class submit extends AppCompatActivity {
    private final static String storeData = "data.txt";
    EditText idEdit, notesEdit;
    TextView munsellChip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);
        idEdit = (EditText) findViewById(R.id.idEdit);
        notesEdit = (EditText) findViewById(R.id.notesEdit);
        munsellChip= (TextView) findViewById(R.id.munsellChip);
//        munsellChip.setText(""+ savedValues);
    }

    public void saveClicked(View v) {
        try {
            OutputStreamWriter out =

                    new OutputStreamWriter(openFileOutput(storeData, 0));

            out.write(idEdit.getText().toString());
            out.write(notesEdit.getText().toString());

            out.close();

            Toast

                    .makeText(this, "The contents are saved in the file.", Toast.LENGTH_LONG)

                    .show();

        } catch (Throwable t) {

            Toast

                    .makeText(this, "Exception: " + t.toString(), Toast.LENGTH_LONG)

                    .show();

        }

    }
    public void readFileInEditor()

    {

        try {

            InputStream in = openFileInput(storeData);

            if (in != null) {

                InputStreamReader tmp=new InputStreamReader(in);

                BufferedReader reader=new BufferedReader(tmp);

                String str;

                StringBuilder buf=new StringBuilder();

                while ((str = reader.readLine()) != null) {

                    buf.append(str+"n");

                }

                in.close();

                idEdit.setText(buf.toString());
                notesEdit.setText(buf.toString());

            }

        }

        catch (FileNotFoundException e) {

// that's OK, we probably haven't created it yet

        }

        catch (Throwable t) {

            Toast

                    .makeText(this, "Exception: "+t.toString(), Toast.LENGTH_LONG)

                    .show();

        }

    }
}

