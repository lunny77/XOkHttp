package com.lunny.xokhttp;

public abstract class NamedRunnable implements Runnable {
    String name;

    public NamedRunnable() {
    }

    public NamedRunnable(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        String old = Thread.currentThread().getName();
        try {
            Thread.currentThread().setName(name);
            execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Thread.currentThread().setName(old);
        }
    }

    abstract void execute();
}
