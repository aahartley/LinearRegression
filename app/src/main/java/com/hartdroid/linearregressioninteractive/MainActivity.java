package com.hartdroid.linearregressioninteractive;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import com.google.android.material.bottomnavigation.BottomNavigationView;


import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView title = null;
    Button bt = null;
    Button bt2 = null;
    Button bt3=null;
    TextView tV =null;
    @SuppressLint("StaticFieldLeak")
    static TextView tV1=null;
    TextView tV4 =null;
    EditText et = null;
    List<Touch> nData = new ArrayList<>();
    GraphView view;

    int n =0;


    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        title = super.findViewById(R.id.title);
        bt = super.findViewById(R.id.bt);
        bt2 = super.findViewById(R.id.bt2);
        bt3 = super.findViewById(R.id.bt3);
        tV = super.findViewById(R.id.tV);
        tV1 = super.findViewById(R.id.tV1);
        tV4 = super.findViewById(R.id.tV4);
        et = super.findViewById(R.id.et);


        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);
        bottomNav.setItemBackgroundResource(android.R.color.darker_gray);

        bottomNav.setOnNavigationItemSelectedListener(item -> {
            switch(item.getItemId()){
                case R.id.touch:
                    Intent intent = new Intent(MainActivity.this,MainActivity.class);
                    startActivity(intent);
                    return true;
                case R.id.file:
                    Intent intent2 = new Intent(MainActivity.this,SecondActivity.class);
                    startActivity(intent2);
                    return true;

            }
            return false;
        });
        try {
            view= findViewById(R.id.graphView);


        } catch (Exception e) {
            e.printStackTrace();
        }
        //    }
//  }
        bt2.setOnClickListener(v -> {
            try  {
                InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            view.erase(false);
            bt.setEnabled(true);
        });
        bt.setOnClickListener(v -> {
            System.out.println("clicked");
            try  {
                InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            } catch (Exception e) {
                System.out.println(e.getMessage());

            }

            String input = et.getText().toString().trim();
            if(input.isEmpty()){
                et.setError("must match above");

            }
            else {
                double year = Double.parseDouble(input);
                year = year+view.getWidth()/2.0f;
                if(year<view.getWidth()&& year>0) {
                    Salary query = new Salary(year, 0.0);
                    Regression(query);
                }
                else{
                    et.setError("must match above");
                }
            }




        });
        bt3.setOnClickListener(v -> {
            view.erase(true);
            bt.setEnabled(false);

        });
    }



    protected void Regression(Salary query) {
        //y=a0+a1x
        double summationX = 0;
        double summationY = 0;
        double summationX2 = 0;
       // double summationY2 = 0;
        double summationXY = 0;
        nData = view.getTouches();
        n=nData.size();
        if (nData.size()!=0) {
            for (int i = 0; i < n; i++) {
                summationX += nData.get(i).x;
                summationY += nData.get(i).y;
                summationX2 += Math.pow(nData.get(i).x, 2);
              //  summationY2 += Math.pow(nData.get(i).y, 2);
                summationXY += nData.get(i).x * nData.get(i).y;
            }
            double averageX = summationX / n;
            double averageY = summationY / n;
            double b = ((n * summationXY) - (summationX * summationY)) / ((n * summationX2) - (Math.pow(summationX, 2)));
            double a = averageY - (b * averageX);

            double y = a + b * query.getYear();
            nData.sort(Comparator.comparingDouble(Touch::getX));
            double smallestX = 5;
            double largestX = view.getWidth();
            System.out.println("smallx" + smallestX + "largestx" + largestX);
            nData.sort(Comparator.comparingDouble(Touch::getY));
            double smallestY = a + b * smallestX;
            double largestY = a + b * largestX;
            tV4.setText(MessageFormat.format("{0} {1} ", getString(R.string.predicton), view.getHeight()/2.0f-y));

            view.setLine(query.getYear()-view.getWidth()/2.0f, y, smallestX, largestX, smallestY, largestY);
        }







    }

}