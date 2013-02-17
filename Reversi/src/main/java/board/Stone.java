package board;

public enum Stone {
    EMPTY,
    BLACK() {
        @Override
        public Stone flip() { return Stone.WHITE; }
    },
    WHITE() {
        @Override
        public Stone flip() { return Stone.BLACK; }
    },
    WALL;

    public Stone flip() {
        assert(false);
        return null;
    }
}

