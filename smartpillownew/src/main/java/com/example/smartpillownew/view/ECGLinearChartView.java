package com.example.smartpillownew.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by a450J on 2018/9/13.
 */

public class ECGLinearChartView extends View{
    private String[] time;
    private String[] data;
    private Paint paint;
    private Paint textpaint;
    private Path path;

    private int width;
    private int heigh;

    private int marginX = 100;
    private int marginY = 100;

    private int mBaseX;//每个x的距离
    private int mBaseY;

    private int picesY;//y轴每个1的长度
    private int maxY;

    private int lastX;
    private int offsetX;


    public ECGLinearChartView(Context context) {
        this(context, null);
    }

    public ECGLinearChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ECGLinearChartView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        paint = new Paint();
        textpaint = new Paint();
        time = new String[]{"16:34:31","16:34:31","16:34:31","16:34:31","16:34:31","16:34:31","16:34:31","16:34:31"};
        data = new String[]{"10","10","10","10","10","10","10"};
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        initSet();
        initPaint();
        super.onLayout(changed, left, top, right, bottom);
    }

    private void initSet() {
        width = getWidth();
        heigh = getHeight();

        maxY = heigh-marginY*2;
        mBaseX = (width-marginX)/9;
        mBaseY = maxY/9;
        picesY = mBaseY/20;
    }

    private void initPaint() {
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(10f);
        LinearGradient lg = new LinearGradient(marginX,maxY+marginY,marginX,marginY,
                new int[]{Color.YELLOW,Color.GREEN,Color.RED},
                new float[]{0.25f,0.5f,0.75f}, Shader.TileMode.CLAMP);
        paint.setShader(lg);
        CornerPathEffect cornerPathEffect = new CornerPathEffect(15f);
        paint.setPathEffect(cornerPathEffect);

        textpaint.setStyle(Paint.Style.STROKE);
        textpaint.setColor(Color.GREEN);
        textpaint.setTextSize(40f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //画x、y轴
        drawY(canvas);
        drawX(canvas);

        drawPath(canvas);
    }

    /**画x轴
     * @param canvas
     */
    private void drawX(Canvas canvas) {
        Paint mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.WHITE);
        mPaint.setTextSize(40f);


        for(int i=0 ; i<data.length ; i++){
            canvas.drawCircle(marginX+mBaseX*i,marginY+maxY,10,mPaint);
            canvas.drawText(time[i],marginX+mBaseX*i-60,marginY+maxY+40,mPaint);
        }

    }

    /**画y轴
     * @param canvas
     */
    private void drawY(Canvas canvas) {
        Paint mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.WHITE);
        mPaint.setTextSize(35f);

        canvas.drawLine(marginX,marginY,marginX,marginY+maxY,mPaint);
        for(int i=1 ; i<9 ; i++){
            canvas.drawCircle(marginX,marginY+maxY-i*mBaseY,10f,mPaint);
            canvas.drawText(String.valueOf(i*20),10,marginY+maxY-i*mBaseY,mPaint);
        }
    }

    private void drawPath(Canvas canvas) {
        path = new Path();

        //画线
        path.moveTo(marginX,marginY+maxY-picesY*(Integer.valueOf(data[0])));
        for(int i=1 ; i<data.length ; i++){
            path.lineTo(marginX+mBaseX*i,marginY+maxY-picesY*(Integer.valueOf(data[i])));
        }
        //画数据
        for(int i=0 ; i<data.length ; i++){
            canvas.drawText(data[i],marginX+mBaseX*i-20,marginY+maxY-picesY*(Integer.valueOf(data[i]))-30,textpaint);
        }

        canvas.drawPath(path,paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                break;
            case MotionEvent.ACTION_MOVE:
                offsetX = x - lastX;
                scrollBy(-offsetX/36,0);
                break;
        }

        return true;
    }

    //暴露接口，刷新数据
    public void getData(String[] dataList,String timeList[]){
        this.data = dataList;
        this.time = timeList;
        invalidate();
    }
}
