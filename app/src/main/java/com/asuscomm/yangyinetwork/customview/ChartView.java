package com.asuscomm.yangyinetwork.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import java.io.InputStream;
import java.util.List;

/**
 * Created by jaeyoung on 19/10/2017.
 */

public class ChartView extends View {
    List<StockData> data;
    List<StockData> subset;

    float width, height;
    Paint paint = new Paint();

    public ChartView(Context context, int resId) {
        super(context);
        setBackgroundColor(Color.RED);

        InputStream inputStream = getResources().openRawResource(resId);
        data = CSVParser.read(inputStream);
        subset = data;
        paint.setColor(Color.GREEN);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        width = canvas.getWidth();
        height = canvas.getHeight();
        float rectWidth = width / subset.size();
        float left = 0;

        for (int i = subset.size() - 1; i >= 0; i--) {
            StockData stockData = subset.get(i);
            canvas.drawRect(left, height - stockData.high, left + rectWidth, height - stockData.low, paint);
            left += rectWidth;
        }
//        canvas.drawRect(0, height / 2, width, 0, paint);
    }

    public void showLast() {
        showLast(data.size());
    }

    public void showLast(int n) {
        subset = data.subList(0, n);
        // notify
        invalidate();
    }
}
