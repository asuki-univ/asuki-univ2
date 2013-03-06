package board;

public class Position {
    public int x;
    public int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Position))
            return false;
        
        Position lhs = this;
        Position rhs = (Position) obj;
        
        return lhs.x == rhs.x && lhs.y == rhs.y;
    }
   
    @Override
    public int hashCode() {
        return x + 37 * y;
    }
    
    @Override
    public String toString() {
        return String.format("%d %d", x, y);
    }
}