package com.munsellapp.munsellcolorrecognitionapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class Calibrate extends AppCompatActivity implements View.OnClickListener {

    Bitmap b;
    ImageView caliPic;
    int actualRed, actualGreen, actualBlue;
    int specRed, specGreen, specBlue;
    static int fixRed, fixGreen, fixBlue;
    ImageButton calibrateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calibrate);
        caliPic=(ImageView) findViewById(R.id.caliPic);
        calibrateButton=(ImageButton) findViewById(R.id.calibrateImageButton);
        calibrateButton.setOnClickListener(this);

        if (getIntent().hasExtra("CalibrateImage")) {
            b = BitmapFactory.decodeByteArray(
                    getIntent().getByteArrayExtra("CalibrateImage"), 0, getIntent().getByteArrayExtra("CalibrateImage").length);

            caliPic.setImageBitmap(b);
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
        specRed = Color.red(pixel);
        specBlue = Color.blue(pixel);
        specGreen = Color.green(pixel);

    }

    //MUST PUT IN THE ACTUAL RGB VALUES THAT WE EXPECT TO GET
    public void fixColors(int Red, int Green, int Blue){
        fixRed=actualRed-Red;
        fixGreen=actualGreen-Green;
        fixBlue=actualBlue-Blue;
    }

    @Override
    public void onClick(View v) {
        //getSpecs();
        //fixColors(specRed,specGreen,specBlue);
        Intent intent=new Intent(this, MainActivity.class);
        startActivity(intent);

    }
}
