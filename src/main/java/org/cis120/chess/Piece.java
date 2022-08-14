package org.cis120.chess;

import java.awt.*;

public abstract class Piece {

    private int width;
    private int height;
    private int row;
    private int column;
    private boolean color;
    private int pieceNumber;

    public Piece(
            int width, int height, int row, int column, boolean color, int pieceNumber
    ) {
        this.width = width;
        this.height = height;
        this.row = row;
        this.column = column;
        this.color = color;
        this.pieceNumber = pieceNumber;
    }

    public int getRow() {
        return this.row;
    }

    public int getColumn() {
        return this.column;
    }

    public boolean getColor() {
        return this.color;
    }

    public void setRow(int newRow) {
        this.row = newRow;
    }

    public void setColumn(int newColumn) {
        this.column = newColumn;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int getPieceNumber() {
        return this.pieceNumber;
    }

    public boolean move(ChessGame game, int endRow, int endColumn) {
        return true;
    }

    public boolean possibleToMove(ChessGame game, int endRow, int endColumn) {
        return true;
    }

    public boolean myTurn(boolean whitePlays, boolean myColor) {
        return (whitePlays && myColor) || (!whitePlays && !myColor);
    }

    public boolean notOutOfBoundsRival(Piece[][] board, int row, int column) {
        return row < 8 && row > -1 && column < 8 && column > -1 &&
                board[row][column] != null && board[row][column].getColor() != this.getColor();
    }

    public boolean checksKing(ChessGame game, int endRow, int endColumn) {
        return false;
    }

    public boolean treasonChecker(Piece[][] board, int endRow, int endColumn) {
        if (board[endRow][endColumn] != null) {
            return board[this.getRow()][this.getColumn()].getColor() == board[endRow][endColumn]
                    .getColor();
        }
        return false;
    }

    public void saveGameForUndo(ChessGame game) {
        // Make deep copy of board
        Piece[][] copy = new Piece[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                copy[i][j] = game.getBoard()[i][j];
            }
        }
        ChessGame saveGame = new ChessGame(
                copy, game.getWhitePlays(),
                game.getKingInCheck(), game.getPieceChecking()
        );
        game.getUndoList().add(saveGame);
    }

    public abstract void draw(Graphics g, int xDrawPos, int yDrawPos);
}
