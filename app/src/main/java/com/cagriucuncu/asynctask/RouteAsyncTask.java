package com.cagriucuncu.asynctask;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class RouteAsyncTask extends AsyncTask< Object, Object, Boolean> {
    Context mContext;
    CustomView customView;
    public RouteAsyncTask(Context context,CustomView customView) {
        super();
        this.mContext = context;
        this.customView = customView;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        this.customView.setBackgroundColor(Color.parseColor("#FFFFDD"));
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        this.customView.setBackgroundColor(Color.parseColor("#DDFFDD"));
    }

    @Override
    protected void onProgressUpdate(Object... values) {
        super.onProgressUpdate(values);
        ArrayList<Point> currentProgress=(ArrayList<Point>) values[0];
        String status=(String) values[1];
        //Toast.makeText(mContext, ""+currentProgress, Toast.LENGTH_SHORT).show();
        this.customView.setPoints(currentProgress, status);
    }

    @Override
    protected void onCancelled(Boolean aBoolean) {
        super.onCancelled(aBoolean);

        Toast.makeText(mContext, "İşlem iptal edildi", Toast.LENGTH_SHORT).show();

        this.customView.setBackgroundColor(Color.parseColor("#FFDDDD"));
    }

    @Override
    protected Boolean doInBackground(Object... data) {
        ArrayList<Point> points = (ArrayList<Point>) data[0];
        float distance = Float.MAX_VALUE;
        for (int i = 0; i < 100; i++) {
            try {
                Thread.sleep(100);
                Collections.shuffle(points);
                float d = this.customView.getDistance(points);
                if(d < distance){
                    distance=d;
                    publishProgress(points,i + "/" +100 );
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(isCancelled()){
                return false;
            }
        }
        return true;
    }
}
