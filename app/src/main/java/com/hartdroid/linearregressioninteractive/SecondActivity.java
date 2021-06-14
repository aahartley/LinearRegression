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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SecondActivity extends AppCompatActivity {
    TextView title = null;
    Button bt = null;
    Button bt2 = null;
    Button bt3=null;
    TextView tV =null;
    TextView tV4 =null;
    EditText et = null;
    EditText et2 =null;
    List<Salary> dataset = new ArrayList<>();
    List<Salary> nData = new ArrayList<>();
    SecondGraphView view;

    int n =0;
    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        title = super.findViewById(R.id.title);
        bt = super.findViewById(R.id.bt);
        bt2 = super.findViewById(R.id.bt2);
        bt3 = super.findViewById(R.id.bt3);
        tV = super.findViewById(R.id.tV);
        tV4 = super.findViewById(R.id.tV4);
        et = super.findViewById(R.id.et);
        et2 = super.findViewById(R.id.et2);

        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);
        bottomNav.setItemBackgroundResource(android.R.color.darker_gray);
        bottomNav.setOnNavigationItemSelectedListener(item -> {
            switch(item.getItemId()){
                case R.id.touch:
                    Intent intent = new Intent(SecondActivity.this,MainActivity.class);
                    startActivity(intent);
                    return true;
                case R.id.file:
                    Intent intent2 = new Intent(SecondActivity.this,SecondActivity.class);
                    startActivity(intent2);
                    return true;

            }
            return false;
        });




        try {
            readFile();
            view= findViewById(R.id.secondGraphView);


        } catch (IOException e) {
            e.printStackTrace();
        }
        bt2.setOnClickListener(v -> {
            try  {
                InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            } catch (Exception e) {
                System.out.println(e.getMessage());

            }
            String input = et2.getText().toString().trim();
            if(input.isEmpty()){
                et.setError("must match above");

            }
            else {
                nData.clear();
                bt.setEnabled(true);
                n = Integer.parseInt(input);
                if (n > dataset.size()) {
                    et.setError("n is too big");
                } else {
                    Collections.shuffle(dataset);
                    for (int i = 0; i < n; i++) {
                        nData.add(dataset.get(i));
                    }
                    view.setList(nData);
                    view.erase(false);
                }
            }

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


                Salary query = new Salary(year, 0.0);
                Regression(query);
            }




        });
        bt3.setOnClickListener(v -> {
            view.erase(true);
            bt.setEnabled(false);

        });
    }


    protected void readFile() throws IOException {
        InputStream is = getResources().openRawResource(R.raw.sal);
        BufferedReader buf = new BufferedReader(new InputStreamReader(is));
        String line;
        while((line =buf.readLine())!=null){
            if(line.contains("YearsExperience")){
                System.out.println(line +"avoid");
            }
            else{
                System.out.println(line);
                String[] tokens = line.split(",");
                Double[] attr = new Double[2];
                for(int i=0; i<tokens.length;i++){
                    attr[i]= Double.parseDouble(tokens[i]);
                }
                dataset.add(new Salary(attr[0],attr[1]));
                //lines.add(line);
            }
        }
    }
    protected void Regression(Salary query) {
        //y=a0+a1x
        double summationX = 0;
        double summationY = 0;
        double summationX2 = 0;
       // double summationY2 = 0;
        double summationXY = 0;
        if (nData != null) {
            for (int i = 0; i < n; i++) {
                summationX += nData.get(i).getYear();
                summationY += nData.get(i).getSalary();
                summationX2 += Math.pow(nData.get(i).getYear(), 2);
               // summationY2 += Math.pow(nData.get(i).getSalary(), 2);
                summationXY += nData.get(i).getYear() * nData.get(i).getSalary();
            }
            double averageX = summationX / n;
            double averageY = summationY / n;
            double b = ((n * summationXY) - (summationX * summationY)) / ((n * summationX2) - (Math.pow(summationX, 2)));
            double a = averageY - (b * averageX);

            double y = a + b * query.getYear();
            nData.sort(Comparator.comparingDouble(Salary::getYear));
            double smallestX = view.getWidth()/2.0f-5*20;
            double largestX = view.getWidth()-view.getWidth()/2.0f*20;
            System.out.println("smallx" + smallestX + "largestx" + largestX);
            nData.sort(Comparator.comparingDouble(Salary::getSalary));
            double smallestY = a+b*smallestX;
            double largestY = a+b*largestX;

            view.setLine(query.getYear(), y, smallestX, largestX, smallestY, largestY);
            tV4.setText(MessageFormat.format("{0} {1} ", getString(R.string.predicton), y));


        }
    }

}