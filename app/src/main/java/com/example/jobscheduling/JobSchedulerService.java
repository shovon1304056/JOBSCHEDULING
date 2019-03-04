package com.example.jobscheduling;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JobSchedulerService extends JobService {
    private static final String TAG = "ExampleJobService";
    private boolean jobCancelled = false;

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.e(TAG, "Job started");
        doBackgroundWork(params);

        return true;
    }

    private void doBackgroundWork(final JobParameters params) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    Log.e(TAG, "Hello: " + getTime());
                    if (jobCancelled) {
                        return;
                    }

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                Log.e(TAG, "Job finished");
                jobFinished(params, false);
            }
        }).start();


        /*new Thread(new Runnable() {
            @Override
            public void run() {
                for (; ; ) {
                    wrtieFileOnInternalStorage(getApplicationContext(), "myLogData.txt", getTime() + "\n");
                    if (jobCancelled) {
                        return;
                    }

                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                //Log.e(TAG, "Job finished");
                //jobFinished(params, false);
            }
        }).start();*/
    }

   /* public void wrtieFileOnInternalStorage(Context mcoContext, String sFileName, String sBody) {

        try {
            File file = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS), "softdir");
            if (!file.exists()) {
                Log.e("Catch", " File Created ");
                file.mkdirs();
            }
            File gpxfile = new File(file, sFileName);
            FileWriter writer = new FileWriter(gpxfile, true);
            writer.append(sBody);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            Log.e(TAG, "wrtieFileOnInternalStorage: " + e.getLocalizedMessage());
        }
    }
*/
    private String getTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return formatter.format(new Date());
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.e(TAG, "Job cancelled before completion");
        jobCancelled = true;
        return true;
    }

}