package fr.obelouix.ultimate.api;

public enum InventoryType {
    ONE_ROW(9),
    TWO_ROW(18),
    THREE_ROW(27),
    FOUR_ROW(36),
    FIVE_ROW(45),
    SIX_ROW(54);

    private final int size;

    InventoryType(int size) {
        this.size = size;
    }

    public int get() {
        return size;
    }
}
