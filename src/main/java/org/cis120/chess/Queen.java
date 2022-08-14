package org.cis120.chess;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Queen extends Piece implements Comparable {
    private static String imageFile;
    public static final int SIZE = 35;

    private BufferedImage img;

    public Queen(
            boolean color,
            int pieceNumber, int row, int column
    ) {
        super(SIZE, SIZE, row, column, color, pieceNumber);

        if (color) {
            this.imageFile = "files/whiteQueen.png";
        } else {

            this.imageFile = "files/blackQueen.png";
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

    private boolean columnMovementChecker(int endRow, Piece[][] board) {
        boolean freePath = true;
        if (endRow < this.getRow()) {
            for (int i = endRow + 1; i < this.getRow(); i++) {
                freePath = freePath && board[i][this.getColumn()] == null;
            }
        } else {
            for (int i = this.getRow() + 1; i < endRow; i++) {
                freePath = freePath && board[i][this.getColumn()] == null;
            }
        }
        return freePath;
    }

    private boolean rowMovementChecker(int endColumn, Piece[][] board) {
        boolean freePath = true;
        if (endColumn < this.getColumn()) {
            for (int i = endColumn + 1; i < this.getColumn(); i++) {
                freePath = freePath && board[this.getRow()][i] == null;
            }
        } else {
            for (int i = this.getColumn() + 1; i < endColumn; i++) {
                freePath = freePath && board[this.getRow()][i] == null;
            }
        }
        return freePath;
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
        // Code reused from King.java
        // Check left horizontal
        int left = endColumn - 1;
        boolean firstInstanceOfPiece = false;
        while (left > -1 && !firstInstanceOfPiece) {

            if (left == this.getColumn() && left != 0) {
                left -= 1;
            }

            if (game.getBoard()[endRow][left] != null) {
                if (game.getBoard()[endRow][left].getColor() != this.getColor()
                        && (game.getBoard()[endRow][left] instanceof King)) {
                    game.setKingInCheck(game.getBoard()[endRow][left]);
                    game.setPieceInCheck(this);

                    return true;
                }
                firstInstanceOfPiece = true;
            }
            left -= 1;
        }
        // Check right horizontal
        int right = endColumn + 1;
        firstInstanceOfPiece = false;
        while (right < 8 && !firstInstanceOfPiece) {

            if (right == this.getColumn() && right != 7) {
                right += 1;
            }

            if (game.getBoard()[endRow][right] != null) {
                if (game.getBoard()[endRow][right].getColor() != this.getColor()
                        && (game.getBoard()[endRow][right] instanceof King)) {
                    game.setKingInCheck(game.getBoard()[endRow][right]);
                    game.setPieceInCheck(this);

                    return true;
                }
                firstInstanceOfPiece = true;
            }
            right += 1;
        }
        // Check up vertical
        int up = endRow - 1;
        firstInstanceOfPiece = false;
        while (up > -1 && !firstInstanceOfPiece) {

            if (up == this.getRow() && up != 0) {
                up -= 1;
            }

            if (game.getBoard()[up][endColumn] != null) {
                if (game.getBoard()[up][endColumn].getColor() != this.getColor()
                        && (game.getBoard()[up][endColumn] instanceof King)) {
                    game.setKingInCheck(game.getBoard()[up][endColumn]);
                    game.setPieceInCheck(this);

                    return true;
                }
                firstInstanceOfPiece = true;
            }
            up -= 1;
        }
        // Check down vertical
        int down = endRow + 1;
        firstInstanceOfPiece = false;
        while (down < 8 && !firstInstanceOfPiece) {

            if (down == this.getRow() && down != 7) {
                down += 1;
            }

            if (game.getBoard()[down][endColumn] != null) {
                if (game.getBoard()[down][endColumn].getColor() != this.getColor()
                        && (game.getBoard()[down][endColumn] instanceof King)) {
                    game.setKingInCheck(game.getBoard()[down][endColumn]);
                    game.setPieceInCheck(this);

                    return true;
                }
                firstInstanceOfPiece = true;
            }
            down += 1;
        }

        // Checking upper right diagonal
        up = endRow - 1;
        right = endColumn + 1;
        firstInstanceOfPiece = false;
        while (up > -1 && right < 8 && !firstInstanceOfPiece) {

            if (up == this.getRow() && right == this.getColumn() && up != 0 && right != 7) {
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

            if (down == this.getRow() && right == this.getColumn() && down != 7 && right != 7) {
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

            if (up == this.getRow() && left == this.getColumn() && up != 0 && left != 0) {
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

            if (down == this.getRow() && left == this.getColumn() && down != 7 && left != 0) {
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
        if (endColumn == myColumn && columnMovementChecker(endRow, game.getBoard())
                && !treasonChecker(game.getBoard(), endRow, endColumn)) {
            return true;
        } else if (endRow == myRow && rowMovementChecker(endColumn, game.getBoard())
                && !treasonChecker(game.getBoard(), endRow, endColumn)) {
            return true;
        } else if (Math.abs(endColumn - myColumn) == Math.abs(myRow - endRow) &&
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

        if ((game.getKingInCheck() == null) || (endColumn == game.getPieceChecking().getColumn()
                && endRow == game.getPieceChecking().getRow())) {

            // COLUMN MOTION
            if (endColumn == myColumn && columnMovementChecker(endRow, game.getBoard())
                    && !treasonChecker(game.getBoard(), endRow, endColumn) &&
                    myTurn(game.getWhitePlays(), this.getColor())) {
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

            // ROW MOTION
            if (endRow == myRow && rowMovementChecker(endColumn, game.getBoard())
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

            // DIAGONAL MOTION
            if (Math.abs(endColumn - myColumn) == Math.abs(myRow - endRow) &&
                    diagonalPathChecker(endRow, endColumn, game.getBoard())
                    && !treasonChecker(game.getBoard(), endRow, endColumn) &&
                    myTurn(game.getWhitePlays(), this.getColor())) {
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

            // COLUMN MOTION
            if (endColumn == myColumn && columnMovementChecker(endRow, game.getBoard())
                    && !treasonChecker(game.getBoard(), endRow, endColumn)
                    && myTurn(game.getWhitePlays(), this.getColor())) {
                game.getBoard()[myRow][myColumn] = null;
                game.getBoard()[endRow][endColumn] = this;
            }

            // ROW MOTION
            if (endRow == myRow && rowMovementChecker(endColumn, game.getBoard())
                    && !treasonChecker(game.getBoard(), endRow, endColumn)
                    && myTurn(game.getWhitePlays(), this.getColor())) {
                game.getBoard()[myRow][myColumn] = null;
                game.getBoard()[endRow][endColumn] = this;
            }

            // DIAGONAL MOTION
            if (Math.abs(endColumn - myColumn) == Math.abs(myRow - endRow) &&
                    diagonalPathChecker(endRow, endColumn, game.getBoard())
                    && !treasonChecker(game.getBoard(), endRow, endColumn) &&
                    myTurn(game.getWhitePlays(), this.getColor())) {
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
