package com.hartdroid.linearregressioninteractive;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.method.Touch;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class SecondGraphView extends View {
    Paint paint = new Paint();
    Paint paint2 = new Paint();
    boolean erase = false;
    boolean drawSlope = false;
    List<Salary> dataset = new ArrayList<>();
    double smallestX = 0;
    double largestX = 0;
    double smallestY = 0;
    double largestY = 0;
    double x = 0;
    double y = 0;

    List<Touch> touches = new ArrayList<>();


    public SecondGraphView(Context context) {
        super(context);
    }

    public SecondGraphView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SecondGraphView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }




    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (erase) {
            canvas.drawColor(Color.WHITE);
            dataset.clear();
            touches.clear();
            drawSlope = false;
            smallestX = 0;
            smallestY = 0;
            canvas.drawLine(0, getHeight() / 2.0f, getWidth(), getHeight() / 2.0f, paint);
            canvas.drawLine(0,getHeight()/2.0f,25,getHeight()/2.0f+25,paint);
            canvas.drawLine(0,getHeight()/2.0f,25,getHeight()/2.0f-25,paint);

            canvas.drawLine(getWidth(),getHeight()/2.0f,getWidth()-25,getHeight()/2.0f+25,paint);
            canvas.drawLine(getWidth(),getHeight()/2.0f,getWidth()-25,getHeight()/2.0f-25,paint);

            canvas.drawLine(getWidth() / 2.0f, 0, getWidth() / 2.0f, getHeight(), paint);
            canvas.drawLine(getWidth()/2.0f-25,25,getWidth()/2.0f,0,paint);
            canvas.drawLine(getWidth()/2.0f+25,25,getWidth()/2.0f,0,paint);
            canvas.drawLine(getWidth()/2.0f-25,getHeight()-25,getWidth()/2.0f,getHeight(),paint);
            canvas.drawLine(getWidth()/2.0f+25,getHeight()-25,getWidth()/2.0f,getHeight(),paint);
            paint2.setTextSize(50.0f);
            canvas.drawText("X",getWidth()-30,getHeight()/2.0f-30,paint2);
            canvas.drawText("Y",getWidth()/2.0f+30,30,paint2);
            erase=false;
        } else {
            canvas.drawColor(Color.WHITE);
            paint.setColor(Color.BLUE);
            paint.setStrokeWidth(5.0f);
            paint2.setColor(Color.RED);
            paint2.setStrokeWidth(4.0f);
            for (Salary s : dataset) {
                System.out.println("drawing dots" + s.toString());
                System.out.println((getWidth() / 2.0f + s.getYear() * 20));
                canvas.drawCircle((float) (getWidth() / 2.0f + s.getYear() * 20), (float) (getHeight() / 2.0f - s.getSalary() * 20), 10.0f, paint2);
            }
            if (smallestX != 0 && smallestY != 0 && drawSlope) {
                System.out.println("1 " + (getWidth() / 2.0f + smallestX * 20) + " 2 " + (getWidth() / 2.0f + largestX * 20));
                System.out.println(getWidth());
                canvas.drawLine((float) (getWidth() / 2.0f + smallestX * 20), (float) (getHeight() / 2.0f - smallestY * 20), (float) (getWidth() / 2.0f + largestX * 20), (float) (getHeight() / 2.0f - largestY * 20), paint2);


                canvas.drawCircle((float) (getWidth() / 2.0f + x * 20), (float) (getHeight() / 2.0f - y * 20), 10.0f, paint);

            }

            canvas.drawLine(0, getHeight() / 2.0f, getWidth(), getHeight() / 2.0f, paint);
            canvas.drawLine(0,getHeight()/2.0f,25,getHeight()/2.0f+25,paint);
            canvas.drawLine(0,getHeight()/2.0f,25,getHeight()/2.0f-25,paint);

            canvas.drawLine(getWidth(),getHeight()/2.0f,getWidth()-25,getHeight()/2.0f+25,paint);
            canvas.drawLine(getWidth(),getHeight()/2.0f,getWidth()-25,getHeight()/2.0f-25,paint);

            canvas.drawLine(getWidth() / 2.0f, 0, getWidth() / 2.0f, getHeight(), paint);
            canvas.drawLine(getWidth()/2.0f-25,25,getWidth()/2.0f,0,paint);
            canvas.drawLine(getWidth()/2.0f+25,25,getWidth()/2.0f,0,paint);
            canvas.drawLine(getWidth()/2.0f-25,getHeight()-25,getWidth()/2.0f,getHeight(),paint);
            canvas.drawLine(getWidth()/2.0f+25,getHeight()-25,getWidth()/2.0f,getHeight(),paint);
            paint2.setTextSize(50.0f);
            canvas.drawText("X",getWidth()-30,getHeight()/2.0f-30,paint2);
            canvas.drawText("Y",getWidth()/2.0f+30,30,paint2);
        }
    }

    protected void setList(List<Salary> dataset) {
        this.dataset = dataset;
        invalidate();

    }

    protected void setLine(double x, double y, double smallestX, double largestX, double smallestY, double largestY) {
        this.smallestX = smallestX;
        this.largestX = largestX;
        this.smallestY = smallestY;
        this.largestY = largestY;
        this.x = x;
        this.y = y;
        this.drawSlope = true;
        invalidate();
    }

    protected void erase(boolean erase) {
        this.erase = erase;
        invalidate();
    }
}
