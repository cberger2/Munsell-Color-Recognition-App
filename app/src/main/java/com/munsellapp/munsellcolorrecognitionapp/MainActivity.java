package com.munsellapp.munsellcolorrecognitionapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

//import androidinterview.com.androidcamera.R;

public class MainActivity extends Activity implements View.OnClickListener {


    private static int TAKE_PIC = 0;
    private static int CALIBRATE_PIC=2;
    private static int SELECT_FILE = 1;
    private ImageView imageView, img;
    private Button calibrateButton, chooseImage;
    private ImageButton getMunsellButton;
    private TextView Munsell;
    protected final static String TAG = "ColorUtils";
    //Bitmap bitmapphoto;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chooseImage = (Button) findViewById(R.id.ChooseImage);
        chooseImage.setOnClickListener(this);
        calibrateButton = (Button) findViewById(R.id.button3);
        //calibrateButton.setOnClickListener(this);
        getMunsellButton = (ImageButton) findViewById(R.id.munsellButton);
        Munsell = (TextView) findViewById(R.id.textView2);



        /*Creates text view with different colored text*/
        String text = "<font color=#960202>M</font> " +
                "<font color=#E6790C>U</font> " +
                "<font color=#E6A627>N</font> " +
                "<font color=#E6DC4A>S</font> " +
                "<font color=#B3AE12>E</font> " +
                "<font color=#284F00>L</font> " +
                "<font color=#03447D>L</font>";
        Munsell.setText(Html.fromHtml(text));



    }


    /*Starts camera Intent -JB*/
    public void CameraClick(View v) {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, TAKE_PIC);
    }

    /*Starts camera Intent -JB*/
    public void CalibrateCameraClick(View v) {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CALIBRATE_PIC);
    }

    /*Opens gallery view, then sets Result Code signaling that
    image has been selected -JB
     */
    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }


   /*Gets result code from camera and gallery intents
   if result is from camera intent, sends image to ImageActivity;
   if result is from gallery intent, calls function to send image to
   ImageActivity -JB

     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == TAKE_PIC && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            PassBitmapToNextActivity(photo,ImageActivity.class,"CameraImage");
        }

        if (requestCode == SELECT_FILE && resultCode == RESULT_OK) {
            Bitmap bm;
            if (data != null) {
                try {
                    bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                    PassBitmapToNextActivity(bm,ImageActivity.class,"GalleryImage");
                } catch (IOException e) {
                    e.printStackTrace();
                }}}

        if (requestCode == CALIBRATE_PIC && resultCode == RESULT_OK){
            Bitmap Calphoto = (Bitmap) data.getExtras().get("data");
            PassBitmapToNextActivity(Calphoto,Calibrate.class,"CalibrateImage");
        }
    }

    /*Passes Bitmap from any intent (camera, gallery, or calibrate camera) and passes it to specified activity)*/
    public void PassBitmapToNextActivity (Bitmap bm, Class myClass, String extraName ){
        Intent intent = new Intent(this, myClass);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
        intent.putExtra(extraName, stream.toByteArray());
        startActivity(intent);

    }

/*
Either calls galleryIntent when ChooseImage button is clicked
or opens cameraIntent -JB
 */
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.ChooseImage) {
            galleryIntent();
        } else {
            Intent intent=new Intent(this, ImageActivity.class);
            startActivity(intent);

        }


    }



}





