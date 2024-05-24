package battleship;

public class Ship {
    private final String NAME;
    private final int SIZE;

    public Ship(String name, int size) {
        this.NAME = name;
        this.SIZE = size;
    }

    public String getNAME() {
        return NAME;
    }

    public int getSIZE() {
        return SIZE;
    }
}
