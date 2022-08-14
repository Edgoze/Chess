package org.cis120.chess;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Pawn extends Piece implements Comparable {
    private static String imageFile;
    public static final int SIZE = 35;

    private BufferedImage img;

    public Pawn(
            boolean color,
            int pieceNumber, int row, int column
    ) {
        super(SIZE, SIZE, row, column, color, pieceNumber);

        if (color) {
            this.imageFile = "files/whitePawn.png";
        } else {

            this.imageFile = "files/blackPawn.png";
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
        // Reusing code from King.java, the "puttingMyselfInCheck" method
        if (this.getColor() && ((notOutOfBoundsRival(game.getBoard(), endRow - 1, endColumn - 1) &&
                game.getBoard()[endRow - 1][endColumn - 1] instanceof King))) {
            game.setKingInCheck(game.getBoard()[endRow - 1][endColumn - 1]);
            game.setPieceInCheck(this);
            return true;
        } else if (this.getColor() &&
                (notOutOfBoundsRival(game.getBoard(), endRow - 1, endColumn + 1) &&
                        game.getBoard()[endRow - 1][endColumn + 1] instanceof King)) {
            game.setKingInCheck(game.getBoard()[endRow - 1][endColumn + 1]);
            game.setPieceInCheck(this);
            return true;
        } else if (!this.getColor() &&
                (notOutOfBoundsRival(game.getBoard(), endRow + 1, endColumn - 1) &&
                        game.getBoard()[endRow + 1][endColumn - 1] instanceof King)) {
            game.setKingInCheck(game.getBoard()[endRow + 1][endColumn - 1]);
            game.setPieceInCheck(this);
            return true;
        } else if (!this.getColor() &&
                (notOutOfBoundsRival(game.getBoard(), endRow + 1, endColumn + 1) &&
                        game.getBoard()[endRow + 1][endColumn + 1] instanceof King)) {
            game.setKingInCheck(game.getBoard()[endRow + 1][endColumn + 1]);
            game.setPieceInCheck(this);
            return true;
        }
        return false;
    }

    @Override
    public boolean possibleToMove(ChessGame game, int endRow, int endColumn) {
        int myRow = this.getRow();
        int myColumn = this.getColumn();
        boolean myColor = this.getColor();
        if (myColor && endColumn == myColumn && myRow - endRow == 1
                && game.getBoard()[endRow][endColumn] == null) {
            return true;
        } else if (!myColor && endColumn == myColumn && endRow - myRow == 1
                && game.getBoard()[endRow][endColumn] == null) {
            return true;
        } else if (myColor && endColumn == myColumn && myRow == 6 && endRow == 4 &&
                game.getBoard()[endRow][endColumn] == null
                && game.getBoard()[endRow + 1][endColumn] == null) {
            return true;
        } else if (!myColor && endColumn == myColumn && myRow == 1 && endRow == 3 &&
                game.getBoard()[endRow][endColumn] == null
                && game.getBoard()[endRow - 1][endColumn] == null) {
            return true;
        } else if (myColor && Math.abs(endColumn - myColumn) == 1
                && (myRow - endRow == 1) &&
                game.getBoard()[endRow][endColumn] != null
                && !treasonChecker(game.getBoard(), endRow, endColumn)) {
            return true;
        } else if (!myColor && Math.abs(endColumn - myColumn) == 1
                && (endRow - myRow == 1) &&
                game.getBoard()[endRow][endColumn] != null
                && !treasonChecker(game.getBoard(), endRow, endColumn)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean move(ChessGame game, int endRow, int endColumn) {
        int myRow = this.getRow();
        int myColumn = this.getColumn();
        boolean myColor = this.getColor();

        // So you can move if the king is in check or, potentially, if you are trying to
        // capture the pieceChecking.
        if ((game.getKingInCheck() == null) || (endColumn == game.getPieceChecking().getColumn()
                && endRow == game.getPieceChecking().getRow())) {

            // FORWARD MOTION
            if (myColor && endColumn == myColumn && myRow - endRow == 1
                    && game.getBoard()[endRow][endColumn] == null
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
            } else if (!myColor && endColumn == myColumn && endRow - myRow == 1
                    && game.getBoard()[endRow][endColumn] == null
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

            // INITIAL
            if (myColor && endColumn == myColumn && myRow == 6 &&
                    endRow == 4 && game.getBoard()[endRow][endColumn] == null
                    && game.getBoard()[endRow + 1][endColumn] == null &&
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
            } else if (!myColor && endColumn == myColumn && myRow == 1 && endRow == 3 &&
                    game.getBoard()[endRow][endColumn] == null
                    && game.getBoard()[endRow - 1][endColumn] == null &&
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

            // CAPTURE
            if (myColor && Math.abs(endColumn - myColumn) == 1 && (myRow - endRow == 1) &&
                    game.getBoard()[endRow][endColumn] != null &&
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
            } else if (!myColor && Math.abs(endColumn - myColumn) == 1
                    && (endRow - myRow == 1) &&
                    game.getBoard()[endRow][endColumn] != null
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

        } else {

            Piece inCaseNotValid = game.getBoard()[endRow][endColumn];

            // FORWARD MOTION
            if (myColor && endColumn == myColumn && myRow - endRow == 1
                    && game.getBoard()[endRow][endColumn] == null
                    && myTurn(game.getWhitePlays(), this.getColor())) {
                game.getBoard()[myRow][myColumn] = null;
                game.getBoard()[endRow][endColumn] = this;
            } else if (!myColor && endColumn == myColumn && endRow - myRow == 1 &&
                    game.getBoard()[endRow][endColumn] == null
                    && myTurn(game.getWhitePlays(), this.getColor())) {
                game.getBoard()[myRow][myColumn] = null;
                game.getBoard()[endRow][endColumn] = this;
            }

            // INITIAL
            if (myColor && endColumn == myColumn && myRow == 6 && endRow == 4 &&
                    game.getBoard()[endRow][endColumn] == null
                    && game.getBoard()[endRow + 1][endColumn] == null &&
                    myTurn(game.getWhitePlays(), this.getColor())) {
                game.getBoard()[myRow][myColumn] = null;
                game.getBoard()[endRow][endColumn] = this;
            } else if (!myColor && endColumn == myColumn && myRow == 1 && endRow == 3 &&
                    game.getBoard()[endRow][endColumn] == null
                    && game.getBoard()[endRow - 1][endColumn] == null &&
                    myTurn(game.getWhitePlays(), this.getColor())) {
                game.getBoard()[myRow][myColumn] = null;
                game.getBoard()[endRow][endColumn] = this;
            }

            // CAPTURE
            if (myColor && Math.abs(endColumn - myColumn) == 1
                    && (myRow - endRow == 1) &&
                    game.getBoard()[endRow][endColumn] != null &&
                    !treasonChecker(game.getBoard(), endRow, endColumn)
                    && myTurn(game.getWhitePlays(), this.getColor())) {
                game.getBoard()[myRow][myColumn] = null;
                game.getBoard()[endRow][endColumn] = this;
            } else if (!myColor && Math.abs(endColumn - myColumn) == 1
                    && (endRow - myRow == 1) &&
                    game.getBoard()[endRow][endColumn] != null
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
