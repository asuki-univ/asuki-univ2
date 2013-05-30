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
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.BoxLayout;

import player.Player;
import player.ai.AlphaBetaEvaluationSimpleAI;
import player.ai.LearnedAI;
import player.ai.NegaMaxSimpleAI;
import player.ai.NegaScoutEvaluationSimpleAI;
import player.ai.TranpositionEvaluationSimpleAI;
import player.ai.simple.MinMaxSimpleAI;
import player.ai.simple.SimpleAIPlayer;

import board.Board;
import board.Position;
import board.Stone;
import board.Turn;

enum GameState {
    NOT_STARTED,
    BLACK_TURN,
    WHITE_TURN,
    GAME_END
}

public class Main {
    static Board board;
    static GameState currentGameState = GameState.NOT_STARTED;
    static Choice blackPlayerChoice = new Choice();
    static Choice whitePlayerChoice = new Choice();
    static boolean hasPassed = false;
    static BoardPanel boardPanel = new BoardPanel();

    static class BoardPanel extends Panel {
        @Override
        public void paint(Graphics g) {
            super.paint(g);

            System.out.println("repaint");

            g.setColor(Color.GREEN);
            g.fillRect(0, 0, 640, 640);

            for (int x = 0; x < 9; ++x) {
                g.setColor(Color.BLACK);
                g.drawLine(0, 80 * x, 640, 80 * x);
                g.drawLine(80 * x, 0, 80 * x, 640);
            }

            for (int x = 1; x <= Board.WIDTH; ++x) {
                for (int y = 1; y <= Board.HEIGHT; ++y) {
                    System.out.println(board.get(x, y));
                    switch (board.get(x, y)) {
                    case BLACK:
                        g.setColor(Color.BLACK);
                        g.fillArc((x - 1) * 80 + 5, (y - 1) * 80 + 5, 70, 70, 0, 360);
                        break;
                    case WHITE:
                        g.setColor(Color.WHITE);
                        g.fillArc((x - 1) * 80 + 5, (y - 1) * 80 + 5, 70, 70, 0, 360);
                        break;
                    case EMPTY:
                    default:
                    }
                }
            }
        }
    }


    public static void main(String[] args) {
        board = new Board();
        board.setup();

        Frame frame = new Frame("Reversi");
        frame.setSize(768, 768);
        frame.setLayout(new BoxLayout(frame, BoxLayout.Y_AXIS));

        frame.add(makeTopPanel());
        frame.add(makeBoardPanel());

        frame.setVisible(true);
    }

    private static Panel makeTopPanel() {
        final String[] players = new String[] {
            "Human", "Simple", "MinMax", "NegaMax", "AlphaBeta",
            "NegaScout", "Tranposition", "Learn"
        };

        Panel panel = new Panel();
        panel.setMaximumSize(new Dimension(1024, 100));
        panel.setLayout(new FlowLayout());

        for (String player : players)
            blackPlayerChoice.add(player);
        for (String player : players)
            whitePlayerChoice.add(player);
        whitePlayerChoice.select(players.length - 1);

        Button startButton = new Button("Start");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentGameState = GameState.BLACK_TURN;
                doTurn();
            }
        });

        panel.add(blackPlayerChoice);
        panel.add(startButton);
        panel.add(whitePlayerChoice);

        return panel;
    }

    private static void doTurn() {
        System.out.println(currentGameState.toString());

        switch (currentGameState) {
        case NOT_STARTED:
            break;
        case BLACK_TURN: {
            if (!board.isPuttableSomewhere(Stone.BLACK)) {
                if (hasPassed) {
                    currentGameState = GameState.GAME_END;
                    break;
                } else {
                    hasPassed = true;
                    currentGameState = GameState.WHITE_TURN;
                    doTurn();
                    break;
                }
            }

            hasPassed = false;
            if ("Human".equals(blackPlayerChoice.getSelectedItem()))
                break;

            Player player = getPlayer(Turn.BLACK);
            Position p = player.play(board);
            board.put(p.x, p.y, Stone.BLACK);

            boardPanel.repaint();

            currentGameState = GameState.WHITE_TURN;
            doTurn();
            break;
        }
        case WHITE_TURN: {
            if (!board.isPuttableSomewhere(Stone.WHITE)) {
                if (hasPassed) {
                    currentGameState = GameState.GAME_END;
                    break;
                } else {
                    hasPassed = true;
                    currentGameState = GameState.BLACK_TURN;
                    doTurn();
                    break;
                }
            }

            hasPassed = false;
            if ("Human".equals(whitePlayerChoice.getSelectedItem()))
                break;

            Player player = getPlayer(Turn.WHITE);
            Position p = player.play(board);
            board.put(p.x, p.y, Stone.WHITE);

            boardPanel.repaint();

            currentGameState = GameState.BLACK_TURN;
            doTurn();
            break;
        }
        case GAME_END:
            break;
        }
    }

    private static Player getPlayer(Turn turn) {
        String playerName = Turn.BLACK.equals(turn) ? blackPlayerChoice.getSelectedItem() : whitePlayerChoice.getSelectedItem();

        if ("Simple".equals(playerName))
            return new SimpleAIPlayer(turn);
        if ("MinMax".equals(playerName))
            return new MinMaxSimpleAI(turn, 5);
        if ("NegaMax".equals(playerName))
            return new NegaMaxSimpleAI(turn, 5);
        if ("AlphaBeta".equals(playerName))
            return new AlphaBetaEvaluationSimpleAI(turn, 5);
        if ("NegaScout".equals(playerName))
            return new NegaScoutEvaluationSimpleAI(turn, 5);
        if ("Tranposition".equals(playerName))
            return new TranpositionEvaluationSimpleAI(turn, 5);
        if ("Learn".equals(playerName)) {
            try {
                return new LearnedAI(turn, 9, 13);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        assert(false);
        throw new RuntimeException("Unknown Player");
    }

    private static Panel makeBoardPanel() {
        boardPanel.setPreferredSize(new Dimension(640, 640));
        boardPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Point p = e.getPoint();
                int x = (p.x / 80) + 1;
                int y = (p.y / 80) + 1;

                System.out.printf("x, y = %d, %d : %s, %s\n", x, y, blackPlayerChoice.getSelectedItem(), whitePlayerChoice.getSelectedItem());


                if (GameState.BLACK_TURN.equals(currentGameState) && "Human".equals(blackPlayerChoice.getSelectedItem())) {
                    if (board.isPuttable(x, y, Stone.BLACK)) {
                        board.put(x, y, Stone.BLACK);
                        currentGameState = GameState.WHITE_TURN;
                        doTurn();
                    }
                } else if (GameState.WHITE_TURN.equals(currentGameState) && "Human".equals(whitePlayerChoice.getSelectedItem())) {
                    if (board.isPuttable(x, y, Stone.WHITE)) {
                        board.put(x, y, Stone.WHITE);
                        currentGameState = GameState.BLACK_TURN;
                        doTurn();
                    }
                }

                boardPanel.repaint();
            }
        });

        Panel p = new Panel();
        p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
        p.add(Box.createHorizontalGlue());
        p.add(boardPanel);
        p.add(Box.createHorizontalGlue());

        return p;
    }
}
