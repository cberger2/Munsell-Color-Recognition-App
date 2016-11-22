package com.munsellapp.munsellcolorrecognitionapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;


public class ImageSelection extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {
    private Bitmap DrawBitmap;
    private Canvas mCanvas;
    private Path mPath;
    private Paint DrawBitmapPaint, mPaint;
    private RelativeLayout Rl;
    private CustomView customView;
    private Display Disp;
    private SeekBar seekbar;
    private Rect rectShape = new Rect();
    private float mX, mY;
    private ImageView imageView;
    private TextView textView;

    public ImageSelection(ImageView imageView){
        this.imageView = imageView;
    }

    protected void onCreate(Bundle savedInstances) {
        super.onCreate(savedInstances);
        setContentView(R.layout.activity_main);
        customView = new CustomView(this);
        Rl = (RelativeLayout) findViewById(R.id.activity_image_selection);
        Rl.addView(customView);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.MAGENTA);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(10);

        //imageView = (ImageView) findViewById(R.id.imageView1);

        textView = (TextView) findViewById(R.id.textView);
        seekbar = (SeekBar) findViewById(R.id.seekBar);

        seekbar.setOnSeekBarChangeListener(this);
        seekbar.setProgress(200);

    }


    /**
     * Changing bound via the seekbar.
     */
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
        customView.setBounds(progress);
        customView.invalidate();

        textView.setText("Width: " + progress);

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    public class CustomView extends View {
        private int bound;

        @SuppressWarnings("deprecation")
        public CustomView(Context c) {

            super(c);
            Disp = getWindowManager().getDefaultDisplay();

            DrawBitmap = Bitmap.createBitmap(Disp.getWidth(), Disp.getHeight() - 400,
                    Bitmap.Config.ARGB_4444);

            mCanvas = new Canvas(DrawBitmap);

            mPath = new Path();
            DrawBitmapPaint = new Paint(Paint.DITHER_FLAG);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            setDrawingCacheEnabled(true);
            canvas.drawBitmap(DrawBitmap, 0, 0, DrawBitmapPaint);
            canvas.drawPath(mPath, mPaint);
            canvas.drawRect(mY, 0, mY, 0, DrawBitmapPaint);
        }

        /**
         * Touch event for the entire screen; where the code goes to adjust circle size, etc.
         */
        @Override
        public boolean onTouchEvent(MotionEvent event) {

            float x = event.getX();
            float y = event.getY();

            rectShape.set((int) x - getBounds(), (int) y - getBounds(), getBounds() + (int) x, getBounds() + (int) y);

            if (y < Disp.getHeight() - 550) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        clear_canvas();

                        mCanvas.drawRect(rectShape, mPaint);

                        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

                        Bitmap test = Bitmap.createBitmap(bitmap, (int) x, (int) y, rectShape.width(), rectShape.height());

                        int red = 0;
                        int blue = 0;
                        int green = 0;
                        int pixelCount = 0;

                        for (int yy = 0; yy < test.getHeight(); ++yy) {
                            for (int xx = 0; xx < test.getWidth(); ++xx) {
                                int pixel = test.getPixel(xx, yy);

                                red += Color.red(pixel);
                                blue += Color.blue(pixel);
                                green += Color.green(pixel);
                                ++pixelCount;

                            }
                        }

                        int red0 = (red / pixelCount);
                        int blue0 = (blue / pixelCount);
                        int green0 = (green / pixelCount);

                        System.out.println(red0);
                        System.out.println(blue0);
                        System.out.println(green0);
                        System.out.println(pixelCount);

                        invalidate();
                        break;

                }
                return false;

            }
            return false;
        }

        private void clear_canvas() {
            mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        }

        private int setBounds(int bound) {
            return this.bound = bound;
        }

        private int getBounds() {
            return bound;
        }

    }
}