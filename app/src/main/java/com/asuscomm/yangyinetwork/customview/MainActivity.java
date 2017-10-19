package com.asuscomm.yangyinetwork.customview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import java.io.InputStream;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String PAST_WEEK = "Past Week";
    private static final String PAST_MONTH = "Past Month";
    private static final String PAST_YEAR = "Past Year";
    private static final String ALL_DATA = "All Data";

    private ChartView chartView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chartView = (ChartView) findViewById(R.id.chartView);
//        chartView = new ChartView(this, R.raw.goog);
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
}
