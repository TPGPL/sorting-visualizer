package pl.edu.pw.sortingvisualizer.sortingevent;

public class SortingEvent {
    private final SortingEventType type;
    private final int firstElementIndex;
    private final int secondElementIndex;
    private final double value;

    public SortingEvent(SortingEventType type, int firstIndex, int secondIndex) {
        this.type = type;
        this.firstElementIndex = firstIndex;
        this.secondElementIndex = secondIndex;
        this.value = 0;
    }

    public SortingEvent(SortingEventType type, int firstIndex, double value) {
        this.type = type;
        this.firstElementIndex = firstIndex;
        this.value = value;
        this.secondElementIndex = -1;
    }

    public SortingEventType getType() {
        return type;
    }

    public int getFirstElementIndex() {
        return firstElementIndex;
    }

    public int getSecondElementIndex() {
        return secondElementIndex;
    }

    public double getValue() {
        return value;
    }
}
