package com.ydh.yudemo.selfview.finger;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.ydh.yudemo.R;

/**
 * Created by Android on 2018/6/17.
 */

public class DrawingView extends View {
    public int width;

    public int height;

    private Bitmap mBitmap;

    private Canvas mCanvas;

    private Path mPath;

    private Paint mBitmapPaint;

    Context mContext;

    private Paint circlePaint;

    private Path circlePath;

    private Paint mPaint;

    private int circleColor;
    private float circleWidth;
    private int pathColor;
    private float pathWidth;

    public DrawingView(Context context) {
        super(context);
        mContext = context;
    }

    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init(attrs);
    }


    @Override

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {

        super.onSizeChanged(w, h, oldw, oldh);


        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);

        mCanvas = new Canvas(mBitmap);


    }

    @Override

    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);


        canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);


        canvas.drawPath(mPath, mPaint);


        canvas.drawPath(circlePath, circlePaint);

    }

    private void init(AttributeSet attrs) {

        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.DrawingView);
        circleColor = typedArray.getColor(R.styleable.DrawingView_dv_circle_color, getResources().getColor(R.color.red_txt));
        circleWidth=typedArray.getDimension(R.styleable.DrawingView_dv_circle_width,4);
        pathColor = typedArray.getColor(R.styleable.DrawingView_dv_path_color, getResources().getColor(R.color.red_txt));
        pathWidth=typedArray.getDimension(R.styleable.DrawingView_dv_path_width,12);

        mPath = new Path();

        mBitmapPaint = new Paint(Paint.DITHER_FLAG);

        circlePaint = new Paint();

        circlePath = new Path();

        circlePaint.setAntiAlias(true);

        circlePaint.setColor(circleColor);

        circlePaint.setStyle(Paint.Style.STROKE);

        circlePaint.setStrokeJoin(Paint.Join.MITER);

        circlePaint.setStrokeWidth(circleWidth);

        mPaint = new Paint();

        mPaint.setAntiAlias(true);

        mPaint.setDither(true);

        mPaint.setColor(pathColor);

        mPaint.setStyle(Paint.Style.STROKE);

        mPaint.setStrokeJoin(Paint.Join.ROUND);

        mPaint.setStrokeCap(Paint.Cap.ROUND);

        mPaint.setStrokeWidth(pathWidth);
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
            circlePath.reset();

            circlePath.addCircle(mX, mY, 30, Path.Direction.CW);

        }

    }

    private void touch_up() {

        mPath.lineTo(mX, mY);
        circlePath.reset();
        mCanvas.drawPath(mPath, mPaint);
        mPath.reset();

    }


    @Override

    public boolean onTouchEvent(MotionEvent event) {

        float x = event.getX();

        float y = event.getY();


        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:

                touch_start(x, y);

                invalidate();

                break;

            case MotionEvent.ACTION_MOVE:

                touch_move(x, y);

                invalidate();

                break;

            case MotionEvent.ACTION_UP:

                touch_up();

                invalidate();

                break;

        }

        return true;

    }

}
