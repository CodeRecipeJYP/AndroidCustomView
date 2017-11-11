package com.asuscomm.yangyinetwork.customview.v2;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.media.audiofx.Visualizer;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;


import java.io.File;
import java.io.IOException;

/**
 * Created by jaeyoung on 11/11/2017.
 */

public class RecorderV2 {
    private static final String TAG = "RecorderV2";
    private static final int SAMPLE_RATE = 44100;
    private static final long DELAY = 150;
    private long startTime;
    private Runnable mRunnable;

    public RecorderV2(AudioDataReceivedListener audioDataReceivedListener) {
        mListener = audioDataReceivedListener;
    }

    public interface AudioDataReceivedListener {
        void onAudioDataReceived(int data);
    }
    private boolean mShouldContinue;
    private MediaRecorder mMediaRecorder;
    private AudioDataReceivedListener mListener;
    private Handler mHandler;

    public boolean recording() {
        return mShouldContinue;
    }

    public void startRecording() {
        if (mShouldContinue)
            return;

        mShouldContinue = true;
        record();
    }

    public void stopRecording() {
        if (mShouldContinue) {
            mShouldContinue = false;
        }
    }


    private void record() {
        Log.v(TAG, "Start");
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_AUDIO);

        mMediaRecorder = new MediaRecorder();
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
        mMediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.DEFAULT);

        File file = new File(Environment.getExternalStorageDirectory().getPath() + "/" + "VoiceRecorder");
        if (!file.exists()) {
            Log.d(TAG, "record: notexist");
            file.mkdir();
        }

        mMediaRecorder.setOutputFile(Environment.getExternalStorageDirectory().getPath() + "/" + "VoiceRecorder" + "/" + System.currentTimeMillis() + ".mp3");
        try {
            mMediaRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mMediaRecorder.start();

        Log.v(TAG, "Start recording");

        startTime = System.currentTimeMillis();
        mHandler = new Handler();
        mRunnable = new Runnable() {
            @Override
            public void run() {
                if (mShouldContinue) {
                    mListener.onAudioDataReceived(mMediaRecorder.getMaxAmplitude());
                    activateAmplitudeChecker();
                } else {
                    Log.d(TAG, "run: end");
                }
            }
        };
        activateAmplitudeChecker();

    }

    private void activateAmplitudeChecker() {
        mHandler.postDelayed(mRunnable, DELAY);
    }
}
