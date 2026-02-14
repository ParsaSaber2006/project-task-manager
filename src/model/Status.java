package model;

public enum Status {
    TODO(1),COMPLETED(2),TIMED_OUT(0);

    private final int value;
    private Status(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}
