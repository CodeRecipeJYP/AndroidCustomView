package com.asuscomm.yangyinetwork.customview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;

import java.io.InputStream;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InputStream inputStream = getResources().openRawResource(R.raw.goog);
        List<StockData> data = CSVParser.read(inputStream);

        for (StockData stockData : data) {
            System.out.println(stockData.toString());
        }

        ChartView chartView = new ChartView(this);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.activity_main);
        relativeLayout.addView(chartView);
    }
}
