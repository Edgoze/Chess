package org.cis120.chess;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RunChess implements Runnable {
    public void run() {
        // NOTE: the 'final' keyword denotes immutability even for local variables.

        // Top-level frame in which game components live
        final JFrame frame = new JFrame("Chess");
        frame.setLocation(300, 300);

        // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Setting up...");
        status_panel.add(status);

        JOptionPane.showMessageDialog(
                frame,
                "If a king cannot escape checkmate " +
                        "then the game is over. Customarily " +
                        "the king is not captured or removed " +
                        "from the board, the game is simply " +
                        "declared over. Each of the 6 \n" +
                        "different kinds of pieces moves " +
                        "differently. Pieces cannot move through " +
                        "other pieces (though the knight can jump " +
                        "over other pieces), and can never " +
                        "move onto a square with one of their \n" +
                        "own pieces. However, they can be " +
                        "moved to take the place of an " +
                        "opponent's piece which is then " +
                        "captured. Pieces are generally moved " +
                        "into positions where they can \n" +
                        "capture other pieces (by landing on " +
                        "their square and then replacing " +
                        "them), defend their own pieces " +
                        "in case of capture or control " +
                        "important squares in the game. \n" +
                        "The king is the most important " +
                        "piece but is one of the weakest. " +
                        "The king can only move one square in " +
                        "any direction - up, down, to the sides," +
                        " and diagonally. If the king has \n" +
                        "not moved yet, and there's a rook " +
                        "on the corner, then it can castle. " +
                        "Castling consists on moving the kind " +
                        "two squares horizontally and putting " +
                        "a rook to the opposite side of its motion. \n" +
                        "The queen is the most powerful piece. " +
                        "She can move in any one straight direction - " +
                        "forward, backward, sideways, or diagonally - " +
                        "as far as possible as long as she does not " +
                        "move through any of her own \n" +
                        "pieces. The rook may move as far as it " +
                        "wants, but only forward, backward, and " +
                        "to the sides The bishop may move as far as " +
                        "it wants, but only diagonally. Each bishop \n" +
                        "starts on one color (light or dark) and must " +
                        "always stay on that color. Knights move in a " +
                        "very different way from the other pieces – \n" +
                        "going two squares in one direction, and then" +
                        "one more move at a 90-degree angle, just like " +
                        "the shape of an “L”. Pawns are unusual because \n" +
                        "they move and capture in different ways: they " +
                        "move forward but capture diagonally. Pawns can \n" +
                        "only move forward one square at a time, " +
                        "except for their very  first move where they " +
                        "can move forward two squares.",
                "Instructions",
                JOptionPane.INFORMATION_MESSAGE
        );

        // Game board
        final ChessGameBoard board = new ChessGameBoard(status);
        frame.add(board, BorderLayout.CENTER);

        // Reset button
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);

        // Note here that when we add an action listener to the reset button, we
        // define it as an anonymous inner class that is an instance of
        // ActionListener with its actionPerformed() method overridden. When the
        // button is pressed, actionPerformed() will be called.
        final JButton reset = new JButton("New Game");
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                board.reset();
            }
        });
        control_panel.add(reset);

        final JButton undo = new JButton("Undo");
        undo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                board.getGame().undo();
                board.updateStatus();
                board.repaint();
            }
        });
        control_panel.add(undo);

        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start the game
        board.reset();
    }
}