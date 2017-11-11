package com.asuscomm.yangyinetwork.customview.v2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.asuscomm.yangyinetwork.customview.R;

public class AmplitudeActivity extends AppCompatActivity {
    private static final String TAG = "AmplitudeActivity";
    private RecordViewV2 mRecordView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amplitude);

        Log.d(TAG, "onCreate: ");
        mRecordView = (RecordViewV2) findViewById(R.id.recordViewV2);
        short amplitude = (short) 0;
        int iteration = 5000;
        short target = Short.MAX_VALUE/2;
        short each = (short) ((Short.MAX_VALUE - target) / iteration);
        for (short i = Short.MAX_VALUE; i > target; i -= each) {
            mRecordView.addSample(i);
        }
    }
}
