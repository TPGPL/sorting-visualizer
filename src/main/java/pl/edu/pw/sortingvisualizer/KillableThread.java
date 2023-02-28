package pl.edu.pw.sortingvisualizer;

public class KillableThread extends Thread {
    protected volatile boolean isKilled;

    public void kill() {
        isKilled = true;
    }
}