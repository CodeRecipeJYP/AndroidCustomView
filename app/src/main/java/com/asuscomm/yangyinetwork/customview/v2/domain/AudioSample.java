package com.asuscomm.yangyinetwork.customview.v2.domain;

/**
 * Created by jaeyoung on 11/11/2017.
 */

public class AudioSample {
    private long time;
    private long amplitude;

    public AudioSample(long time, int amplitude) {
        this.time = time;
        this.amplitude = amplitude;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getAmplitude() {
        return amplitude;
    }

    public void setAmplitude(long amplitude) {
        this.amplitude = amplitude;
    }
}
