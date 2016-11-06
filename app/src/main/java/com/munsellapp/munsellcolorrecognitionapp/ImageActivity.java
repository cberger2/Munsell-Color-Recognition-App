package com.munsellapp.munsellcolorrecognitionapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.opencsv.CSVReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

//import androidinterview.com.androidcamera.R;

/**
 * Created by Victorine on 10/12/16.
 */
//comment

public class ImageActivity extends AppCompatActivity implements View.OnClickListener {
    private Button calibrate;
    private ImageView ResultPic;
    private ImageButton saveresult, exportresult, home;
    Bitmap b;
    String munsellValue;

//    int actualRed, actualGreen, actualBlue;
    int compareRed, compareGreen, compareBlue;
    double smallestDif = 1000;
    int smallRed, smallGreen, smallBlue;

    int red;
    int green;
    int blue;
    int i;
    //this is a comment
    //this is another comment
    //Can you see my comment


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_layout);
        home = (ImageButton) findViewById(R.id.homeButton);
        home.setOnClickListener(this);
        calibrate=(Button) findViewById(R.id.button3);
        ResultPic = (ImageView) findViewById(R.id.imageView1);
        saveresult=(ImageButton)findViewById(R.id.saveButton);
        saveresult.setOnClickListener(this);
        exportresult=(ImageButton)findViewById(R.id.submitButton);
        exportresult.setOnClickListener(this);

/* Extracts image taken from camera or image selected from gallery and
passes it to imageview
 */
        if (getIntent().hasExtra("byteArray")) {
            b = BitmapFactory.decodeByteArray(
                    getIntent().getByteArrayExtra("byteArray"), 0, getIntent().getByteArrayExtra("byteArray").length);
            ResultPic.setImageBitmap(b);
        }
        else if (getIntent().hasExtra("image")) {
            b = BitmapFactory.decodeByteArray(
                    getIntent().getByteArrayExtra("image"), 0, getIntent().getByteArrayExtra("image").length);
            ResultPic.setImageBitmap(b);
        }


//         ResultPic.setImageBitmap(resultImage);
    }

    public static double getDistance(float aR, float aG, float aB, float cR, float cG, float cB) {
        float dx = aR - cR;
        float dy = aG - cG;
        float dz = aB - cB;

        // We should avoid Math.pow or Math.hypot due to perfomance reasons
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }


    public void munsell(View v) throws IOException {

        TextView text = (TextView) findViewById(R.id.musellValue);

        InputStream csv;

        csv = getAssets().open("munsell.csv");

        InputStreamReader is = new InputStreamReader(csv);

        CSVReader csvReader = new CSVReader(is);
        String[] line;
        csvReader.readNext();
        getSpecs();


        while ((line = csvReader.readNext()) != null) {
            compareRed = Integer.parseInt(line[line.length-3]);
            compareGreen = Integer.parseInt(line[line.length-2]);
            compareBlue = Integer.parseInt(line[line.length-1]);
            if (getDistance(red, green, blue, compareRed, compareGreen, compareBlue) < smallestDif) {
                smallestDif = getDistance(red, green, blue, compareRed, compareGreen, compareBlue);
                smallRed = Integer.parseInt(line[3]);
                smallGreen = Integer.parseInt(line[4]);
                smallBlue = Integer.parseInt(line[5]);
            }
            else
                csvReader.readNext();
        }
        System.out.println("smalled difference: "+Double.toString(smallestDif)+"/n smallest red: "+ Double.toString(smallRed)+"/n Actual red:"
                +Integer.toString(red)+ "/n Smallest green: "+ Double.toString(smallGreen)+ "/n Actual Green:"+ Integer.toString(green)+
                "/n Actual blue:" +Integer.toString(blue)+ "/n Smallest Blue: "+ Double.toString(smallBlue));

        InputStream csv2;

        csv2 = getAssets().open("munsell.csv");

        InputStreamReader is2 = new InputStreamReader(csv2);

        CSVReader csvReader2 = new CSVReader(is2);
        String[] line2;
        csvReader2.readNext();


        while ((line2 = csvReader2.readNext()) != null) {
            if (smallRed == (Integer.parseInt(line2[line2.length - 3]))) {
                if (smallGreen == (Integer.parseInt(line2[line2.length - 2]))) {
                    if (smallBlue == Integer.parseInt(line2[line2.length - 1])) {
                        munsellValue = line2[0] + " " + line2[1] + "/" + line2[2];
                        text.setText(munsellValue);

                    }
                }
            }
        }
  setBackground(smallRed,smallGreen,smallBlue);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.homeButton:
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
                break;
            case R.id.submitButton:
                Intent submitForm= new Intent(this, SubmitForm.class);
                submitForm.putExtra(munsellValue, "MunsellValue");
                startActivity(submitForm);
                break;
            case R.id.saveButton:
                Intent save= new Intent(this, SubmitForm.class);
                startActivity(save);
                break;


        }
//        Intent i = new Intent(this, MainActivity.class);
//        startActivity(i);

    }

    public void setBackground(int red, int green, int blue) {
        View view = this.getWindow().getDecorView();


//Changes the background of the app to the color of the munsell chips
        if (red > 9 && green > 9 && blue > 9) {
            StringBuilder builder = new StringBuilder();
            builder.append("#");
            builder.append(Integer.toHexString(red));
            builder.append(Integer.toHexString(green));
            builder.append(Integer.toHexString(blue));
            view.setBackgroundColor(Color.parseColor(builder.toString()));
        } else if (red < 10) {
            StringBuilder builder = new StringBuilder();
            builder.append("#");
            builder.append("0" + Integer.toString(red));
            builder.append(Integer.toHexString(green));
            builder.append(Integer.toHexString(blue));

            view.setBackgroundColor(Color.parseColor(builder.toString()));
        } else if (green < 10) {
            StringBuilder builder = new StringBuilder();
            builder.append("#");
            builder.append(Integer.toHexString(red));
            builder.append("0" + Integer.toString(green));
            builder.append(Integer.toHexString(blue));

            view.setBackgroundColor(Color.parseColor(builder.toString()));
        } else if (blue < 10) {
            StringBuilder builder = new StringBuilder();
            builder.append("#");
            builder.append(Integer.toHexString(red));
            builder.append(Integer.toHexString(green));
            builder.append("0" + Integer.toString(blue));
            view.setBackgroundColor(Color.parseColor(builder.toString()));
        } else if (red < 10 && green < 10) {
            StringBuilder builder = new StringBuilder();
            builder.append("#");
            builder.append("0" + Integer.toString(red));
            builder.append("0" + Integer.toString(green));
            builder.append(Integer.toHexString(blue));

            view.setBackgroundColor(Color.parseColor(builder.toString()));
        } else if (red < 10 && blue < 10) {
            StringBuilder builder = new StringBuilder();
            builder.append("#");
            builder.append("0" + Integer.toString(red));
            builder.append(Integer.toHexString(green));
            builder.append("0" + Integer.toString(blue));

            view.setBackgroundColor(Color.parseColor(builder.toString()));
        } else if (green < 10 && blue < 10) {
            StringBuilder builder = new StringBuilder();
            builder.append("#");
            builder.append(Integer.toHexString(red));
            builder.append("0" + Integer.toString(green));
            builder.append("0" + Integer.toString(blue));

            view.setBackgroundColor(Color.parseColor(builder.toString()));
        } else if (green < 10 && blue < 10 && red < 10) {
            StringBuilder builder = new StringBuilder();
            builder.append("#");
            builder.append("0" + Integer.toString(red));
            builder.append("0" + Integer.toString(green));
            builder.append("0" + Integer.toString(blue));

            view.setBackgroundColor(Color.parseColor(builder.toString()));

        }
    }
    public void getSpecs() {
        //When implementing with camera, change field i to get the image taken from the camera,
        //so it's no longer pre loaded in with Android Studio
        ImageView i = new ImageView(this);
        i.setImageBitmap(b);
        int w = i.getWidth();
        int h = i.getHeight();
        Bitmap bitmap = ((BitmapDrawable) i.getDrawable()).getBitmap();
        int pixel = bitmap.getPixel(w, h);
        red = Color.red(pixel);
        blue = Color.blue(pixel);
        green = Color.green(pixel);

    }
    //Test Comment
}





