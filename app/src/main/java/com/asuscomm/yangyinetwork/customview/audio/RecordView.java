package com.asuscomm.yangyinetwork.customview.audio;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;


import java.util.Arrays;
import java.util.List;

/**
 * Created by jaeyoung on 19/10/2017.
 */

public class RecordView extends View {
    private static final String TAG = "RecordView";
    short[] data;

    float width, height, maxPrice, minPrice;
    Paint paint = new Paint();


    public RecordView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        data = new short[0];
    }

    public void setSamples(short[] data) {
        Log.d(TAG, "setSamples() called with: data = [" + data.length + "]");
//        Log.d(TAG, "setSamples() called with: data = [" + Arrays.toString(data) + "]");
        this.data = data;
        postInvalidate();
    }

    protected void onDraw(Canvas canvas) {
        width = canvas.getWidth();
        height = canvas.getHeight();
        float middleHeight = height / 2;
        float rectWidth = width / data.length;
        float left = 0;

        float maximum = 0;
        for (int i = data.length - 1; i >= 0; i--) {
            short amplitude = data[i];
            paint.setColor(Color.BLACK);
            if (amplitude > maximum) {
                maximum = amplitude;
            }
            float heightPortion = (float) (Math.log10(amplitude) / Math.log10(Short.MAX_VALUE));
            canvas.drawRect(left, middleHeight, left + rectWidth, middleHeight + middleHeight * heightPortion, paint);
            canvas.drawRect(left, middleHeight, left + rectWidth, middleHeight - middleHeight * heightPortion, paint);
            left += rectWidth;
        }

        Log.d(TAG, "onDraw: maximum=" + maximum);
    }
}
