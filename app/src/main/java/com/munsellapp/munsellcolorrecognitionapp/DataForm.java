package com.munsellapp.munsellcolorrecognitionapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class DataForm extends AppCompatActivity implements View.OnClickListener {
    TextView dataListText, savedData;
    String idNumber, munsellChip, notes, dataString, dataListString, dataListStringBundle;
    ImageButton home, email;
    SharedPreferences savedValues;
    String savedDataString, restoreData;
    String savedDataPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_form);
        dataListText = (TextView) findViewById(R.id.dataList);
        home = (ImageButton) findViewById(R.id.dfHome);
        home.setOnClickListener(this);
        email = (ImageButton) findViewById(R.id.dfEmail);
        email.setOnClickListener(this);

//
        Bundle getBundle = getIntent().getExtras();
        idNumber = getBundle.getString("idNumber");
        munsellChip = getBundle.getString("munsellChip");
        notes = getBundle.getString("notes");

        SharedPreferences sp = getSharedPreferences("key", 0);
        savedDataPref = sp.getString("savedDataPref", "");
        dataListText.setText("\""+idNumber +"\""+ " , " +"\""+ munsellChip +"\""+ " , " +"\""+ notes+"\"" + "\n" + savedDataPref);
    }

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
                dataListText = (TextView) findViewById(R.id.dataList);
                Intent intent = new Intent(this, MainActivity.class);

                SharedPreferences sp = getSharedPreferences("key", 0);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("savedDataPref", dataListText.getText().toString());
                editor.commit();
                startActivity(intent);

                break;
            case R.id.dfEmail:


                writeFile();
                String filelocation="/storage/emulated/0/MyAppFile/data.txt";
                Intent emailIntent= new Intent((Intent.ACTION_SEND));
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {""});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Munsell Data List");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "");
//                File root=Environment.getExternalStorageDirectory();
//                String pathToMyAttachedFile= " /storage/emulated/0/MyAppFile/data.txt";
//                File file= new File(root, pathToMyAttachedFile);
//                if(!file.exists()||!file.canRead()){
//                    System.out.println("ERROR DID NOT FIND CORRECT ROOT TO FILE");
//                    return;
//                }
//                Uri uri= Uri.fromFile(file);
                emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse( "file://"+filelocation));
                startActivity(emailIntent);


                break;
        }
    }
public void writeFile(){
    String content=dataListText.getText().toString();
    File file;
    FileOutputStream outputStream;
    try{
        file=new File(Environment.getExternalStorageDirectory(), "MyCache");
        outputStream=new FileOutputStream(file);
        outputStream.write(content.getBytes());
        outputStream.close();
        System.out.println(file);}
    catch (IOException e){
        e.printStackTrace();

    }
    dataListText.setText("");


//    String state;
//    state=Environment.getExternalStorageState();
//
//    if(Environment.MEDIA_MOUNTED.equals(state)){
//        File Root= Environment.getExternalStorageDirectory();
//        File dir=new File(Root.getAbsolutePath()+"/MyAppFile");
//
//        if(!dir.exists()) {
//
//            dir.mkdir();
//        }
//        File dataText=new File(dir,"data.txt");
//        System.out.print(dataText);
//        String data=dataListText.getText().toString();
//        try {
//            FileOutputStream fos=new FileOutputStream(dataText);
//            fos.write(data.getBytes());
//            fos.close();
//            dataListText.setText("");
//            Toast.makeText(this,"File saved", Toast.LENGTH_LONG ).show();
//
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
//    }
//    else{
//        Toast.makeText(this, "SD card not found", Toast.LENGTH_LONG).show();
//    }
////    readFile();
//
//    File Root= Environment.getExternalStorageDirectory();

}
    public void readFile(){
        File Root= Environment.getExternalStorageDirectory();
        File dir=new File(Root.getAbsolutePath()+"/MyAppFile");
        File dataText=new File(dir,"data.txt");

        String message;
        try {
            FileInputStream fis=new FileInputStream(dataText);
            InputStreamReader isr= new InputStreamReader(fis);
            BufferedReader buffRead= new BufferedReader(isr);
            StringBuffer stringBuff= new StringBuffer();

            while((message=buffRead.readLine())!=null){
                stringBuff.append(message+"/n");
            }

            System.out.println(stringBuff.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}


//EVERYTHING BELOW THIS ARE DIFFERENT IDEA ATTEMPTS. IGNORE.

//    public void createFile(String fileName) throws IOException {
//        File file = new File(fileName);
//
//        if (!file.exists()) {
//            file.createNewFile();
//
//
//            FileWriter fw = new FileWriter(file.getAbsoluteFile());
//            BufferedWriter bw = new BufferedWriter(fw);
//            bw.write(dataListText.getText().toString());
//            bw.close();
//
//        }
//    }
//
///** Method to check whether external media available and writable. This is adapted from
// http://developer.android.com/guide/topics/data/data-storage.html#filesExternal */
//
//    private void checkExternalMedia(){
//        boolean mExternalStorageAvailable = false;
//        boolean mExternalStorageWriteable = false;
//        String state = Environment.getExternalStorageState();
//
//        if (Environment.MEDIA_MOUNTED.equals(state)) {
//            // Can read and write the media
//            mExternalStorageAvailable = mExternalStorageWriteable = true;
//        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
//            // Can only read the media
//            mExternalStorageAvailable = true;
//            mExternalStorageWriteable = false;
//        } else {
//            // Can't read or write
//            mExternalStorageAvailable = mExternalStorageWriteable = false;
//        }
//        tv.append("\n\nExternal Media: readable="
//                +mExternalStorageAvailable+" writable="+mExternalStorageWriteable);
//    }
//
//    /** Method to write ascii text characters to file on SD card. Note that you must add a
//     WRITE_EXTERNAL_STORAGE permission to the manifest file or this method will throw
//     a FileNotFound Exception because you won't have write permission. */
//
//    private File writeToSDFile(){
//
//        // Find the root of the external storage.
//        // See http://developer.android.com/guide/topics/data/data-  storage.html#filesExternal
//
//        File root = android.os.Environment.getExternalStorageDirectory();
//        tv.append("\nExternal file system root: "+root);
//
//        // See http://stackoverflow.com/questions/3551821/android-write-to-sd-card-folder
//
//        File dir = new File (root.getAbsolutePath() + "/download");
//        dir.mkdirs();
//        File file = new File(dir, "myData.txt");
//
//        try {
//            FileOutputStream f = new FileOutputStream(file);
//            PrintWriter pw = new PrintWriter(f);
//            pw.println(savedDataPref);
//            pw.flush();
//            pw.close();
//            f.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//            Log.i(TAG, "******* File not found. Did you" +
//                    " add a WRITE_EXTERNAL_STORAGE permission to the   manifest?");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
////        tv.append("\n\nFile written to "+file);
//        return file;
//    }
//
//    /** Method to read in a text file placed in the res/raw directory of the application. The
//     method reads in all lines of the file sequentially. */
//
//    private void readRaw(){
//        tv.append("\nData read from res/raw/textfile.txt:");
//        InputStream is = this.getResources().openRawResource(R.raw.textfile);
//        InputStreamReader isr = new InputStreamReader(is);
//        BufferedReader br = new BufferedReader(isr, 8192);    // 2nd arg is buffer size
//
//        // More efficient (less readable) implementation of above is the composite expression
//    /*BufferedReader br = new BufferedReader(new InputStreamReader(
//            this.getResources().openRawResource(R.raw.textfile)), 8192);*/
//
//        try {
//            String test;
//            while (true){
//                test = br.readLine();
//                // readLine() returns null if no more lines in the file
//                if(test == null) break;
//                tv.append("\n"+"    "+test);
//            }
//            isr.close();
//            is.close();
//            br.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        tv.append("\n\nThat is all");
//    }
//}


