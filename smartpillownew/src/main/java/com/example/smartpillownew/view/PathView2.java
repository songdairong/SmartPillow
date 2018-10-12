package com.example.smartpillownew.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;

import java.util.Random;

/**
 * Created by a450J on 2018/8/13.
 */

public class PathView2 extends CardiographView {

    private static final String TAG = "PathView2";

    private int[] dataList;
    private int[] rangeList;
    private Random random;

    public PathView2(Context context) {
        super(context);
    }

    public PathView2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PathView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBreath(canvas);
    }

    /**
     * 画呼吸图
     *
     * @param canvas
     */
    private void drawBreath(Canvas canvas) {
        mPath.reset();

        mPath.moveTo(mWidth, mHeight * 3 / 4);

        boolean flag = false;

        if (dataList!=null && dataList.length>0){
            for (int i=0;i<dataList.length;i++){
                if (dataList[i] > 1){
                    mPath.rQuadTo(-100,-rangeList[i],-200,0);
                }else if (dataList[i] == 1){
                    if (i%2==0 && !flag){
                        flag = !flag;
                        mPath.rQuadTo(-25,20,-50,0);
                        mPath.rQuadTo(-25,-20,-50,0);
                        mPath.rQuadTo(-50,30,-100,0);
                    }else {
                        flag = !flag;
                        mPath.rQuadTo(-35,-20,-50,0);
                        mPath.rQuadTo(-35,20,-50,0);
                        mPath.rQuadTo(-40,-30,-100,0);
                    }

                } else {
                    mPath.rQuadTo(-100,0,-200,0);
                }
            }
        }

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(mLineColor);
        mPaint.setStrokeWidth(5);
        canvas.drawPath(mPath, mPaint);
    }

    public void getData(int[] tmp, int[] range) {
        dataList = new int[]{0, 0, 0, 0, 0, 0};
        rangeList = new int[]{0, 0, 0, 0, 0, 0};
        for (int i = 0; i < tmp.length; i++) {
            dataList[i] = tmp[i];
            rangeList[i] = range[i];
        }
    }
}
