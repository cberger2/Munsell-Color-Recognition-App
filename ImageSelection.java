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
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Bitmap DrawBitmap;
    private Canvas mCanvas;
    private Path mPath;
    private Paint DrawBitmapPaint;
    RelativeLayout Rl;
    CustomView View;
    List<Point> mStartPointsX = new ArrayList<Point>();
    List<Point> mStartPointsY = new ArrayList<Point>();
    List<Point> mEndPointsX = new ArrayList<Point>();
    List<Point> mEndPointsY = new ArrayList<Point>();

    protected void onCreate(Bundle savedInstances) {
        super.onCreate(savedInstances);
        setContentView(R.layout.activity_main);
        View = new CustomView(this);
        Rl = (RelativeLayout) findViewById(R.id.activity_main);
        Rl.addView(View);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.MAGENTA);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(20);

    }

    private Paint mPaint;

    public class CustomView extends View {

        @SuppressWarnings("deprecation")
        public CustomView(Context c) {

            super(c);
            Display Disp = getWindowManager().getDefaultDisplay();

//            ImageView imageView = (ImageView) findViewById(R.id.imageView1);

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
        }

        private float mX, mY;
        private static final float TOUCH_TOLERANCE = 4;

        private void touch_start(float x, float y) {
            mPath.reset();
            mPath.moveTo(x, y);
            mX = x;
            mY = y;
        }

        private void touch_move(float x, float y) {
            float dx = Math.abs(x - mX);
            float dy = Math.abs(y - mY);
            if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
                mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
                mX = x;
                mY = y;
            }
        }

        private void touch_up() {

            mPath.lineTo(mX, mY);

            mCanvas.drawPath(mPath, mPaint);

            mPath.reset();
        }

        private void clear_canvas() {
            mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {

            Coordinate coordinate = new Coordinate();
            float x = event.getX();
            float y = event.getY();

            mEndPointsX.add(new Point((int) coordinate.getEndX(), 0));
            mEndPointsY.add(new Point(0, (int) coordinate.getEndY()));

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    touch_start(x, y);
                    mStartPointsX.clear();
                    mStartPointsY.clear();
                    invalidate();
                    clear_canvas();
                    break;
                case MotionEvent.ACTION_MOVE:
                    touch_move(x, y);
                    mStartPointsX.add(new Point((int) mX, 0));
                    mStartPointsY.add(new Point(0, (int) mY));
                    invalidate();
                    break;
                case MotionEvent.ACTION_UP:
                    mEndPointsX.add(new Point((int) mX, 0));
                    mEndPointsY.add(new Point(0, (int) mY));
                    touch_up();

                    if (new Point(mStartPointsX.get(0).describeContents(), mStartPointsY.get(0).describeContents()) ==
                            new Point(mEndPointsX.get(mEndPointsX.size() - 1).describeContents(), mEndPointsY.get(mEndPointsY.size() - 1).describeContents())) {
                        //Return RGB value here of contents within circle.


                        //For testing purposes.
                        System.out.println("Full: " + mStartPointsX.get(0) + ", " + mStartPointsY + " X: " + (int) x + " Y: " + (int) y);

                    } else {
                        //Go to the startingPoints and then return RGB value here of contents within circle.

                        //mCanvas.drawLine(mStartPointsX.get(0).x, mStartPointsY.get(0).y, mEndPointsX.get(mEndPointsX.size() - 1).x, mEndPointsY.get(mEndPointsY.size() - 1).y, mPaint);

                        mPath.moveTo(mEndPointsX.get(mEndPointsX.size() - 1).x, mEndPointsY.get(mEndPointsY.size() - 1).y);
                        mPath.lineTo(mStartPointsX.get(0).x, mStartPointsY.get(0).y);

                        //For testing purposes.
                        System.out.println("--------------------");
                        System.out.println("Null (STARTING POINTS): X: " + mStartPointsX.get(0) + ", Y: " + mStartPointsY.get(0));
                        System.out.println("Null (END POINTS): X: " + mEndPointsX.get(mEndPointsX.size() - 1) + ", Y: " + mEndPointsY.get(mEndPointsX.size() - 1));

                    }

                    invalidate();
                    break;
            }
            return true;
        }

    }

    private class Coordinate {

        private float startX;
        private float endX;
        private float startY;
        private float endY;

        /**
         * @return the startX
         */
        public float getStartX() {
            return startX;
        }

        /**
         * @param startX the startX to set
         */
        public void setStartX(float startX) {
            this.startX = startX;
        }

        /**
         * @return the endX
         */
        public float getEndX() {
            return endX;
        }

        /**
         * @param endX the endX to set
         */
        public void setEndX(float endX) {
            this.endX = endX;
        }

        public float getStartY() {
            return startY;
        }

        /**
         * @param startX the startX to set
         */
        public void setStartY(float startX) {
            this.startY = startY;
        }

        /**
         * @return the endX
         */
        public float getEndY() {
            return endY;
        }

        public float setEndY(float endY) {
            return this.endY = endY;
        }
    }

}