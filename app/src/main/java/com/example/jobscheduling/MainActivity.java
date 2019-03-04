package com.example.jobscheduling;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
        public void scheduleJob(View view)
        {
            ComponentName componentName=new ComponentName(this,JobSchedulerService.class);
            JobInfo info = new JobInfo.Builder(12, componentName)
                    .setPeriodic(15 * 60 * 1000)
                    .build();

            JobScheduler jobScheduler= (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
            int resultCode = jobScheduler.schedule(info);
            if (resultCode == JobScheduler.RESULT_SUCCESS) {
                Log.e(TAG, "Job scheduled");
            } else {
                Log.e(TAG, "Job scheduling failed");
            }
        }
    public void cancelJob(View v) {
        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        scheduler.cancel(12);
        Log.e(TAG, "Job cancelled");
    }
    }
