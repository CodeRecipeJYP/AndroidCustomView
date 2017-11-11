package com.asuscomm.yangyinetwork.customview;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.audiofx.Visualizer;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.asuscomm.yangyinetwork.customview.audio.RecordView;
import com.asuscomm.yangyinetwork.customview.audio.Recorder;
import com.asuscomm.yangyinetwork.customview.audio.RendererFactory;
import com.asuscomm.yangyinetwork.customview.audio.WaveformView;

import java.io.InputStream;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Visualizer.OnDataCaptureListener {
    private static final String TAG = "MainActivity";

    private int REQUEST_RECORD_AUDIO_CODE = 123;
    private static final String PAST_WEEK = "Past Week";
    private static final String PAST_MONTH = "Past Month";
    private static final String PAST_YEAR = "Past Year";

    private static final String ALL_DATA = "All Data";
    private ChartView chartView;
    private RecordView mRecordView;
    private Recorder mRecorder;
    private Visualizer visualiser;
    private WaveformView waveformView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chartView = (ChartView) findViewById(R.id.chartView);
        findViewById(R.id.recordBtn).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onRecordBtnClicked(view);
                    }
                }
        );

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.RECORD_AUDIO)
                == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.MODIFY_AUDIO_SETTINGS)
                == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "onCreate: PERMISSION_GRANTED");
            initRecord();
            initWaveformView();
        } else {
            Log.d(TAG, "onCreate: REQUEST_PERMISSION");
            ActivityCompat.requestPermissions(this, new String[]{
                            Manifest.permission.RECORD_AUDIO,
                            Manifest.permission.MODIFY_AUDIO_SETTINGS
            }, REQUEST_RECORD_AUDIO_CODE);
        }
//        chartView = new ChartView(this, R.raw.goog);
    }

    private void initWaveformView() {
        waveformView = (WaveformView) findViewById(R.id.waveformView);
        RendererFactory rendererFactory = new RendererFactory();
        waveformView.setRenderer(rendererFactory.createSimpleWaveformRenderer(Color.GREEN, Color.DKGRAY));

        final int CAPTURE_SIZE = 256;
        visualiser = new Visualizer(0);
        visualiser.setDataCaptureListener(this, Visualizer.getMaxCaptureRate(), true, false);
        visualiser.setCaptureSize(CAPTURE_SIZE);
        visualiser.setEnabled(true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_RECORD_AUDIO_CODE) {
            if (grantResults.length > 0) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    initRecord();
                    initWaveformView();
                }
            }
        }
    }

    private void onRecordBtnClicked(View v) {
        Log.d(TAG, "onRecordBtnClicked: ");
        mRecorder.startRecording();
    }

    private void initRecord() {
        mRecordView = (RecordView) findViewById(R.id.recordView);
        mRecorder = new Recorder(new Recorder.AudioDataReceivedListener() {
            @Override
            public void onAudioDataReceived(short[] data) {
                mRecordView.setSamples(data);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getTitle().toString()) {
            case PAST_WEEK:
                chartView.showLast(7);
                break;
            case PAST_MONTH:
                chartView.showLast(30);
                break;
            case PAST_YEAR:
                chartView.showLast(365);
                break;
            case ALL_DATA:
                chartView.showLast();
                break;
        }

        return true;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(PAST_WEEK);
        menu.add(PAST_MONTH);
        menu.add(PAST_YEAR);
        menu.add(ALL_DATA);

        return true;
    }

    @Override
    public void onWaveFormDataCapture(Visualizer visualizer, byte[] bytes, int i) {
        if (waveformView != null) {
            waveformView.setWaveform(bytes);
        }
    }

    @Override
    public void onFftDataCapture(Visualizer visualizer, byte[] bytes, int i) {

    }
}
