package com.murach.invoice.imageselection;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {
    private Bitmap DrawBitmap;
    private Canvas mCanvas;
    private Path mPath;
    private Paint DrawBitmapPaint, mPaint;
    private RelativeLayout Rl;
    private CustomView customView;
    private Display Disp;
    private SeekBar seekbar;
    private float mX, mY, lastX, lastY;
    private static final float TOUCH_TOLERANCE = 4;
    ImageView imageView;

    protected void onCreate(Bundle savedInstances) {
        super.onCreate(savedInstances);
        setContentView(R.layout.activity_main);
        customView = new CustomView(this);
        Rl = (RelativeLayout) findViewById(R.id.activity_main);
        Rl.addView(customView);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.MAGENTA);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(10);

//        imageView = (ImageView) findViewById(R.id.imageView1);

        seekbar = (SeekBar) findViewById(R.id.seekBar);
        seekbar.setOnSeekBarChangeListener(this);

    }


    /**
     *Changing radius via the seekbar.
     */
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
        customView.setRadius(progress);
//        mCanvas.drawCircle(lastX, lastY, customView.setRadius(progress), mPaint);
        customView.invalidate();

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    public class CustomView extends View {
        private float radius;

        @SuppressWarnings("deprecation")
        public CustomView(Context c) {

            super(c);
            Disp = getWindowManager().getDefaultDisplay();

            DrawBitmap = Bitmap.createBitmap(Disp.getWidth(), Disp.getHeight(),
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
            System.out.println(Disp.getHeight() + " ANDREW");
        }

        private void clear_canvas() {
            mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        }

        Random random = new Random();

        /**
         * Method's I've been messing around with to find the pixels within the circles area.
         * What y'all could help me with.
         *
         * Use Midpoint Circle Algorithm to return pixels. All we have to do is be able to detect pixels
         * within the circle. Once we've broken through on that, then converting pixels to color is simple.
         */

        private Point CalculatePoint(float originX, float originY)
        {
            double angle = random.nextDouble() * Math.PI * 2;
            double radius = random.nextDouble() * getRadius();
            double x = originX + radius * Math.cos(angle);
            double y = originY + radius * Math.sin(angle);
            return new Point((int)x,(int)y);
        }

        public void cRGB(Canvas canvas) {
            ArrayList<Integer> indices = new ArrayList<>();
            int height = 2 * (int)getRadius();
            int width = height;

            for (int x = 0; x < width; x++)
            {
                for (int y = 0; y < height; y++)
                {
                    //M1 & M2 will represent where the circles midpoint is. TO-DO.
                    double dx = x - m1;
                    double dy = y - m2;
                    double distanceSquared = dx * dx + dy * dy;

                    if (distanceSquared <= Math.pow(getRadius(), 2))
                    {
                        indices.add(x + y * width);
                    }
                }
            }
        }


        /**
         *
         * Touch event for the entire screen; where the code goes to adjust circle size, etc.
         */
        @Override
        public boolean onTouchEvent(MotionEvent event) {

            float x = event.getX();
            float y = event.getY();

            System.out.println((int) radius);
            System.out.println(">>" + event.getAction());
            System.out.println("X: " + x + " Y: " + y);
//            System.out.println(imageView.getBottom());

            while (!seekbar.isPressed()) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        //touch_start(x, y);
                        clear_canvas();
                        System.out.println("1");

                        mCanvas.drawCircle(x, y, getRadius(), mPaint);

                        lastX = event.getX();
                        lastY = event.getY();

//                    System.out.println(CalculatePoint(x, y));

                        invalidate();
                        break;
                    //return false;
////                case MotionEvent.ACTION_MOVE:
////                    //touch_move(x, y);
////                    System.out.println("2");
////                    invalidate();
////                    break;
////                    //return true;
//                    case MotionEvent.ACTION_UP:
//                        //touch_up();
//                        System.out.println("3");
//                        //invalidate();
//                        break;
                    //return true;

                }
                return false;

            }
            return false;
        }

        private float setRadius(int radius) {
            return this.radius = radius;
        }

        private float getRadius() {
            return radius;
        }

    }
}





/*
 * Ignore everything below this line. Just previous code for free-from drawing, which we changed
 * to just have a circle. No longer relevant, but storing code here just in case in the meantime.
 */


//
//                    tPath.addCircle(mStartPointsX.get(0).x, mStartPointsY.get(0).y, 2, Direction.CCW);


//                    if (new Point(mStartPointsX.get(0).x, mStartPointsY.get(0).y) ==
//                           new Point(mEndPointsX.get(mEndPointsX.size() - 1).x, mEndPointsY.get(mEndPointsY.size() - 1).y)) {
//
//                        //Return RGB value here of contents within circle.
//                        //TO-DO: MAKE INTO A FUNCTION FOR CLEANER CODE.
//                        for (int xx = 0; x < mCanvas.getWidth(); x++)
//                        {
//                            for (int yy = 0; y < mCanvas.getHeight(); y++)
//                            {
//                                double radius = new Point(mStartPointsX.get(0).x, mStartPointsY.get(0).y).describeContents() /
//                                        ((new Point(mEndPointsX.get(mEndPointsX.size() / 2).x, mEndPointsY.get(mEndPointsY.size() / 2).y)).describeContents());
//                                double dx = xx - radius;
//                                double dy = yy - radius;
//                                double distanceSquared = dx * dx + dy * dy;
//                                double radiusSquared = radius * radius;
//
//                                if (distanceSquared <= radiusSquared)
//                                {
//                                    circleRGBValues.add((int) (x + y * mCanvas.getWidth()));
//                                }
//                            }
//                        }
//
//                        //For testing purposes.
//                        System.out.println("RGB VALUES: " + circleRGBValues);
//                        System.out.println("Full: " + mStartPointsX.get(0) + ", " + mStartPointsY + " X: " + (int) x + " Y: " + (int) y);
//
//                    } else {
//
//                        //Go to the startingPoints and then return RGB value here of contents within circle.
//                        mPath.moveTo(mEndPointsX.get(mEndPointsX.size() - 1).x, mEndPointsY.get(mEndPointsY.size() - 1).y);
//                        mPath.lineTo(mStartPointsX.get(0).x, mStartPointsY.get(0).y);
//
//                        //Return RGB value here of contents within circle.
////                        if (new Point(mStartPointsX.get(0).x, mStartPointsY.get(0).y) ==
////                                new Point(mEndPointsX.get(mEndPointsX.size() - 1).x, mEndPointsY.get(mEndPointsY.size() - 1).y)) {
////
////                            //TO-DO: MAKE INTO A FUNCTION FOR CLEANER CODE.
////
////                        }
//
//                        //For testing purposes.
//                        System.out.println("--------------------");
//                        System.out.println("RGB VALUES: " + circleRGBValues);
//                        System.out.println("Null (STARTING POINTS): X: " + mStartPointsX.get(0) + ", Y: " + mStartPointsY.get(0));
//                        System.out.println("Null (END POINTS): X: " + mEndPointsX.get(mEndPointsX.size() - 1) + ", Y: " + mEndPointsY.get(mEndPointsX.size() - 1));
//
//                    }


                    //TO-DO: FIX ERROR WHICH CRASHES APP IF USER ONLY CLICKS THE SCREEN, AND DOESN'T DRAW A PATH.
//                    if (new Point(mStartPointsX.get(0).x, mStartPointsY.get(0).y) ==
//                            new Point(mEndPointsX.get(mEndPointsX.size() - 1).x, mEndPointsY.get(mEndPointsY.size() - 1).y) && mStartPointsX.size() < 0) {
//                        System.out.println("Draw more than one point");
//                        break;
//                    }


//
//    private void touch_start(float x, float y) {
//        mPath.reset();
//        mPath.moveTo(x, y);
//        mX = x;
//        mY = y;
//    }
//
//    private void touch_move(float x, float y) {
//        float dx = Math.abs(x - mX);
//        float dy = Math.abs(y - mY);
//        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
//            mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
//            mX = x;
//            mY = y;
//        }
//
//    }
//
//    private void touch_up() {
//
//        mPath.lineTo(mX, mY);
//
//        mCanvas.drawPath(mPath, mPaint);
//
//        mPath.reset();
//    }