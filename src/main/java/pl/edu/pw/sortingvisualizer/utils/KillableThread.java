package pl.edu.pw.sortingvisualizer.utils;

public class KillableThread extends Thread {
    protected volatile boolean isKilled;

    public void kill() {
        isKilled = true;
    }

    public boolean isKilled() {
        return isKilled;
    }
}