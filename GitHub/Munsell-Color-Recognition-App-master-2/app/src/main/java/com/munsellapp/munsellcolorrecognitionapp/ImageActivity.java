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
import android.widget.ImageView;
import android.widget.TextView;

import com.opencsv.CSVReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static android.R.attr.bitmap;
import static android.R.attr.x;
import static android.R.attr.y;

//import androidinterview.com.androidcamera.R;

/**
 * Created by Victorine on 10/12/16.
 */
//comment

public class ImageActivity extends AppCompatActivity implements View.OnClickListener {
    private Button calibrate;
    private Button Home;
    private ImageView ResultPic;
    Bitmap b;

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
        Home = (Button) findViewById(R.id.button2);
        Home.setOnClickListener(this);
        calibrate=(Button) findViewById(R.id.button3);
        ResultPic = (ImageView) findViewById(R.id.imageView1);


        if (getIntent().hasExtra("byteArray")) {
            b = BitmapFactory.decodeByteArray(
                    getIntent().getByteArrayExtra("byteArray"), 0, getIntent().getByteArrayExtra("byteArray").length);
            ResultPic.setImageBitmap(b);


        }

//         ResultPic.setImageBitmap(resultImage);
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
            for (i = 0; i < 20; i++) {
                if ((red >= (Integer.parseInt(line[3]) - i) && red <= (Integer.parseInt(line[3]) + i))) {
                    if ((green >= (Integer.parseInt(line[4]) - i) && green <= (Integer.parseInt(line[4]) + i)))
                        if ((blue >= (Integer.parseInt(line[5]) - i) && blue <= (Integer.parseInt(line[5]) + i))) {
                            text.setText(line[0] + " " + line[1] + "/" + line[2]);
                        }

                }

            }
        }
        setBackground(red, green, blue);

    }


    @Override
    public void onClick(View v) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);

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
}





