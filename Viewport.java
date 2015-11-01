package particlesystem;



public enum Viewport {
    WIDTH(720),
    HEIGHT(540);

    private final int value;

    Viewport(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
