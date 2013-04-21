package game;

import player.Player;
import board.Board;
import board.Position;
import board.Stone;
import board.Turn;

public class Game {
    public enum Winner {
        BLACK,
        WHITE,
        TIE
    }
    
    public Winner play(Player blackPlayer, Player whitePlayer, Board board, Turn turn, Boolean verbose) {
        boolean hasPassed = false;

        while (true) {
            if (verbose) {
                // 盤を見やすいように表示
                board.show();
            }
            
            // どこかに置けないならばパスをしなければならない。
            if (!board.isPuttableSomewhere(turn.stone())) {
                if (hasPassed) // ２連続パスすると終了
                    break;

                hasPassed = true;
                turn = turn.flip();
                continue;
            }

            hasPassed = false;
            Position p; 
            if (turn == Turn.BLACK)
                p = blackPlayer.play(board);
            else
                p = whitePlayer.play(board);

            board.put(p.x, p.y, turn.stone());
            turn = turn.flip();
        }
        
        int blackStoneCount = board.countStone(Stone.BLACK);
        int whiteStoneCount = board.countStone(Stone.WHITE);
        
        if (verbose) {
            board.show();
            System.out.printf("BLACK = %d\n", blackStoneCount);
            System.out.printf("WHITE = %d\n", whiteStoneCount);
        }
        
        if (blackStoneCount > whiteStoneCount) {
            return Winner.BLACK;
        } else if (blackStoneCount < whiteStoneCount) {
            return Winner.WHITE;
        } else {
            return Winner.TIE;
        }
    }

}
