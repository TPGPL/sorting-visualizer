package pl.edu.pw.sortingvisualizer.sortingevent;

public class SortingEvent {
    private final SortingEventType type;
    private final int firstElementIndex;
    private final int secondElementIndex;

    public SortingEvent(SortingEventType type, int firstIndex, int secondIndex) {
        this.type = type;
        this.firstElementIndex = firstIndex;
        this.secondElementIndex = secondIndex;
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
}
