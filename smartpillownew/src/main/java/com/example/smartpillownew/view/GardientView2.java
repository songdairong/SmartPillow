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
import android.util.Log;
import android.view.View;

import com.example.smartpillownew.R;

/**
 * Created by a450J on 2018/8/20.
 */

public class GardientView2 extends View {

    private static final String TAG = "GardientView";

    private Path path;
    private Paint paint;
    private int width;
    private int heigh;

    private int mBaseX = 120;
    private int mBaseY = 120;

    private int mPeceiceX = 60;
    private int mPeceiceY = 60;

    private int marginX = 80;
    private int marginY = 100;

    private int maxX = 840;
    private int maxY = 720;

    private String[] time = {"","周一","周二","周三","周四","周五","周六","周日"};
    private int[] yFloat1 = new int[8];//需要7个数据,取值为0~11
    private int[] yFloat2 = new int[15];//需要15个数据,取值为0~15

    public void getData(int[] data ){
        for(int i = 0; i< yFloat1.length ; i++){
            yFloat1[i] = data[i];
        }
    }

    public GardientView2(Context context) {
        this(context, null);
    }

    public GardientView2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GardientView2(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        //初始化数据的paint
        initPaint();
    }

    private void initPaint() {
        paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(30f);
        paint.setStyle(Paint.Style.STROKE);
        LinearGradient lg = new LinearGradient(marginX,maxY+mBaseY,marginX,marginY,
                new int[]{Color.YELLOW,Color.YELLOW},
                null, Shader.TileMode.CLAMP);
//        LinearGradient lg = new LinearGradient(0,0,getMeasuredWidth(),0,)
//        LinearGradient lg = new LinearGradient(0,0,getMeasuredWidth(),getMeasuredHeight(),Color.RED,Color.BLACK, Shader.TileMode.CLAMP);
        paint.setShader(lg);
        //添加转角圆滑
        CornerPathEffect cornerPathEffect = new CornerPathEffect(90f);
        paint.setPathEffect(cornerPathEffect);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Log.i(TAG, "onDraw: ");

        //画x轴和y轴
        drawXline(canvas);
        drawYline(canvas);
        //画数据
        drawLinear(canvas);
        //画均值
        drawrev(canvas);
    }

    private void drawrev(Canvas canvas) {
        float ave = 0;
        for (int a: yFloat1){
            ave+=a;
        }

        double data1 = ave/yFloat1.length;
        ave = ave/yFloat1.length;
        Log.i(TAG, "drawrev: data1 "+data1);

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.WHITE);
        paint.setTextSize(35f);

        String str = String.valueOf(data1);
        if (str.length()>4){
            str = str.substring(0,4);
        }

        canvas.drawLine(marginX,marginY+maxY-mPeceiceY*ave,marginX+maxX,marginY+maxY-mPeceiceY*ave,paint);
        canvas.drawText(str,marginX+maxX-30,marginY+maxY-mPeceiceY*ave-30,paint);

    }

    private void drawLinear(Canvas canvas) {
        for(int i=1 ; i<8 ; i++){
            canvas.drawLine(marginX+mBaseX*i,marginY+maxY,marginX+mBaseX*i,marginY+maxY-yFloat1[i]*mPeceiceY,paint);
        }
    }

    //画y轴，包括上面的点
    private void drawYline(Canvas canvas) {

        Paint mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.WHITE);
        mPaint.setTextSize(35f);
//        mPaint.setStrokeWidth(2f);

        canvas.drawLine(marginX,marginY,marginX,marginY+maxY,mPaint);
        for(int i=0 ; i<7 ; i++){
            canvas.drawCircle(marginX,marginY+i*mBaseY-10,10f,mPaint);
            canvas.drawText(String.valueOf((6-i)*2),10,marginY+mBaseY*i,mPaint);
        }
        canvas.drawText("睡眠时间/小时",10,marginY-50,mPaint);
    }

    //画x轴，包括上面的点
    private void drawXline(Canvas canvas) {

        Paint mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.WHITE);
        mPaint.setTextSize(30f);
//        mPaint.setStrokeWidth(2f);

        for(int i=1 ; i<8 ; i++){
            canvas.drawCircle(marginX+mBaseX*i,marginY+maxY,10,mPaint);
            canvas.drawText(time[i],marginX+mBaseX*i-30,marginY+maxY+40,mPaint);
        }
        canvas.drawLine(marginX,marginY+maxY,marginX+maxX,marginY+maxY,mPaint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        heigh = h;
    }
}
