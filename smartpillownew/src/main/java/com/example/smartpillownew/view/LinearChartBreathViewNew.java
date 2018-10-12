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
import android.view.View;


/**
 * Created by a450J on 2018/8/25.
 */

public class LinearChartBreathViewNew extends View{

    private static final String TAG = "LinearChartBreathViewNew";

    private Path path;
    private Paint paint;
    private Paint textPaint;

    private int width;
    private int heigh;

    private int mPeceiceY;

    private int marginX = 100;
    private int marginY = 100;

    private int maxX ;
    private int maxY ;

    private int mBaseX;
    private int mBaseY;

    private String[] time ;
    private String[] data ;


    public LinearChartBreathViewNew(Context context) {
        this(context, null);
    }

    public LinearChartBreathViewNew(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LinearChartBreathViewNew(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        paint = new Paint();
        textPaint = new Paint();
        time = new String[]{"","","","","","","",""};
        data = new String[]{"10","10","10","10","10","10","10"};
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        initSettings();
        initPaint();
        super.onLayout(changed, left, top, right, bottom);
    }

    private void initSettings() {
        width = getWidth();
        heigh = getHeight();
        maxX = width-marginX*2;
        maxY = heigh-marginY*2;
        mBaseX = maxX/7;
        mBaseY = maxY/4;
        mPeceiceY = mBaseY/10;
    }

    private void initPaint() {
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(10f);
        LinearGradient lg = new LinearGradient(marginX,maxY+marginY,marginX,marginY,
                new int[]{Color.YELLOW,Color.GREEN,Color.RED},
                null, Shader.TileMode.CLAMP);
        paint.setShader(lg);
        CornerPathEffect cornerPathEffect = new CornerPathEffect(15f);
        paint.setPathEffect(cornerPathEffect);

        textPaint.setStyle(Paint.Style.STROKE);
        textPaint.setColor(Color.GREEN);
        textPaint.setTextSize(40f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画x、y轴
        drawX(canvas);
        drawY(canvas);
        //画三条均值线
        drawNotifyLine("较低",10,Color.YELLOW,canvas);
        drawNotifyLine("正常",25,Color.GREEN,canvas);
        drawNotifyLine("较高",35,Color.RED,canvas);
        //画数据
        drawData(canvas);
    }

    private void drawData(Canvas canvas) {
        path = new Path();
        //移动到第一个点
        path.moveTo(marginX+mBaseX,marginY+maxY-mPeceiceY*(Integer.valueOf(data[0])));
        for(int i=1 ; i<7 ; i++){
           path.lineTo(marginX+mBaseX*(i+1),marginY+maxY-mPeceiceY*(Integer.valueOf(data[i])));
        }
        for(int i=0 ; i<7 ; i++){
            canvas.drawText(data[i],marginX+mBaseX*(i+1)-20,marginY+maxY-mPeceiceY*(Integer.valueOf(data[i]))-30,textPaint);
        }
        canvas.drawPath(path,paint);
    }

    private void drawNotifyLine(String content, int heigh, int color, Canvas canvas) {
        Paint mPaint = new Paint();
        mPaint.setColor(color);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setTextSize(35f);

        canvas.drawLine(marginX,marginY+maxY-mPeceiceY*(heigh),marginX+maxX,marginY+maxY-mPeceiceY*heigh,mPaint);
        canvas.drawText(content,maxX+marginY-20,marginY+maxY-mPeceiceY*(heigh)-20,mPaint);
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
        for(int i=1 ; i<5 ; i++){
            canvas.drawCircle(marginX,marginY+maxY-i*mBaseY,10f,mPaint);
            canvas.drawText(String.valueOf(i*10),10,marginY+maxY-i*mBaseY,mPaint);
        }
    }

    /**画x轴
     * @param canvas
     */
    private void drawX(Canvas canvas) {
        Paint mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.WHITE);
        mPaint.setTextSize(40f);

        for(int i=0 ; i<8 ; i++){

            canvas.drawCircle(marginX+mBaseX*i,marginY+maxY,10,mPaint);
            canvas.drawText(time[i],marginX+mBaseX*i-30,marginY+maxY+40,mPaint);
        }
        canvas.drawLine(marginX,marginY+maxY,marginX+maxX,marginY+maxY,mPaint);
    }

    //暴露接口，刷新数据
    public void getData(String[] dataList,String timeList[]){
        this.data = dataList;
        this.time = timeList;
        invalidate();
    }

}
