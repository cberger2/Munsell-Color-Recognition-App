package com.munsellapp.munsellcolorrecognitionapp;

import android.app.Activity;
import android.app.Notification;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;

import com.opencsv.CSVReader;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

//import androidinterview.com.androidcamera.R;

public class MainActivity extends Activity implements View.OnClickListener{


    static int TAKE_PIC = 1;
    private ImageView imageView;
    private Button munsellButton;
    private Button calibrateButton;
    private TextView Munsell;
    private ImageView img;
    protected final static String TAG = "ColorUtils";
    //Bitmap bitmapphoto;
    Button getMunsellButton;


//    int red = 160;
//    int green = 170;
//    int blue = 140;
//    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // this.imageView = (ImageView) this.findViewById(R.id.imageView1);
        calibrateButton=(Button)  findViewById(R.id.button3) ;
        calibrateButton.setOnClickListener(this);
        //munsellButton = (Button) findViewById(R.id.button);
        // color=(TextView)findViewById(R.id.textView2);
        /// Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        /// startActivityForResult(intent, TAKE_PIC);
        getMunsellButton=(Button)findViewById(R.id.munsellButton);
        Munsell=(TextView) findViewById(R.id.textView2);
        String text = "<font color=#960202>M</font> " +
                "<font color=#E6790C>U</font> " +
                "<font color=#E6A627>N</font> " +
                "<font color=#E6DC4A>S</font> " +
                "<font color=#B3AE12>E</font> " +
                "<font color=#284F00>L</font> " +
                "<font color=#03447D>L</font>";
        Munsell.setText(Html.fromHtml(text));

    }

    public void CameraClick(View v) {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //File file = new File(Environment.getExternalStorageDirectory(),
        //       "MyPhoto.jpg");
        //outPutfileUri = Uri.fromFile(file);
        // intent.putExtra(MediaStore.EXTRA_OUTPUT, outPutfileUri);
        startActivityForResult(intent, TAKE_PIC);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == TAKE_PIC && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");

            Intent intent = new Intent(this, ImageActivity.class);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.PNG, 100, stream);
            // byte[] byteArray = stream.toByteArray();
            intent.putExtra("byteArray",stream.toByteArray());
            startActivity(intent);
            //imageView.setImageBitmap(photo);
            // munsellButton.setVisibility(View.VISIBLE);
            // calibrateButton.setVisibility(View.INVISIBLE);

            // Drawable d = new BitmapDrawable(getResources(), photo);


        }


    }


    @Override
    public void onClick(View view) {

        startActivity(new Intent(MainActivity.this, ImageActivity.class));

    }


}

