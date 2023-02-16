package com.cagriucuncu.asynctask;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

public class FakeAsyncTask extends AsyncTask<Object, Integer, Boolean> {
    Context mContext;
    ProgressBar mProgressBar;
    public FakeAsyncTask(Context context, ProgressBar progressBar) {
        super();
        this.mContext = context;
        this.mProgressBar = progressBar;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressBar.setProgress(0);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        mProgressBar.setProgress(100);
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        int currentProgress=values[0].intValue();
        //Toast.makeText(mContext, ""+currentProgress, Toast.LENGTH_SHORT).show();
        mProgressBar.setProgress(currentProgress);
    }

    @Override
    protected void onCancelled(Boolean aBoolean) {
        super.onCancelled(aBoolean);

        Toast.makeText(mContext, "İşlem iptal edildi", Toast.LENGTH_SHORT).show();
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    protected Boolean doInBackground(Object... integers) {
        int count = (int) integers[0];
        for (int i = 0; i < count; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            publishProgress((int) (100.0*i/count));
            if(isCancelled()){
                return false;
            }
        }
        return true;
    }
}
