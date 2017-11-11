package com.asuscomm.yangyinetwork.customview.v2;

import android.Manifest;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.asuscomm.yangyinetwork.customview.R;

public class AmplitudeActivity extends AppCompatActivity {
    private static final String TAG = "AmplitudeActivity";
    private static final int MY_PERMISSIONS_REQUEST_CODE = 1;
    private RecordViewV2 mRecordView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amplitude);

        Log.d(TAG, "onCreate: ");
        getPermission();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        record();
    }

    private void record() {
        Log.d(TAG, "record: ");

        mRecordView = (RecordViewV2) findViewById(R.id.recordViewV2);
        RecorderV2 recorderV2 = new RecorderV2(
                new RecorderV2.AudioDataReceivedListener() {
                    @Override
                    public void onAudioDataReceived(int data) {
                        mRecordView.addSample(data);
                    }
                }
        );
        recorderV2.startRecording();
    }

    public void getPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.RECORD_AUDIO)) {

            // Show an explanation to the user *asynchronously* -- don't block
            // this thread waiting for the user's response! After the user
            // sees the explanation, try again to request the permission.
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECORD_AUDIO,Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_CODE);

        } else {
            // No explanation needed, we can request the permission.

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECORD_AUDIO,Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_CODE);
            // MY_PERMISSIONS_REQUEST_CODE is an
            // app-defined int constant. The callback method gets the
            // result of the request.
        }
    }
}
