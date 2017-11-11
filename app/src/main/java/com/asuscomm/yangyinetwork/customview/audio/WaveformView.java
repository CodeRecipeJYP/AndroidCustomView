package com.asuscomm.yangyinetwork.customview.audio;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import java.util.Arrays;

/**
 * Created by jaeyoung on 19/10/2017.
 */

public class WaveformView extends View {
    public interface WaveformRenderer {
        void render(Canvas canvas, byte[] waveform);
    }

    private byte[] waveform;

    private WaveformRenderer renderer;

    public WaveformView(Context context) {
        super(context);
    }

    public WaveformView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WaveformView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setRenderer(WaveformRenderer renderer) {
        this.renderer = renderer;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (renderer != null) {
            renderer.render(canvas, waveform);
        }
    }

    public void setWaveform(byte[] waveform) {
        this.waveform = Arrays.copyOf(waveform, waveform.length);
        invalidate();
    }

}