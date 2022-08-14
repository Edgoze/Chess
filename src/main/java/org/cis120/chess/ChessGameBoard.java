package org.cis120.chess;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@SuppressWarnings("serial")
public class ChessGameBoard extends JPanel {

    private JLabel status; // current status text
    private ChessGame game;
    private Piece selectedPiece = null;

    // Game constants
    public static final int BOARD_WIDTH = 400;
    public static final int BOARD_HEIGHT = 400;

    /**
     * Initializes the game board.
     */
    public ChessGameBoard(JLabel statusInit) {
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Enable keyboard focus on the court area. When this component has the
        // keyboard focus, key events are handled by its key listener.
        setFocusable(true);

        status = statusInit; // initializes the status JLabel

        /*
         * Listens for mouseclicks. Updates the model, then updates the game
         * board based off of the updated model.
         */
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                Point p = e.getPoint();
                int column = p.x / 50;
                int row = p.y / 50;
                if (game.getBoard()[row][column] != null && selectedPiece == null) {
                    selectedPiece = game.getBoard()[row][column];
                } else if (!game.getCheckMate() && selectedPiece != null) {

                    if (selectedPiece.move(game, row, column)) {
                        game.toggleWhitePlays();
                    }
                    selectedPiece = null;
                }
                updateStatus(); // updates the status JLabel
                repaint(); // repaints the game board
            }
        });

        game = new ChessGame();
    }

    public ChessGame getGame() {
        return this.game;
    }

    /**
     * (Re-)sets the game to its initial state.
     */
    public void reset() {
        game = new ChessGame();
        status.setText("White plays");
        repaint();

        // Makes sure this component has keyboard/mouse focus
        requestFocusInWindow();
    }

    /**
     * Updates the JLabel to reflect the current state of the game.
     */
    public void updateStatus() {

        if (game.getKingInCheck() != null) {
            status.setText("Check!");
        } else if (game.getWhitePlays()) {
            status.setText("White Plays");
        } else {
            status.setText("Black Plays");
        }

        if (game.getCheckMate() && game.getKingInCheck().getColor()) {
            status.setText("Checkmate! Blacks win!");
        } else if (game.getCheckMate() && !game.getKingInCheck().getColor()) {
            status.setText("Checkmate! Whites win!");
        }

    }

    /**
     * Draws the game board.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draws board grid
        int xPositionForLine = 50;
        int yPositionForLine = 50;
        for (int i = 0; i < 8; i++) {
            g.drawLine(xPositionForLine, 0, xPositionForLine, 400);
            xPositionForLine += 50;
            g.drawLine(0, yPositionForLine, 400, yPositionForLine);
            yPositionForLine += 50;
        }

        // Draws black boxes
        int xPositionForBlackBox = 0;
        int yPositionForBlackBox = 350;
        int rowParity = 1;
        g.setColor(Color.GRAY);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 4; j++) {
                g.fillRect(xPositionForBlackBox, yPositionForBlackBox, 50, 50);
                xPositionForBlackBox += 100;
            }
            rowParity += 1;
            if (rowParity % 2 == 0) {
                xPositionForBlackBox = 50;
            } else {
                xPositionForBlackBox = 0;
            }
            yPositionForBlackBox -= 50;
        }
        g.setColor(Color.BLACK);

        // Draws pieces
        for (int i = 0; i < game.getBoard().length; i++) {
            for (int j = 0; j < game.getBoard()[i].length; j++) {
                if (game.getBoard()[i][j] != null) {
                    game.getBoard()[i][j].draw(
                            g, game.getxDrawingPositions()[j],
                            game.getyDrawingPositions()[i]
                    );
                }
            }
        }
    }

    /**
     * Returns the size of the game board.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
    }
}
