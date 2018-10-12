package com.example.smartpillownew.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by a450J on 2018/8/22.
 */

public class AbnormalECGVIew extends View {

    private static final String TAG = "ECGdemo";

    private int width;
    private float y_center;
    private int lastX;
    private int offsetX;
    private int[] datas = new int[]{};
    /**
     * 画多少个5s
     */
    private int draw_5s ;
    private Paint paint;
    private Path path;

    public AbnormalECGVIew(Context context) {
        super(context);
    }

    public AbnormalECGVIew(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    private void initViews() {
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2f);
        paint.setTextSize(35f);

        datas = new int[]{};
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawECG(canvas);
    }

    //一个屏幕的心电图
    private void drawECG(Canvas canvas){

        path = new Path();

        path.moveTo(0,y_center);
        int tmp = 0;
        int time = 0;
        //绘制每个单元
        for(int j=0 ; j<draw_5s ; j++){
            //计算每次次心率下三分之一个屏幕画多少个心跳单元
            int times = datas[j]*3/60;
            //每个心跳单元的长度
            int each_puls = width/(4*times);
            //每个5s的图形之间画间隔并标注时间
            canvas.drawLine(((j)*width)/4,y_center+200,((j)*width)/4,y_center-300,paint);
            canvas.drawText(String.valueOf(time)+"s",((j)*width)/4,y_center-350,paint);
            time+=5;
            //标注每个5s的心率为多少
            canvas.drawText(String.valueOf(datas[j])+"bpm",((j)*width)/4+width/8,y_center-350,paint);

            for(int i=0 ; i<times ; i++){
                path.lineTo(tmp+each_puls/12,y_center+50);
                path.lineTo(tmp+each_puls*5/12,y_center-200);
                path.lineTo(tmp+each_puls*10/12,y_center+100);
                path.lineTo(tmp+each_puls,y_center);
                tmp+=each_puls;
            }
        }
        canvas.drawPath(path,paint);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        y_center = h*3/5;
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

    public void getData(int[] list){
        this.datas = list;
        draw_5s = datas.length;
        invalidate();
    }
}
