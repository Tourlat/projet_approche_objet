package com.projetjava.Controller.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import com.projetjava.Controller.Observer;

public class GameTimer {
    private final int DAY_LENGTH = 24;
    private boolean isDay;

    private int currentHour;

    private boolean isUpdated;

    private boolean running;

    private Timer timer;

    private static GameTimer instance;

     private final List<Observer> observers = new ArrayList<>();

    public static GameTimer getInstance() {
        if (instance == null) {
            instance = new GameTimer();
        }
        return instance;
    }

    private GameTimer() {
        currentHour = 6;
        isDay = true;
        running = false;
        timer = new Timer();
    }

    public void start() {
        running = true;
        timer.scheduleAtFixedRate(new java.util.TimerTask() {
            @Override
            public void run() {
                updateGameTime();
            }
        }, 0, 1000);

    }

    public void stop() {
        running = true;

    }

    private void updateGameTime() {
        isUpdated = true;
        currentHour = (currentHour + 1) % DAY_LENGTH;
        if (currentHour == 6) {
            // notify observers that it's now day time update resources etc...
            notifyObservers();
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

    public boolean isRunning() {
        return running;
    }

    public boolean isUpdated() {
        return isUpdated;
    }

    public void setUpdated(boolean updated) {
        isUpdated = updated;
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    private void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }
}
