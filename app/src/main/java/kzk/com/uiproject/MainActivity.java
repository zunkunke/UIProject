package kzk.com.uiproject;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import kzk.com.uiproject.view.CustomerProgressBar;

public class MainActivity extends AppCompatActivity
{

    private CustomerProgressBar mCustomProgressBar;
    private int progressBar = 0;
    private @SuppressLint("HandlerLeak")
    Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        testProgressBar();
    }

    @SuppressLint("HandlerLeak")
    private void testProgressBar()
    {
        mCustomProgressBar = findViewById(R.id.custom_ProgressBar);

        mHandler = new Handler()
        {
            @Override
            public void handleMessage(Message msg)
            {
                if (progressBar > 100)
                {
                    return;
                }
                mCustomProgressBar.setProgress(progressBar++);
                mHandler.sendEmptyMessageDelayed(1,1000);
                super.handleMessage(msg);
            }
        };

        mHandler.sendEmptyMessage(1);
    }
}
