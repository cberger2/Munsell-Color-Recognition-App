package com.murach.invoice.imageselection;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements Serializable {
    private Bitmap DrawBitmap;
    private Canvas mCanvas;
    private Path mPath;
    private Paint DrawBitmapPaint;
    RelativeLayout Rl;
    ImageSelection View;
    List<Point> mStartPoints = new ArrayList<Point>();
    List<Point> mEndPoints = new ArrayList<Point>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View = new ImageSelection(this, DrawBitmap);
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

    public class ImageSelection extends View {

        @SuppressWarnings("deprecation")
        public ImageSelection(Context c, Bitmap b) {

            super(c);
            Display Disp = getWindowManager().getDefaultDisplay();

//            ImageView imageView = (ImageView) findViewById(R.id.imageView1);

            b = Bitmap.createBitmap(Disp.getWidth(), Disp.getHeight(),
                    Bitmap.Config.ARGB_4444);

            mCanvas = new Canvas(b);

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

            float x = event.getX();
            float y = event.getY();

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    touch_start(x, y);
                    mStartPoints.clear();
                    invalidate();
                    clear_canvas();
                    break;
                case MotionEvent.ACTION_MOVE:
                    touch_move(x, y);
                    mStartPoints.add(new Point((int) mX, (int) mY));
                    invalidate();
                    break;
                case MotionEvent.ACTION_UP:
                    mStartPoints.add(new Point((int) mX, (int) mY));
                    touch_up();
                    if (mStartPoints.get(0).equals((int)x, (int)y) ) {
                        //Return RGB value here of contents within circle.

                        //For testing purposes.
                        System.out.println("Full: " + mStartPoints.get(0) + " X: " + x + " Y: " + y);
                    } else {
                        mPath.lineTo((float) mStartPoints.get(0).describeContents(), (float) mStartPoints.get(0).describeContents());
                        //For testing purposes.
                        System.out.println("Null: " + mStartPoints.get(0) + " X: " + x + " Y: " + y);
                    }
                        invalidate();
                    break;
            }
            return true;
        }

    }

}