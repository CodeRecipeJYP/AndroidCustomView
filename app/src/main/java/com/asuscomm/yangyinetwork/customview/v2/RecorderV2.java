package com.asuscomm.yangyinetwork.customview.v2;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.media.audiofx.Visualizer;
import android.util.Log;

import com.asuscomm.yangyinetwork.customview.audio.Recorder;

/**
 * Created by jaeyoung on 11/11/2017.
 */

public class RecorderV2 {
    private static final String TAG = "RecorderV2";
    private static final int SAMPLE_RATE = 44100;

    public interface AudioDataReceivedListener {
        void onAudioDataReceived(short[] data);
    }

    public RecorderV2(Recorder.AudioDataReceivedListener listener) {
        mListener = listener;
    }

    private boolean mShouldContinue;
    private Recorder.AudioDataReceivedListener mListener;
    private Thread mThread;

    public boolean recording() {
        return mThread != null;
    }

    public void startRecording() {
        if (mThread != null)
            return;

        mShouldContinue = true;
        mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                record();
            }
        });
        mThread.start();

        new Visualizer(0);
    }

    public void stopRecording() {
        if (mThread == null)
            return;

        mShouldContinue = false;
        mThread = null;
    }


    private void record() {
        Log.v(TAG, "Start");
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_AUDIO);

        // buffer size in bytes
        int bufferSize = AudioRecord.getMinBufferSize(SAMPLE_RATE,
                AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT);

        if (bufferSize == AudioRecord.ERROR || bufferSize == AudioRecord.ERROR_BAD_VALUE) {
            bufferSize = SAMPLE_RATE * 2;
        }

        short[] audioBuffer = new short[bufferSize / 2];

        AudioRecord record = new AudioRecord(MediaRecorder.AudioSource.DEFAULT,
                SAMPLE_RATE,
                AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT,
                bufferSize);

        if (record.getState() != AudioRecord.STATE_INITIALIZED) {
            Log.e(TAG, "Audio Record can't initialize!");
            return;
        }
        record.startRecording();

        Log.v(TAG, "Start recording");

        long shortsRead = 0;
        while (mShouldContinue) {
            int numberOfShort = record.read(audioBuffer, 0, audioBuffer.length);
            shortsRead += numberOfShort;

            // Notify waveform
            mListener.onAudioDataReceived(audioBuffer);
        }

        record.stop();
        record.release();

        Log.v(TAG, String.format("Recording stopped. Samples read: %d", shortsRead));
    }
}
