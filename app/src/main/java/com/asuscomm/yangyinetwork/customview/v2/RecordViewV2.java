package com.asuscomm.yangyinetwork.customview.v2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jaeyoung on 11/11/2017.
 */

public class RecordViewV2 extends View {
    private static final String TAG = "RecordView";
    List<Short> stackedData = new ArrayList<>();

    float width, height;
    Paint strokePaint = new Paint();
    int SAMPLES_IN_DISPLAY = 1000;


    public RecordViewV2(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setBackgroundColor(Color.YELLOW);
        strokePaint.setColor(Color.BLACK);
    }

    public void addSample(short sample) {
//        Log.d(TAG, "setSamples() called with: data = [" + Arrays.toString(data) + "]");
        Log.d(TAG, "addSample() called with: sample = [" + sample + "]");
        stackedData.add(sample);
        postInvalidate();
    }

    protected void onDraw(Canvas canvas) {
        int totalSize;
        if (stackedData.isEmpty()) {
            return;
        } else {
            totalSize = stackedData.size();
        }
        width = canvas.getWidth();
        height = canvas.getHeight();


        float middleHeight = height / 2;
        float rectWidth = width / SAMPLES_IN_DISPLAY;
        float left = 0;
        strokePaint.setStrokeWidth(rectWidth / 2);

        Log.d(TAG, "onDraw: stackedData = [" + stackedData.size() + "]");
        Log.d(TAG, "onDraw: totalSize = [" + totalSize + "]");

        for (int i = 0; i < stackedData.size(); i++) {
            short data = stackedData.get(i);
            float heightPortion = (float) ((float) Math.log(data) / Math.log(Short.MAX_VALUE));
//            Log.d(TAG, "onDraw: data=" + data);
//            Log.d(TAG, "onDraw: Short.MAX_VALUE =" + Short.MAX_VALUE);
//            Log.d(TAG, "onDraw() called with: i = [" + heightPortion + "]");
            canvas.drawLine(left, middleHeight, left, middleHeight + middleHeight*heightPortion, strokePaint);
            canvas.drawLine(left, middleHeight, left, middleHeight - middleHeight*heightPortion, strokePaint);
            left += rectWidth;
        }

    }
}
