package com.asuscomm.yangyinetwork.customview.audio;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by jaeyoung on 19/10/2017.
 */

public class RecordView extends View {
    private static final String TAG = "RecordView";
    List<short[]> stackedData = new ArrayList<>();

    float width, height;
    Paint paint = new Paint();
    int SAMPLINGRATE = 44100;
    int VISUALDOT_PERSECOND = 10;
    int SAMPLES_PER_SINGLEVISUALDOT = SAMPLINGRATE / VISUALDOT_PERSECOND;


    public RecordView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public void setSamples(short[] data) {
//        Log.d(TAG, "setSamples() called with: data = [" + Arrays.toString(data) + "]");
        stackedData.add(data);
        postInvalidate();
    }

    protected void onDraw(Canvas canvas) {
        int totalSize;
        if (stackedData.isEmpty()) {
            return;
        } else {
            totalSize = stackedData.size() * stackedData.get(0).length;
        }
        width = canvas.getWidth();
        height = canvas.getHeight();


        float middleHeight = height / 2;
        float rectWidth = width / totalSize * SAMPLES_PER_SINGLEVISUALDOT;
        float left = 0;

        float maximum = 0;
        int totalidx = 0;
        long bufferAmplitude = 0;
        Log.d(TAG, "onDraw: stackedData = [" + stackedData.size() + "]");
        Log.d(TAG, "onDraw: totalSize = [" + totalSize + "]");
        Log.d(TAG, "onDraw: num of visualdots = [" + totalSize / SAMPLES_PER_SINGLEVISUALDOT + "]");

        for (int i = 0; i < stackedData.size(); i++) {
            short[] data = stackedData.get(i);
            for (int j = 0; j < data.length; j++) {
                totalidx++;
                short amplitude = data[i];
                bufferAmplitude += amplitude;
                if (totalidx % SAMPLES_PER_SINGLEVISUALDOT == 0) {
//                    Log.d(TAG, "onDraw: bufferAmplitude=" + bufferAmplitude);
                    paint.setColor(Color.BLACK);
                    if (bufferAmplitude > maximum) {
                        maximum = bufferAmplitude;
                    }
                    short nomalizedAmplitude = (short) (bufferAmplitude / SAMPLES_PER_SINGLEVISUALDOT);
                    float heightPortion = (float) (nomalizedAmplitude / Short.MAX_VALUE * 10);
                    canvas.drawRect(left, middleHeight, left + rectWidth, middleHeight + middleHeight * heightPortion, paint);
                    canvas.drawRect(left, middleHeight, left + rectWidth, middleHeight - middleHeight * heightPortion, paint);
                    left += rectWidth;
                    bufferAmplitude = 0;
                }
            }
        }

        Log.d(TAG, "onDraw: maximum=" + maximum);
    }
}
