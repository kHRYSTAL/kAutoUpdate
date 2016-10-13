package me.khrystal.kupdatedemo.test;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.File;

import me.khrystal.kautoupdate.net.ApkPatchDownLoader;
import me.khrystal.kautoupdate.net.ApkPatchResponseBody;
import me.khrystal.kupdatedemo.R;

/**
 * usage:
 * author: kHRYSTAL
 * create time: 16/10/13
 * update time:
 * email: 723526676@qq.com
 */

public class DownLoadTestActivity extends AppCompatActivity implements View.OnClickListener, ApkPatchResponseBody.ProgressListener {

    Button mBtnStart, mBtnPause, mBtnResume;
    ProgressBar mProgressBar;
    public static final String APK_URL = "http://gdown.baidu.com/data/wisegame/df65a597122796a4/weixin_821.apk";

    private long breakPoints;
    private ApkPatchDownLoader downloader;
    private File file;
    private long currentBytes;
    private long contentLength;
    private boolean isComplete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        mProgressBar = (ProgressBar) findViewById(R.id.progress);
        mBtnStart = (Button) findViewById(R.id.btn_start);
        mBtnPause = (Button) findViewById(R.id.btn_pause);
        mBtnResume = (Button) findViewById(R.id.btn_resume);

        mBtnStart.setOnClickListener(this);
        mBtnResume.setOnClickListener(this);
        mBtnPause.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                breakPoints = 0L;
                file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "sample.apk");
                downloader = new ApkPatchDownLoader(APK_URL, file, this);
                downloader.execute(0L);
                break;
            case R.id.btn_pause:
                downloader.pause();
                Toast.makeText(this, "pause", Toast.LENGTH_SHORT).show();
                breakPoints = currentBytes;
                break;
            case R.id.btn_resume:
                downloader.execute(breakPoints);
                break;
        }
    }

    @Override
    public void onPreExecute(long contentLength) {
        if (this.contentLength == 0L) {
            this.contentLength = contentLength;
            mProgressBar.setMax((int) (contentLength / 1024));
        }
    }

    @Override
    public void onUpdate(final long currentBytes, final boolean isComplete) {
        this.currentBytes = currentBytes + breakPoints;
        mProgressBar.setProgress((int) (currentBytes + breakPoints) / 1024);
//        this.isComplete = isComplete;
//        runOnUiThread(mUiRunnable);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (isComplete)
                    Toast.makeText(DownLoadTestActivity.this, "Complete", Toast.LENGTH_SHORT).show();
            }
        });

    }

//    private Runnable mUiRunnable = new Runnable() {
//        @Override
//        public void run() {
//            mProgressBar.setProgress((int) (currentBytes + breakPoints) / 1024);
//            if (isComplete) {
//                Toast.makeText(DownLoadTestActivity.this, "Complete", Toast.LENGTH_SHORT).show();
//            }
//        }
//    };

}
