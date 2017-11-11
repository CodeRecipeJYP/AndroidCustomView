package com.asuscomm.yangyinetwork.customview.audio;

import android.support.annotation.ColorInt;

/**
 * Created by jaeyoung on 19/10/2017.
 */

public class RendererFactory {
    public WaveformView.WaveformRenderer createSimpleWaveformRenderer(@ColorInt int foreground, @ColorInt int background) {
        return SimpleWaveformRenderer.newInstance(background, foreground);
    }
}
