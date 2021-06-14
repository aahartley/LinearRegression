package com.hartdroid.linearregressioninteractive;




import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class GraphView extends View {
    Paint paint = new Paint();
    Paint paint2= new Paint();
    boolean erase=false;
    boolean drawSlope=false;
    List<Salary> dataset = new ArrayList<>();
    double smallestX=0;
    double largestX=0;
    double smallestY=0;
    double largestY=0;
    double x=0;
    double y=0;
    boolean touch=false;
    List<Touch> touches = new ArrayList<>();
    List<Float> xs = new ArrayList<>();
    List<Float> ys = new ArrayList<>();





    public GraphView(Context context) {
        super(context);
    }

    public GraphView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GraphView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            Touch t = new Touch(event.getX(), event.getY());
            System.out.println(t.x + " " + t.y);
            xs.add(event.getX());
            ys.add(event.getY());
            touches.add(t);
            touch = true;
            MainActivity.tV1.setText(MessageFormat.format("Number of dots: {0}\nX: {1} Y: {2}", xs.size(), event.getX() - getWidth() / 2.0f, getHeight() / 2.0f - event.getY()));
        }
        invalidate();
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (erase){
            canvas.drawColor(Color.WHITE);
            dataset.clear();
            touches.clear();
            xs.clear();
            ys.clear();
            drawSlope=false;
            smallestX=0;
            smallestY=0;
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
        }
        else {
            //canvas.drawColor(Color.WHITE);
            paint.setColor(Color.BLUE);
            paint.setStrokeWidth(5.0f);
            paint2.setColor(Color.RED);
            paint2.setStrokeWidth(4.0f);

            if (smallestX != 0 && smallestY != 0 &&drawSlope) {
                System.out.println("1 "+(getWidth()/2.0f+smallestX*20)+" 2 "+(getWidth()/2.0f+largestX*20));
                System.out.println(getWidth());
                //first working slope
                canvas.drawLine((float)(smallestX), (float) (smallestY),(float)(largestX), (float)(largestY), paint2);

                canvas.drawCircle((float) (getWidth() / 2.0f + x ), (float) ( y), 10.0f, paint2);

                //first working pred
                //  canvas.drawCircle((float) ( x ), (float) (y), 10.0f, paint2);

            }
            if(touches!=null){
                for(Touch t: touches) {
                    canvas.drawCircle((float)t.x, (float) t.y, 10.0f, paint);
                }
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



            // System.out.println(getWidth() + " " + getHeight());
        }
    }

    protected void setLine(double x, double y, double smallestX, double largestX, double smallestY, double largestY){
        this.smallestX=smallestX;
        this.largestX=largestX;
        this.smallestY=smallestY;
        this.largestY=largestY;
        this.x=x;
        this.y=y;
        this.drawSlope = true;
        invalidate();
    }
    protected void erase(boolean erase){
        this.erase = erase;
        invalidate();
    }
    protected  List<Touch> getTouches(){
        return touches;
    }





}
class Touch {
    double x;
    double y;
    public Touch(double x, double y){
        this.x=x;
        this.y=y;
    }
    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }

}
