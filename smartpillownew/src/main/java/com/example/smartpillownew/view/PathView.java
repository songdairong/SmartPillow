package com.example.smartpillownew.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;

import java.util.Random;

/**
 * Created by a450J on 2018/8/13.
 */

public class PathView extends CardiographView {

    private static final String TAG = "PathView";

    private int[] dataList;
    private int[] rangeList;

    public PathView(Context context) {
        super(context);
    }

    public PathView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PathView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setData(int[] data , int[] range) {
        dataList = new int[data.length];
        rangeList = new int[data.length];
        for (int i = 0; i < data.length; i++) {
            dataList[i] = data[i];
            rangeList[i] = range[i];
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i(TAG, "onDraw: ");
        drawPath(canvas);
//        scrollBy(1,0);
//        postInvalidateDelayed(10);
    }

    private void drawPath(Canvas canvas) {

        Log.i(TAG, "drawPath: ");

        // 重置path
        mPath.reset();

        int high = mHeight * 3 / 4;

//        int[] heart = {10,20,30,40,50,60,70,80,90,100};

        //用path模拟一个心电图样式
        mPath.moveTo(mWidth, high);
        int tmp = mWidth;

        if (dataList != null && dataList.length>0){
            for (int i=0;i<dataList.length;i++){
                if (dataList[i] > 1){
                    //原始心电图
//                    mPath.lineTo(tmp-20, high-rangeList[i]);
//                    mPath.lineTo(tmp-70, high + 50);
//                    mPath.lineTo(tmp-80, high);
//                    mPath.lineTo(tmp-200, high);
                    //第一次修改心电图
//                    mPath.lineTo(tmp-5,high-20);
//                    mPath.lineTo(tmp-10,high);
//
//                    mPath.lineTo(tmp-20,high-50);
//                    mPath.lineTo(tmp-30,high);
//
//                    mPath.lineTo(tmp-50,high);
//
//                    mPath.lineTo(tmp-75,high-rangeList[i]);
//                    mPath.lineTo(tmp-105,high+20);
//                    mPath.lineTo(tmp-110,high);
//
//                    mPath.lineTo(tmp-200,high);

                    //第二次修改心电图
                    mPath.lineTo(tmp-10,high+60);
                    mPath.lineTo(tmp-30,high-rangeList[i]/5);
                    mPath.lineTo(tmp-40,high);

                    mPath.lineTo(tmp-60,high);

                    mPath.lineTo(tmp-65,high+50);
                    mPath.lineTo(tmp-100,high-rangeList[i]);
                    mPath.lineTo(tmp-140,high+50);
                    mPath.lineTo(tmp-150,high);

                    mPath.lineTo(tmp-300,high);



                    tmp = tmp-300;
                }else if (dataList[i] == 1){
                    //绘制杂乱曲线
                    mPath.lineTo(tmp-10,high+50);
                    mPath.lineTo(tmp-25,high-30);
                    mPath.lineTo(tmp-30,high);

                    mPath.rQuadTo(-50,50,-100,0);
                    mPath.rQuadTo(-30,-30,-60,0);

                    mPath.lineTo(tmp-300,high);

                    tmp-=300;
                }else {
                    mPath.lineTo(tmp-300,high);
                    tmp-=300;
                }
            }
        }

        //添加转角圆滑
        CornerPathEffect cornerPathEffect = new CornerPathEffect(90f);
        mPaint.setPathEffect(cornerPathEffect);

        //设置画笔style
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(mLineColor);
        mPaint.setStrokeWidth(5);
        canvas.drawPath(mPath, mPaint);
    }

}
