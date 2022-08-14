package org.cis120.chess;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class King extends Piece implements Comparable {
    private static String imageFile;
    public static final int SIZE = 35;

    private BufferedImage img;

    public King(
            boolean color,
            int pieceNumber, int row, int column
    ) {
        super(SIZE, SIZE, row, column, color, pieceNumber);

        if (color) {
            this.imageFile = "files/whiteKing.png";
        } else {

            this.imageFile = "files/blackKing.png";
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

    public boolean puttingMyselfInCheck(Piece[][] board, int endRow, int endColumn) {

        // CHECKING FOR KNIGHTS
        int knightsAtBottom = endRow + 2;
        int knightsCloseBottom = endRow + 1;
        int knightsCloseTop = endRow - 1;
        int knightsAtTop = endRow - 2;
        int leftmostColumn = endColumn - 2;
        int leftColumn = endColumn - 1;
        int rightColumn = endColumn + 1;
        int rightmostColumn = endColumn + 2;
        if (notOutOfBoundsRival(board, knightsAtBottom, leftColumn)
                && board[knightsAtBottom][leftColumn] instanceof Knight
                || notOutOfBoundsRival(board, knightsAtBottom, rightColumn)
                        && board[knightsAtBottom][rightColumn] instanceof Knight
                || notOutOfBoundsRival(board, knightsCloseBottom, leftmostColumn) &&
                        board[knightsCloseBottom][leftmostColumn] instanceof Knight
                || notOutOfBoundsRival(board, knightsCloseBottom, rightmostColumn) &&
                        board[knightsCloseBottom][rightmostColumn] instanceof Knight
                || notOutOfBoundsRival(board, knightsCloseTop, leftmostColumn) &&
                        board[knightsCloseTop][leftmostColumn] instanceof Knight
                || notOutOfBoundsRival(board, knightsCloseTop, rightmostColumn) &&
                        board[knightsCloseTop][rightmostColumn] instanceof Knight
                || notOutOfBoundsRival(board, knightsAtTop, leftColumn) &&
                        board[knightsAtTop][leftColumn] instanceof Knight
                || notOutOfBoundsRival(board, knightsAtTop, rightColumn) &&
                        board[knightsAtTop][rightColumn] instanceof Knight) {
            return true;
        }

        // CHECKING FOR PAWNS
        if (this.getColor() && ((notOutOfBoundsRival(board, endRow - 1, endColumn - 1) &&
                board[endRow - 1][endColumn - 1] instanceof Pawn)
                ||
                (notOutOfBoundsRival(board, endRow - 1, endColumn + 1) &&
                        board[endRow - 1][endColumn + 1] instanceof Pawn))) {
            return true;
        } else if (!this.getColor() && ((notOutOfBoundsRival(board, endRow + 1, endColumn - 1) &&
                board[endRow + 1][endColumn - 1] instanceof Pawn)
                ||
                (notOutOfBoundsRival(board, endRow + 1, endColumn + 1) &&
                        board[endRow + 1][endColumn + 1] instanceof Pawn))) {
            return true;
        }

        // CHECKING FOR ROOKS AND QUEENS
        // Check left horizontal
        int left = endColumn - 1;
        boolean firstInstanceOfPiece = false;
        while (left > -1 && !firstInstanceOfPiece) {

            if (left == this.getColumn() && left != 0) {
                left -= 1;
            }

            if (board[endRow][left] != null) {
                if (board[endRow][left].getColor() != this.getColor()
                        && (board[endRow][left] instanceof Rook ||
                                board[endRow][left] instanceof Queen)) {
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

            if (board[endRow][right] != null) {
                if (board[endRow][right].getColor() != this.getColor()
                        && (board[endRow][right] instanceof Rook ||
                                board[endRow][right] instanceof Queen)) {
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

            if (board[up][endColumn] != null) {
                if (board[up][endColumn].getColor() != this.getColor()
                        && (board[up][endColumn] instanceof Rook
                                || board[up][endColumn] instanceof Queen)) {
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

            if (board[down][endColumn] != null) {
                if (board[down][endColumn].getColor() != this.getColor()
                        && (board[down][endColumn] instanceof Rook
                                || board[down][endColumn] instanceof Queen)) {
                    return true;
                }
                firstInstanceOfPiece = true;
            }
            down += 1;
        }

        // CHECKING FOR BISHOPS AND QUEENS
        // Checking upper right diagonal
        up = endRow - 1;
        right = endColumn + 1;
        firstInstanceOfPiece = false;
        while (up > -1 && right < 8 && !firstInstanceOfPiece) {

            if (up == this.getRow() && right == this.getColumn()
                    && up != 0 && right != 7) {
                up -= 1;
                right += 1;
            }

            if (board[up][right] != null) {
                if (board[up][right].getColor() != this.getColor()
                        && (board[up][right] instanceof Bishop
                                || board[up][right] instanceof Queen)) {
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

            if (board[down][right] != null) {
                if (board[down][right].getColor() != this.getColor()
                        && (board[down][right] instanceof Bishop
                                || board[down][right] instanceof Queen)) {
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
        while (up > -1 && left > -1 && !firstInstanceOfPiece
                && up != 0 && left != 0) {

            if (up == this.getRow() && left == this.getColumn()) {
                up -= 1;
                left -= 1;
            }

            if (board[up][left] != null) {
                if (board[up][left].getColor() != this.getColor()
                        && (board[up][left] instanceof Bishop
                                || board[up][left] instanceof Queen)) {
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
        while (down < 8 && left > 0 && !firstInstanceOfPiece
                && down != 7 && left != 0) {

            if (down == this.getRow() && left == this.getColumn()) {
                down += 1;
                left -= 1;
            }

            if (board[down][left] != null) {
                if (board[down][left].getColor() != this.getColor()
                        && (board[down][left] instanceof Bishop
                                || board[down][left] instanceof Queen)) {
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
    public boolean move(ChessGame game, int endRow, int endColumn) {
        int myRow = this.getRow();
        int myColumn = this.getColumn();

        // COLUMN MOTION
        if (endColumn == myColumn && Math.abs(endRow - myRow) == 1
                && !treasonChecker(game.getBoard(), endRow, endColumn)
                && !puttingMyselfInCheck(game.getBoard(), endRow, endColumn)
                && myTurn(game.getWhitePlays(), this.getColor())) {
            saveGameForUndo(game);
            game.getBoard()[myRow][myColumn] = null;
            game.getBoard()[endRow][endColumn] = this;
            this.setRow(endRow);
            this.setColumn(endColumn);
            game.setKingInCheck(null);
            game.setPieceInCheck(null);
            if (this.getColor()) {
                game.theWhiteKingHasMoved();
            } else {
                game.theBlackKingHasMoved();
            }
            return true;
        }

        // ROW MOTION
        if (endRow == myRow && Math.abs(endColumn - myColumn) == 1
                && !treasonChecker(game.getBoard(), endRow, endColumn)
                && !puttingMyselfInCheck(game.getBoard(), endRow, endColumn)
                && myTurn(game.getWhitePlays(), this.getColor())) {
            saveGameForUndo(game);
            game.getBoard()[myRow][myColumn] = null;
            game.getBoard()[endRow][endColumn] = this;
            this.setRow(endRow);
            this.setColumn(endColumn);
            game.setKingInCheck(null);
            game.setPieceInCheck(null);
            if (this.getColor()) {
                game.theWhiteKingHasMoved();
            } else {
                game.theBlackKingHasMoved();
            }
            return true;
        }

        // DIAGONAL MOTION
        if (Math.abs(endColumn - myColumn) == 1 && Math.abs(myRow - endRow) == 1
                && !treasonChecker(game.getBoard(), endRow, endColumn)
                && !puttingMyselfInCheck(game.getBoard(), endRow, endColumn)
                && myTurn(game.getWhitePlays(), this.getColor())) {
            saveGameForUndo(game);
            game.getBoard()[myRow][myColumn] = null;
            game.getBoard()[endRow][endColumn] = this;
            this.setRow(endRow);
            this.setColumn(endColumn);
            game.setKingInCheck(null);
            game.setPieceInCheck(null);
            if (this.getColor()) {
                game.theWhiteKingHasMoved();
            } else {
                game.theBlackKingHasMoved();
            }
            return true;
        }

        boolean shortWhiteCastling = game.getKingInCheck() == null &&
                this.getColor() &&
                !game.getWhiteKingHasMoved() && myRow == 7 &&
                myColumn == 4 && endRow == 7 && endColumn == 6
                && game.getBoard()[7][5] == null &&
                game.getBoard()[7][6] == null && game.getBoard()[7][7] instanceof Rook;
        boolean longWhiteCastling = game.getKingInCheck() == null &&
                this.getColor() &&
                !game.getWhiteKingHasMoved() && myRow == 7 &&
                myColumn == 4 && endRow == 7 && endColumn == 2
                && game.getBoard()[7][3] == null &&
                game.getBoard()[7][2] == null && game.getBoard()[7][1] == null
                && game.getBoard()[7][0] instanceof Rook;
        boolean shortBlackCastling = game.getKingInCheck() == null &&
                !this.getColor() &&
                !game.getBlackKingHasMoved() && myRow == 0 &&
                myColumn == 4 && endRow == 0 && endColumn == 6
                && game.getBoard()[0][5] == null && game.getBoard()[0][6] == null
                && game.getBoard()[0][7] instanceof Rook;
        boolean longBlackCastling = game.getKingInCheck() == null
                && !this.getColor() &&
                !game.getBlackKingHasMoved() && myRow == 0
                && myColumn == 4 && endRow == 0 && endColumn == 2
                && game.getBoard()[0][3] == null && game.getBoard()[0][2] == null
                && game.getBoard()[0][1] == null
                && game.getBoard()[0][0] instanceof Rook;

        // Castling
        if (shortWhiteCastling) {
            saveGameForUndo(game);
            Piece theRook = game.getBoard()[7][7];
            game.getBoard()[myRow][myColumn] = null;
            game.getBoard()[endRow][endColumn] = this;
            this.setRow(endRow);
            this.setColumn(endColumn);
            game.getBoard()[7][7] = null;
            game.getBoard()[7][5] = theRook;
            theRook.setRow(7);
            theRook.setColumn(5);
            return true;
        } else if (longWhiteCastling) {
            saveGameForUndo(game);
            Piece theRook = game.getBoard()[7][0];
            game.getBoard()[myRow][myColumn] = null;
            game.getBoard()[endRow][endColumn] = this;
            this.setRow(endRow);
            this.setColumn(endColumn);
            game.getBoard()[7][0] = null;
            game.getBoard()[7][3] = theRook;
            theRook.setRow(7);
            theRook.setColumn(3);
            return true;
        } else if (shortBlackCastling) {
            saveGameForUndo(game);
            Piece theRook = game.getBoard()[0][7];
            game.getBoard()[myRow][myColumn] = null;
            game.getBoard()[endRow][endColumn] = this;
            this.setRow(endRow);
            this.setColumn(endColumn);
            game.getBoard()[0][7] = null;
            game.getBoard()[0][5] = theRook;
            theRook.setRow(0);
            theRook.setColumn(5);
            return true;
        } else if (longBlackCastling) {
            saveGameForUndo(game);
            Piece theRook = game.getBoard()[0][0];
            game.getBoard()[myRow][myColumn] = null;
            game.getBoard()[endRow][endColumn] = this;
            this.setRow(endRow);
            this.setColumn(endColumn);
            game.getBoard()[0][0] = null;
            game.getBoard()[0][3] = theRook;
            theRook.setRow(0);
            theRook.setColumn(3);
            return true;
        }

        return false;
    }
}
