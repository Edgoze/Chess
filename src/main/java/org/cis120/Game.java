package org.cis120;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Game {
    public static void main(String[] args) {
        Runnable game = new org.cis120.chess.RunChess();
        SwingUtilities.invokeLater(game);
    }
}
