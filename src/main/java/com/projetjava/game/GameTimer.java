package com.projetjava.game;

import java.util.Timer;

public class GameTimer {
    private final int DAY_LENGTH = 24;
    private boolean isDay;

    private int currentHour;

    Timer timer;

    public GameTimer() {
        timer = new Timer();
        currentHour = 6;
        isDay = true;


    }

    public void start() {
        timer.scheduleAtFixedRate(new java.util.TimerTask() {
            @Override
            public void run() {
                updateGameTime();
            }
        }, 0, 1000);
    }

    public void stop() {
        timer.cancel();
    }

    private void updateGameTime() {
        currentHour = (currentHour + 1) % DAY_LENGTH;
        if (currentHour == 6) {
            isDay = true;
            System.out.println("It's now day time.");
        } else if (currentHour == 18) {
            isDay = false;
            System.out.println("It's now night time.");
        }
    }

    public boolean isDay() {
        return isDay;
    }

    public int getTimeOfDay() {
        return currentHour;
    }

    

}
