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

    float width, height, maxPrice, minPrice;
    Paint paint = new Paint();

    public ChartView(Context context, int resId) {
        super(context);
        setBackgroundColor(Color.RED);

        InputStream inputStream = getResources().openRawResource(resId);
        data = CSVParser.read(inputStream);
        showLast();
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
            canvas.drawRect(left, getYPosition(stockData.high), left + rectWidth, getYPosition(stockData.low), paint);
            left += rectWidth;
        }
//        canvas.drawRect(0, height / 2, width, 0, paint);
    }

    private float getYPosition(float price) {
        float scaleFactorY = (price - minPrice) / (maxPrice - minPrice);
        return height - height * scaleFactorY;
    }

    private void updateMaxAndMin() {
        maxPrice = -1f;
        minPrice = 99999f;
        for (StockData stockData : subset) {
            if (stockData.high > maxPrice)
                maxPrice = stockData.high;
            if (stockData.low < minPrice)
                minPrice = stockData.low;
        }
    }

    public void showLast() {
        showLast(data.size());
    }

    public void showLast(int n) {
        subset = data.subList(0, n);
        updateMaxAndMin();
        // notify
        invalidate();
    }
}
