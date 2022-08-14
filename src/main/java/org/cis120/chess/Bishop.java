package org.cis120.chess;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Bishop extends Piece implements Comparable {
    private static String imageFile;
    public static final int SIZE = 35;

    private BufferedImage img;

    public Bishop(
            boolean color,
            int pieceNumber, int row, int column
    ) {
        super(SIZE, SIZE, row, column, color, pieceNumber);

        if (color) {
            this.imageFile = "files/whiteBishop.png";
        } else {

            this.imageFile = "files/blackBishop.png";
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

    private boolean diagonalPathChecker(int endRow, int endColumn, Piece[][] board) {
        boolean freePath = true;
        if (endColumn > this.getColumn() && endRow < this.getRow()) {
            int columnToCheck = this.getColumn() + 1;
            int rowToCheck = this.getRow() - 1;
            while (columnToCheck < endColumn && rowToCheck > endRow) {
                freePath = freePath && board[rowToCheck][columnToCheck] == null;
                columnToCheck += 1;
                rowToCheck -= 1;
            }
        } else if (endColumn > this.getColumn() && endRow > this.getRow()) {
            int columnToCheck = this.getColumn() + 1;
            int rowToCheck = this.getRow() + 1;
            while (columnToCheck < endColumn && rowToCheck < endRow) {
                freePath = freePath && board[rowToCheck][columnToCheck] == null;
                columnToCheck += 1;
                rowToCheck += 1;
            }
        } else if (endColumn < this.getColumn() && endRow < this.getRow()) {
            int columnToCheck = this.getColumn() - 1;
            int rowToCheck = this.getRow() - 1;
            while (columnToCheck > endColumn && rowToCheck > endRow) {
                freePath = freePath && board[rowToCheck][columnToCheck] == null;
                columnToCheck -= 1;
                rowToCheck -= 1;
            }
        } else if (endColumn < this.getColumn() && endRow > this.getRow()) {
            int columnToCheck = this.getColumn() - 1;
            int rowToCheck = this.getRow() + 1;
            while (columnToCheck > endColumn && rowToCheck < endRow) {
                freePath = freePath && board[rowToCheck][columnToCheck] == null;
                columnToCheck -= 1;
                rowToCheck += 1;
            }
        }

        return freePath;
    }

    @Override
    public boolean checksKing(ChessGame game, int endRow, int endColumn) {
        // Reusing code from King.java
        // CHECKING FOR BISHOPS AND QUEENS
        // Checking upper right diagonal
        int left = endColumn - 1;
        int right = endColumn + 1;
        int up = endRow - 1;
        int down = endRow + 1;
        boolean firstInstanceOfPiece = false;

        up = endRow - 1;
        right = endColumn + 1;
        firstInstanceOfPiece = false;
        while (up > -1 && right < 8 && !firstInstanceOfPiece) {

            if (up == this.getRow() && right == this.getColumn()
                    && up != 0 && right != 7) {
                up -= 1;
                right += 1;
            }

            if (game.getBoard()[up][right] != null) {
                if (game.getBoard()[up][right].getColor() != this.getColor()
                        && (game.getBoard()[up][right] instanceof King)) {
                    game.setKingInCheck(game.getBoard()[up][right]);
                    game.setPieceInCheck(this);
                    return true;
                }
                firstInstanceOfPiece = true;
            }
            up -= 1;
            right += 1;
        }
        // Checking lower right diagonal
        down = endRow + 1;
        right = endColumn + 1;
        firstInstanceOfPiece = false;
        while (down < 8 && right < 8 && !firstInstanceOfPiece) {

            if (down == this.getRow() && right == this.getColumn()
                    && down != 7 && right != 7) {
                down += 1;
                right += 1;
            }

            if (game.getBoard()[down][right] != null) {
                if (game.getBoard()[down][right].getColor() != this.getColor()
                        && (game.getBoard()[down][right] instanceof King)) {
                    game.setKingInCheck(game.getBoard()[down][right]);
                    game.setPieceInCheck(this);
                    return true;
                }
                firstInstanceOfPiece = true;
            }
            down += 1;
            right += 1;
        }
        // Checking upper left diagonal
        up = endRow - 1;
        left = endColumn - 1;
        firstInstanceOfPiece = false;
        while (up > -1 && left > -1 && !firstInstanceOfPiece) {

            if (up == this.getRow() && left == this.getColumn()
                    && up != 0 && left != 0) {
                up -= 1;
                left -= 1;
            }

            if (game.getBoard()[up][left] != null) {
                if (game.getBoard()[up][left].getColor() != this.getColor()
                        && (game.getBoard()[up][left] instanceof King)) {
                    game.setKingInCheck(game.getBoard()[up][left]);
                    game.setPieceInCheck(this);
                    return true;
                }
                firstInstanceOfPiece = true;
            }
            up -= 1;
            left -= 1;
        }
        // Checking lower left diagonal
        down = endRow + 1;
        left = endColumn - 1;
        firstInstanceOfPiece = false;
        while (down < 8 && left > 0 && !firstInstanceOfPiece) {

            if (down == this.getRow() && left == this.getColumn()
                    && down != 7 && left != 0) {
                down += 1;
                left -= 1;
            }

            if (game.getBoard()[down][left] != null) {
                if (game.getBoard()[down][left].getColor() != this.getColor()
                        && (game.getBoard()[down][left] instanceof King)) {
                    game.setKingInCheck(game.getBoard()[down][left]);
                    game.setPieceInCheck(this);

                    return true;
                }
                firstInstanceOfPiece = true;
            }
            down += 1;
            left -= 1;
        }
        return false;
    }

    @Override
    public boolean possibleToMove(ChessGame game, int endRow, int endColumn) {
        int myRow = this.getRow();
        int myColumn = this.getColumn();
        if (Math.abs(endColumn - myColumn) == Math.abs(myRow - endRow) &&
                diagonalPathChecker(endRow, endColumn, game.getBoard())
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
            // MOTION
            if (Math.abs(endColumn - myColumn) == Math.abs(myRow - endRow) &&
                    diagonalPathChecker(endRow, endColumn, game.getBoard()) &&
                    !treasonChecker(game.getBoard(), endRow, endColumn)
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
        } else {

            Piece inCaseNotValid = game.getBoard()[endRow][endColumn];

            // MOTION
            if (Math.abs(endColumn - myColumn) == Math.abs(myRow - endRow) &&
                    diagonalPathChecker(endRow, endColumn, game.getBoard()) &&
                    !treasonChecker(game.getBoard(), endRow, endColumn)
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
