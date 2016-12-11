package com.bounded.queue.jobs;

public interface Observable {

    void addObserver(Observer obj);
    void removeObserver(Observer obj);

    void notifyObservers();
}
