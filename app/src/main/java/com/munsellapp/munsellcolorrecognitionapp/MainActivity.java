package com.munsellapp.munsellcolorrecognitionapp;

import android.app.Activity;
import android.app.Notification;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;

import com.opencsv.CSVReader;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

//import androidinterview.com.androidcamera.R;

public class MainActivity extends Activity implements View.OnClickListener {


    static int TAKE_PIC = 0;
    static int SELECT_FILE = 1;
    private ImageView imageView;
    private Button calibrateButton;
    private Button chooseImage;
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
        chooseImage = (Button) findViewById(R.id.ChooseImage);
        chooseImage.setOnClickListener(this);
        calibrateButton = (Button) findViewById(R.id.button3);
        //calibrateButton.setOnClickListener(this);
        getMunsellButton = (Button) findViewById(R.id.munsellButton);
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
        //File file = new File(Environment.getExternalStorageDirectory(),
        //       "MyPhoto.jpg");
        //outPutfileUri = Uri.fromFile(file);
        // intent.putExtra(MediaStore.EXTRA_OUTPUT, outPutfileUri);
        startActivityForResult(intent, TAKE_PIC);
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

    /*
    Function to set bitmap as the selected
    image from gallery intent, then passes bitmap to
    ImageActivity -JB
     */
    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] mybytearray = stream.toByteArray();
                Intent intent = new Intent(this, ImageActivity.class);
                intent.putExtra("image", mybytearray);
                startActivity(intent);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

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

            Intent intent = new Intent(this, ImageActivity.class);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.PNG, 100, stream);
            // byte[] byteArray = stream.toByteArray();
            intent.putExtra("byteArray", stream.toByteArray());
            startActivity(intent);
        }
        if (requestCode == SELECT_FILE && resultCode == RESULT_OK)
            onSelectFromGalleryResult(data);

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
            startActivity(new Intent(MainActivity.this, ImageActivity.class));
        }


    }
}




