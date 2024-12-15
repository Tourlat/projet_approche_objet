package com.projetjava.Model.game;

import com.projetjava.Controller.Observer;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class GameTimer {

  private final int DAY_LENGTH = 24;

  private int currentHour;

  private Timer timer;

  private static GameTimer instance;

  private static GameManager gameManager;

  private static final int TICK_RATE = 1000;

  private final List<Observer> observers = new ArrayList<>();

  /**
   * Singleton pattern : only one instance of GameTimer
   * @return the instance of GameTimer
   */
  public static GameTimer getInstance() {
    if (instance == null) {
      instance = new GameTimer();
    }
    return instance;
  }

  /**
   * Constructor of GameTimer
   */
  private GameTimer() {
    currentHour = 6;
    timer = new Timer();
  }

  /**
   * Start the timer
   */
  public void start() {
    GameTimer.gameManager = GameManager.getInstance();
    timer.scheduleAtFixedRate(
      new java.util.TimerTask() {
        @Override
        public void run() {
          updateGameTime();
          gameManager.hasGameEnded();
        }
      },
      0,
      TICK_RATE
    );
  }

  /**
   * Stop the timer
   */
  public void stop() {
    timer.cancel();
  }

  /**
   * Update the game time
   */
  private void updateGameTime() {
    currentHour = (currentHour + 1) % DAY_LENGTH;

    if (currentHour == 6) {
      System.out.println("It's now day time.");
    } else if (currentHour == 18) {
      System.out.println("It's now night time.");
      notifyObservers();
    }
  }

  /**
   * Add an observer to the list of observers
   * @param observer the observer to add
   */
  public void addObserver(Observer observer) {
    observers.add(observer);
  }

  /**
   * Remove an observer from the list of observers
   * @param observer the observer to remove
   */
  public void removeObserver(Observer observer) {
    observers.remove(observer);
  }

  /**
   * Notify (update) all observers
   */
  private void notifyObservers() {
    for (Observer observer : observers) {
      observer.update();
    }
  }
}
