package com.murach.invoice.imageselection;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

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
    private Rect rectShape = new Rect();
    private float mX, mY, lastX, lastY;
    private static final float TOUCH_TOLERANCE = 4;
    ImageView imageView;
    TextView textView;
    private ArrayList<Integer> indices = new ArrayList<>();

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

        textView = (TextView) findViewById(R.id.textView);

        seekbar = (SeekBar) findViewById(R.id.seekBar);
        seekbar.setOnSeekBarChangeListener(this);
        seekbar.setProgress(200);

    }


    /**
     *Changing bound via the seekbar.
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
         *
         * Touch event for the entire screen; where the code goes to adjust circle size, etc.
         */
        @Override
        public boolean onTouchEvent(MotionEvent event) {

            float x = event.getX();
            float y = event.getY();

            System.out.println(">>" + event.getAction());
            System.out.println("Bound: " + (int) bound);
            System.out.println("X: " + x + " Y: " + y);
            //System.out.println("Bottom of ImageView " + imageView.getBottom());
            System.out.println("Indices: " + indices);

            System.out.println("Top of seekbar: " + seekbar.getTop());
            System.out.println("Height of bitmap: " + DrawBitmap.getHeight());

            rectShape.set((int)x - getBounds(), (int)y - getBounds(), getBounds() + (int)x, getBounds() + (int)y);

            if (y < Disp.getHeight() - 550) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        //touch_start(x, y);
                        clear_canvas();
                        System.out.println("ACTION_DOWN");

                        mCanvas.drawRect(rectShape, mPaint);

                        Bitmap bitmap = Bitmap.createBitmap(rectShape.width(), rectShape.height(), Bitmap.Config.ARGB_4444);

                        for (int i = 0; i < rectShape.width(); ++i) {
                            int pixel = bitmap.getPixel(rectShape.width() - 1, rectShape.height() - 1);
                            int red = Color.red(pixel);
                            int blue = Color.blue(pixel);
                            int green = Color.green(pixel);
                            indices.add(red);
                            indices.add(blue);
                            indices.add(green);

                        }

                        if (rectShape.isEmpty()) {
                            System.out.println("empty");
                        } else {
                            System.out.println("not empty");
                            System.out.println(rectShape.describeContents());
                        }

//                        lastX = event.getX();
//                        lastY = event.getY();


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

            } else {
                System.out.println("STOP");
            }
            return false;
        }

        private void clear_canvas() {
            mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        }

        private void test() {

        }

        private int setBounds(int bound) {
            return this.bound = bound;
        }

        private int getBounds() {
            return bound;
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