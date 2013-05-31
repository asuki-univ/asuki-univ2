package board;

public enum Stone {
    EMPTY() {
        @Override
        public Stone flip() { return EMPTY; }
    },
    BLACK() {
        @Override
        public Stone flip() { return WHITE; }
    },
    WHITE() {
        @Override
        public Stone flip() { return BLACK; }
    },
    WALL() {
        @Override
        public Stone flip() { throw new RuntimeException("WALL cannot be flipped."); }

    };

    abstract public Stone flip();
}

