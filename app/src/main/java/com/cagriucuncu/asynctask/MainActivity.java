package com.cagriucuncu.asynctask;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button button, button2, button3 ;
    ProgressBar progressBar ;
    AsyncTask task;
    CustomView customView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init_UI();

    }

    private  ArrayList<Point> getPoints(){
        ArrayList<Point> points = new ArrayList<Point>();
        points.add(new Point(30,30));
        points.add(new Point(550,70));
        points.add(new Point(450,270));
        points.add(new Point(90,390));
        points.add(new Point(350,170));
        points.add(new Point(70,150));
        return points;
    }

    private void init_UI() {
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        button = (Button) findViewById(R.id.button);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        customView = (CustomView) findViewById(R.id.customView) ;

        button2.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        Context self = this;

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                task = new FakeAsyncTask(self, progressBar);
                Integer c = new Integer(10);
                task.execute(c);
                button2.setVisibility(View.VISIBLE);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(task != null && task.getStatus() == AsyncTask.Status.RUNNING){
                    task.cancel(true);
                    button2.setVisibility(View.INVISIBLE);
                }
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                task = new RouteAsyncTask(self, customView);
                task.execute(getPoints());
                button2.setVisibility(View.VISIBLE);
            }
        });
    }
}