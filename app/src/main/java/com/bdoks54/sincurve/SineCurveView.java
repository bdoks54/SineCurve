package com.bdoks54.sincurve;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;

public class SineCurveView extends View {
    private int verticalCenter;
    private int verticalOffset;
    private int period;
    private float dayUnit;
    private int height;
    private int horizontalCenter;
    public SineCurveView(Context context) {
        super(context);
    }

    public void onDraw(Canvas canvas){
        super.onDraw(canvas);
        drawAxis(canvas); //축그리기
        makeGraph(canvas); //싸인곡선그리기
    }

    private void drawAxis(Canvas canvas){
        setBackgroundColor(Color.BLACK);
        Paint paint = new Paint();
        Path path = new Path();
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true); // 부드럽게 연결
        paint.setStyle(Paint.Style.STROKE); // 실선
        verticalOffset =30;             // 위에서 30px 떨어지게
        height = getHeight() - verticalOffset; // 화면 전체높이 -30
        height /= 3;                    //그래프 높이, 화면의 3분의1
        verticalCenter = height/2;          //그래프 높이의 중앙
        period = getWidth();                //그래프 넓이
        dayUnit = period/12;                //12등분, 30도 단위로 구분
        horizontalCenter = period/2;        //그래프 넓이 중앙

        path.moveTo(0, verticalOffset); //선의 시작점
        path.lineTo(period, verticalOffset); //선의 끝점
        canvas.drawPath(path,paint);        //선 그리기

        path.moveTo(0, verticalCenter+verticalOffset);
        path.lineTo(period, verticalCenter+verticalOffset);
        canvas.drawPath(path, paint);

        path.moveTo(0, height+verticalOffset);
        path.lineTo(period, height+verticalOffset);
        canvas.drawPath(path,paint);

        for(int i=0; i<=period; i+= dayUnit){
            path.moveTo(i, verticalOffset);
            path.lineTo(i, height+verticalOffset);
        }

        canvas.drawPath(path,paint);

    }

    private double getBioRhythmValue(float n){
        return verticalCenter*Math.sin(Math.toRadians(n));
    }

    public void makeGraph(Canvas canvas){
        Path path = new Path();
        Paint paint = new Paint();
        paint.setAntiAlias(true); // 부드럽게 연결
        paint.setStrokeWidth(3); //선의 두께
        paint.setStyle(Paint.Style.STROKE); //실선
        paint.setColor(Color.RED);
        //x값에 대한 싸인곡선 그리기
        path.moveTo(0, -(float)getBioRhythmValue(0)+verticalCenter +verticalOffset);
        for(int i=0;i <=360; i++){
            path.lineTo(i*(dayUnit/30.0f),
                    -(float)getBioRhythmValue(i)+verticalCenter+verticalOffset);
        }
        canvas.drawPath(path,paint);
    }
}
