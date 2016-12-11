package com.bounded.queue;

import com.bounded.queue.jobs.Observable;
import com.bounded.queue.jobs.Observer;

import java.util.ArrayList;
import java.util.List;

public class Subject implements Observable {

    private List<Observer> consumers = new ArrayList<>();
    private String name;

    public Subject(String name) {
        this.name = name;
    }

    @Override
    public void addObserver(Observer obj) {
        consumers.add(obj);
    }

    @Override
    public void removeObserver(Observer obj) {
        consumers.remove(obj);
    }

    @Override
    public void notifyObservers() {
        for(Observer consumer : consumers) {
            consumer.update();
        }
    }

    public void setProduced() {
        notifyObservers();
    }

    @Override
    public String toString() {
        return "Subject " + name;
    }
}
