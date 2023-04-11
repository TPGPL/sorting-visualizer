package pl.edu.pw.sortingvisualizer.utils;

/**
 * Wątek z dodatkowym mechanizmem zatrzymywania jego działania.
 */
public class KillableThread extends Thread {
    /**
     * Określa, czy wątek powinien zostać zatrzymany.
     */
    protected volatile boolean isKilled;

    /**
     * Wysyła komunikat o zatrzymaniu działania wątku.
     */
    public void kill() {
        isKilled = true;
    }
}