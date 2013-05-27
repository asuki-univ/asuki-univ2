package game.gui;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;

import board.Board;

enum GameState {
    NOT_STARTED,
    BLACK_TURN,
    WHITE_TURN,
    GAME_END
}

public class Main {
    static Board board;
    static GameState currentGameState = GameState.NOT_STARTED;

    public static void main(String[] args) {
        board = new Board();
        board.setup();

        Frame frame = new Frame("Reversi");
        frame.setSize(1024, 768);
        frame.setLayout(new BoxLayout(frame, BoxLayout.Y_AXIS));

        frame.add(makeTopPanel());
        frame.add(makeBoardPanel());

        frame.setVisible(true);
    }

    private static Panel makeTopPanel() {
        final String[] players = new String[] {
            "Human", "Simple", "MinMax", "NegaMax", "AlphaBeta",
            "NegaScount", "Tranposition"
        };

        Panel panel = new Panel();
        panel.setMaximumSize(new Dimension(1024, 100));
        panel.setLayout(new FlowLayout());

        Choice blackPlayerChoice = new Choice();
        for (String player : players)
            blackPlayerChoice.add(player);

        Choice whitePlayerChoice = new Choice();
        for (String player : players)
            whitePlayerChoice.add(player);
        whitePlayerChoice.select(players.length - 1);

        Button startButton = new Button("Start");

        panel.add(blackPlayerChoice);
        panel.add(startButton);
        panel.add(whitePlayerChoice);

        return panel;
    }

    private static Panel makeBoardPanel() {
        class BoardPanel extends Panel {
            @Override
            public void paint(Graphics g) {
                super.paint(g);

                g.setColor(Color.GREEN);
                g.fillRect(0, 0, 640, 640);
            }
        }

        BoardPanel panel = new BoardPanel();
        panel.setPreferredSize(new Dimension(640, 640));

        Panel p = new Panel();
        p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
        p.add(Box.createHorizontalGlue());
        p.add(panel);
        p.add(Box.createHorizontalGlue());

        return p;
    }
}
