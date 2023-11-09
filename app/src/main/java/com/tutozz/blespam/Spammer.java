package com.tutozz.blespam;

public interface Spammer {
    boolean isSpamming();
    void start();
    void stop();
    void setBlinkRunnable(Runnable blinkRunnable);
    Runnable getBlinkRunnable();
}
