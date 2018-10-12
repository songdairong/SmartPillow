package com.example.smartpillownew.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.smartpillownew.R;

import java.util.ArrayList;

/**
 * Created by a450J on 2018/8/22.
 */

public class GrideDemo extends View {

    private static final String TAG = "ECGdemo";

    private float gap_grid;//网格间距
    private int width,height;//本页面宽，高
    private int xori;//原点x坐标
    private int grid_hori,grid_ver;//横、纵线条数
    private float gap_x;//两点间横坐标间距
    private int dataNum_per_grid = 18;//每小格内的数据个数
    private float y_center;//中心y值
    private ArrayList<String> data_source;

    private float x_change ;//滑动查看时，x坐标的变化
    private static float x_changed ;
    private static float startX;//手指touch屏幕时候的x坐标
    private int data_num;//总的数据个数
    private float offset_x_max;//x轴最大偏移量

    private int rect_high = 80;//下方用于显示心电图形的矩形区域的高
    private float rect_width;//下方矩形框的宽度
    private float rect_gap_x;//下方矩形区域心电图数据间的横坐标间隙
    private float multiple_for_rect_width;//矩形区域的宽与屏幕宽的比
    private int lastX;

    public GrideDemo(Context context) {
        this(context, null);
        this.setBackgroundColor(getResources().getColor(R.color.colorBlack));
    }

    public GrideDemo(Context context, AttributeSet attrs) {
        super(context,attrs);
        this.setBackgroundColor(getResources().getColor(R.color.colorBlack));
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if (changed){
            xori = 0;
            gap_grid = 30.0f;
            width = getWidth();
            height = getHeight();
            grid_hori = height/(int)gap_grid;
            grid_ver = width/(int)gap_grid;
            y_center = (height - rect_high)/2;
            gap_x = gap_grid/dataNum_per_grid;
//            data_num = data_source.size();
            data_num = 72;
            x_change = 0.0f ;
            x_changed = 0.0f ;
            offset_x_max = width - gap_x * data_num;
            rect_gap_x = (float) width/data_num;
            rect_width = (float) width * width/(gap_x * data_num);
            multiple_for_rect_width = (float) width/rect_width;
            Log.e("json","本页面宽： " + width +"  高:" + height);
//            Log.e("json","两点间横坐标间距:" + gap_x + "   矩形区域两点间横坐标间距：" + rect_gap_x);
        }
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        DrawGrid(canvas);

    }

    /**
     * 画背景网格
     */
    private void DrawGrid(Canvas canvas){
        Log.i(TAG, "DrawGrid: "+grid_hori+grid_ver);
        //横线
        for (int i = 1 ; i < grid_hori + 2 ; i ++){
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(getResources().getColor(R.color.limegreen)); //<color name="data_pr">#0a7b14</color>
            paint.setStrokeWidth(1.0f);
            Path path = new Path();
            path.moveTo(xori, gap_grid * (i-1) + (height-grid_hori*gap_grid)/2);
            path.lineTo(width,gap_grid * (i-1) + (height-grid_hori*gap_grid)/2);
            if ( i % 5 != 0 ){//每第五条，为实线   其余为虚线 ，以下为画虚线方法
                PathEffect effect = new DashPathEffect(new float[]{1,5},1);
                paint.setPathEffect(effect);
            }
            canvas.drawPath(path,paint);
        }
        //竖线
        for (int i = 1 ; i < grid_ver + 2 ; i ++){
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(getResources().getColor(R.color.limegreen));
            paint.setStrokeWidth(1.0f);
            Path path = new Path();
            path.moveTo(gap_grid * (i-1) + (width-grid_ver*gap_grid)/2, 0);
            path.lineTo(gap_grid * (i-1) + (width-grid_ver*gap_grid)/2,height);
            if ( i % 5 != 0 ){
                PathEffect effect = new DashPathEffect(new float[]{1,5},1);
                paint.setPathEffect(effect);
            }
            canvas.drawPath(path,paint);
        }
    }

}
