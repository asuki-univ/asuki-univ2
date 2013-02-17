package board;

public enum Turn {
    BLACK() {
        @Override
        public Turn flip() { return WHITE; }
        @Override
        public Stone stone() { return Stone.BLACK; }
    },
    WHITE() {
        @Override
        public Turn flip() { return BLACK; }
        @Override
        public Stone stone() { return Stone.WHITE; }		
    };

    public abstract Turn flip();
    public abstract Stone stone();
}

