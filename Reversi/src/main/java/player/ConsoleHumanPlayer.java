package player;

import java.util.Scanner;

import board.Board;
import board.Position;
import board.Turn;

public class ConsoleHumanPlayer extends Player {
    public ConsoleHumanPlayer(Turn turn) {
        super(turn);
    }
    
    public Position play(Board board) {
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.print("(x, y) = ?");
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            if (!board.isPuttable(x, y, turn.stone())) {
                System.out.println("Invalid.");
                continue;
            }

            return new Position(x, y);
        }
    }
}
