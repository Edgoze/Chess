package org.cis120.chess;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Knight extends Piece implements Comparable {

    private static String imageFile;
    public static final int SIZE = 35;
    private BufferedImage img;

    public Knight(
            boolean color,
            int pieceNumber, int row, int column
    ) {
        super(SIZE, SIZE, row, column, color, pieceNumber);

        if (color) {
            this.imageFile = "files/whiteKnight.png";
        } else {

            this.imageFile = "files/blackKnight.png";
        }

        try {
            if (img == null) {
                img = ImageIO.read(new File(imageFile));
            }
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }

    }

    @Override
    public void draw(Graphics g, int xDrawPos, int yDrawPos) {
        g.drawImage(img, xDrawPos, yDrawPos, this.getWidth(), this.getHeight(), null);
    }

    @Override
    public int compareTo(Object o) {
        return getPieceNumber() - ((Piece) o).getPieceNumber();
    }

    @Override
    public boolean checksKing(ChessGame game, int endRow, int endColumn) {
        // REUSING CODE FROM KING, METHOD "PUTTING MYSELF IN CHECK"
        int knightsAtBottom = endRow + 2;
        int knightsCloseBottom = endRow + 1;
        int knightsCloseTop = endRow - 1;
        int knightsAtTop = endRow - 2;
        int leftmostColumn = endColumn - 2;
        int leftColumn = endColumn - 1;
        int rightColumn = endColumn + 1;
        int rightmostColumn = endColumn + 2;
        if (notOutOfBoundsRival(game.getBoard(), knightsAtBottom, leftColumn)
                && game.getBoard()[knightsAtBottom][leftColumn] instanceof King) {
            game.setKingInCheck(game.getBoard()[knightsAtBottom][leftColumn]);
            game.setPieceInCheck(this);
            return true;
        } else if (notOutOfBoundsRival(game.getBoard(), knightsAtBottom, rightColumn)
                && game.getBoard()[knightsAtBottom][rightColumn] instanceof King) {
            game.setKingInCheck(game.getBoard()[knightsAtBottom][rightColumn]);
            game.setPieceInCheck(this);
            return true;
        } else if (notOutOfBoundsRival(game.getBoard(), knightsCloseBottom, leftmostColumn) &&
                game.getBoard()[knightsCloseBottom][leftmostColumn] instanceof King) {
            game.setKingInCheck(game.getBoard()[knightsCloseBottom][leftmostColumn]);
            game.setPieceInCheck(this);
            return true;
        } else if (notOutOfBoundsRival(game.getBoard(), knightsCloseBottom, rightmostColumn) &&
                game.getBoard()[knightsCloseBottom][rightmostColumn] instanceof King) {
            game.setKingInCheck(game.getBoard()[knightsCloseBottom][rightmostColumn]);
            game.setPieceInCheck(this);
            return true;
        } else if (notOutOfBoundsRival(game.getBoard(), knightsCloseTop, leftmostColumn) &&
                game.getBoard()[knightsCloseTop][leftmostColumn] instanceof King) {
            game.setKingInCheck(game.getBoard()[knightsCloseTop][leftmostColumn]);
            game.setPieceInCheck(this);
            return true;
        } else if (notOutOfBoundsRival(game.getBoard(), knightsCloseTop, rightmostColumn) &&
                game.getBoard()[knightsCloseTop][rightmostColumn] instanceof King) {
            game.setKingInCheck(game.getBoard()[knightsCloseTop][rightmostColumn]);
            game.setPieceInCheck(this);
            return true;
        } else if (notOutOfBoundsRival(game.getBoard(), knightsAtTop, leftColumn) &&
                game.getBoard()[knightsAtTop][leftColumn] instanceof King) {
            game.setKingInCheck(game.getBoard()[knightsAtTop][leftColumn]);
            game.setPieceInCheck(this);
            return true;
        } else if (notOutOfBoundsRival(game.getBoard(), knightsAtTop, rightColumn) &&
                game.getBoard()[knightsAtTop][rightColumn] instanceof King) {
            game.setKingInCheck(game.getBoard()[knightsAtTop][rightColumn]);
            game.setPieceInCheck(this);
            return true;
        }
        return false;
    }

    @Override
    public boolean possibleToMove(ChessGame game, int endRow, int endColumn) {
        int myRow = this.getRow();
        int myColumn = this.getColumn();
        if (Math.abs(myRow - endRow) == 2 && Math.abs(myColumn - endColumn) == 1
                && !treasonChecker(game.getBoard(), endRow, endColumn)) {
            return true;
        } else if (Math.abs(myRow - endRow) == 1 && Math.abs(myColumn - endColumn) == 2
                && !treasonChecker(game.getBoard(), endRow, endColumn)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean move(ChessGame game, int endRow, int endColumn) {
        int myRow = this.getRow();
        int myColumn = this.getColumn();

        /*
         * So you can move if the king is in check or, potentially,
         * if you are trying to capture the pieceChecking.
         */
        if ((game.getKingInCheck() == null) || (endColumn == game.getPieceChecking().getColumn()
                && endRow == game.getPieceChecking().getRow())) {
            // Vertical motion
            if (Math.abs(myRow - endRow) == 2 && Math.abs(myColumn - endColumn) == 1
                    && !treasonChecker(game.getBoard(), endRow, endColumn)
                    && myTurn(game.getWhitePlays(), this.getColor())) {
                saveGameForUndo(game);
                game.getBoard()[myRow][myColumn] = null;
                game.getBoard()[endRow][endColumn] = this;
                this.setRow(endRow);
                this.setColumn(endColumn);
                game.setKingInCheck(null);
                game.setPieceInCheck(null);
                if (checksKing(game, endRow, endColumn)) {
                    game.checkForCheckMate();
                }
                return true;
            }

            // Horizontal motion
            if (Math.abs(myRow - endRow) == 1 && Math.abs(myColumn - endColumn) == 2
                    && !treasonChecker(game.getBoard(), endRow, endColumn)
                    && myTurn(game.getWhitePlays(), this.getColor())) {
                saveGameForUndo(game);
                game.getBoard()[myRow][myColumn] = null;
                game.getBoard()[endRow][endColumn] = this;
                this.setRow(endRow);
                this.setColumn(endColumn);
                game.setKingInCheck(null);
                game.setPieceInCheck(null);
                if (checksKing(game, endRow, endColumn)) {
                    game.checkForCheckMate();
                }
                return true;
            }
            // ELSE MOVE IT AND CHECK IF IT INTERFERES WITH THE CHECK
        } else {

            Piece inCaseNotValid = game.getBoard()[endRow][endColumn];

            // Vertical motion
            if (Math.abs(myRow - endRow) == 2 && Math.abs(myColumn - endColumn) == 1
                    && !treasonChecker(game.getBoard(), endRow, endColumn)
                    && myTurn(game.getWhitePlays(), this.getColor())) {
                // I removed this.setRow and setColumn and other things
                game.getBoard()[myRow][myColumn] = null;
                game.getBoard()[endRow][endColumn] = this;
            }

            // Horizontal motion
            if (Math.abs(myRow - endRow) == 1 && Math.abs(myColumn - endColumn) == 2
                    && !treasonChecker(game.getBoard(), endRow, endColumn)
                    && myTurn(game.getWhitePlays(), this.getColor())) {
                game.getBoard()[myRow][myColumn] = null;
                game.getBoard()[endRow][endColumn] = this;
            }

            if (!game.getPieceChecking().checksKing(
                    game,
                    game.getPieceChecking().getRow(), game.getPieceChecking().getColumn()
            )) {
                game.getBoard()[myRow][myColumn] = this;
                game.getBoard()[endRow][endColumn] = inCaseNotValid;
                saveGameForUndo(game);
                game.getBoard()[myRow][myColumn] = null;
                game.getBoard()[endRow][endColumn] = this;
                this.setRow(endRow);
                this.setColumn(endColumn);
                game.setKingInCheck(null);
                game.setPieceInCheck(null);
                if (this.checksKing(game, endRow, endColumn)) {
                    game.checkForCheckMate();
                }
                return true;

            } else {
                game.getBoard()[myRow][myColumn] = this;
                game.getBoard()[endRow][endColumn] = inCaseNotValid;
                return false;
            }
        }
        return false;
    }
}
