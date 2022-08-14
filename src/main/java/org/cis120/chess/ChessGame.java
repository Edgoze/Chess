package org.cis120.chess;

import java.util.LinkedList;

public class ChessGame {
    private Piece[][] board;
    private boolean whitePlays = true;
    private Piece kingInCheck = null;
    private Piece pieceChecking = null;
    private LinkedList<ChessGame> undoList = new LinkedList<>();
    private boolean checkMate = false;

    private boolean whiteKingHasMoved = false;
    private boolean blackKingHasMoved = false;

    private int[] xDrawingPositions = { 6, 56, 106, 156, 206, 256, 306, 356 };
    private int[] yDrawingPositions = { 10, 60, 110, 160, 210, 260, 310, 360 };

    public ChessGame() {
        board = new Piece[8][8];

        Pawn whitePawn1 = new Pawn(true, 1, 6, 0);
        board[6][0] = whitePawn1;
        Pawn whitePawn2 = new Pawn(true, 2, 6, 1);
        board[6][1] = whitePawn2;
        Pawn whitePawn3 = new Pawn(true, 3, 6, 2);
        board[6][2] = whitePawn3;
        Pawn whitePawn4 = new Pawn(true, 4, 6, 3);
        board[6][3] = whitePawn4;
        Pawn whitePawn5 = new Pawn(true, 5, 6, 4);
        board[6][4] = whitePawn5;
        Pawn whitePawn6 = new Pawn(true, 6, 6, 5);
        board[6][5] = whitePawn6;
        Pawn whitePawn7 = new Pawn(true, 7, 6, 6);
        board[6][6] = whitePawn7;
        Pawn whitePawn8 = new Pawn(true, 8, 6, 7);
        board[6][7] = whitePawn8;

        Pawn blackPawn1 = new Pawn(false, 9, 1, 0);
        board[1][0] = blackPawn1;
        Pawn blackPawn2 = new Pawn(false, 10, 1, 1);
        board[1][1] = blackPawn2;
        Pawn blackPawn3 = new Pawn(false, 11, 1, 2);
        board[1][2] = blackPawn3;
        Pawn blackPawn4 = new Pawn(false, 12, 1, 3);
        board[1][3] = blackPawn4;
        Pawn blackPawn5 = new Pawn(false, 13, 1, 4);
        board[1][4] = blackPawn5;
        Pawn blackPawn6 = new Pawn(false, 14, 1, 5);
        board[1][5] = blackPawn6;
        Pawn blackPawn7 = new Pawn(false, 15, 1, 6);
        board[1][6] = blackPawn7;
        Pawn blackPawn8 = new Pawn(false, 16, 1, 7);
        board[1][7] = blackPawn8;

        Rook whiteRook1 = new Rook(true, 17, 7, 0);
        board[7][0] = whiteRook1;
        Rook whiteRook2 = new Rook(true, 18, 7, 7);
        board[7][7] = whiteRook2;

        Rook blackRook1 = new Rook(false, 19, 0, 0);
        board[0][0] = blackRook1;
        Rook blackRook2 = new Rook(false, 20, 0, 7);
        board[0][7] = blackRook2;

        Knight whiteKnight1 = new Knight(true, 21, 7, 1);
        board[7][1] = whiteKnight1;
        Knight whiteKnight2 = new Knight(true, 22, 7, 6);
        board[7][6] = whiteKnight2;

        Knight blackKnight1 = new Knight(false, 23, 0, 1);
        board[0][1] = blackKnight1;
        Knight blackKnight2 = new Knight(false, 24, 0, 6);
        board[0][6] = blackKnight2;

        Bishop whiteBishop1 = new Bishop(true, 25, 7, 2);
        board[7][2] = whiteBishop1;
        Bishop whiteBishop2 = new Bishop(true, 26, 7, 5);
        board[7][5] = whiteBishop2;
        Bishop blackBishop1 = new Bishop(false, 27, 0, 2);
        board[0][2] = blackBishop1;
        Bishop blackBishop2 = new Bishop(false, 28, 0, 5);
        board[0][5] = blackBishop2;

        Queen whiteQueen = new Queen(true, 29, 7, 3);
        board[7][3] = whiteQueen;
        Queen blackQueen = new Queen(false, 30, 0, 3);
        board[0][3] = blackQueen;

        King whiteKing = new King(true, 31, 7, 4);
        board[7][4] = whiteKing;
        King blackKing = new King(false, 30, 0, 4);
        board[0][4] = blackKing;

    }

    public Piece[][] getBoard() {
        return board;
    }

    public boolean getWhitePlays() {
        return whitePlays;
    }

    public void toggleWhitePlays() {
        this.whitePlays = !this.whitePlays;
    }

    public Piece getKingInCheck() {
        return kingInCheck;
    }

    public void setKingInCheck(Piece newKing) {
        this.kingInCheck = newKing;
    }

    public Piece getPieceChecking() {
        return pieceChecking;
    }

    public void setPieceInCheck(Piece newChecker) {
        this.pieceChecking = newChecker;
    }

    public LinkedList<ChessGame> getUndoList() {
        return undoList;
    }

    public boolean getCheckMate() {
        return checkMate;
    }

    public boolean getWhiteKingHasMoved() {
        return whiteKingHasMoved;
    }

    public void theWhiteKingHasMoved() {
        this.whiteKingHasMoved = true;
    }

    public boolean getBlackKingHasMoved() {
        return blackKingHasMoved;
    }

    public void theBlackKingHasMoved() {
        this.blackKingHasMoved = true;
    }

    public int[] getxDrawingPositions() {
        return xDrawingPositions;
    }

    public int[] getyDrawingPositions() {
        return yDrawingPositions;
    }

    public ChessGame(
            Piece[][] board, boolean whitePlays, Piece kingInCheck,
            Piece pieceChecking
    ) {
        this.board = board;
        this.whitePlays = whitePlays;
        this.kingInCheck = kingInCheck;
        this.pieceChecking = pieceChecking;
    }

    public void undo() {
        if (!this.undoList.isEmpty()) {
            ChessGame previousGame = this.undoList.removeLast();
            this.board = previousGame.board;

            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (board[i][j] != null) {
                        board[i][j].setRow(i);
                        board[i][j].setColumn(j);
                    }
                }
            }
            this.whitePlays = previousGame.whitePlays;
            this.kingInCheck = previousGame.kingInCheck;
            this.pieceChecking = previousGame.pieceChecking;
        }
    }

    public void checkForCheckMate() {
        // Check if king can escape
        King kingInThreat = (King) kingInCheck;
        for (int i = kingInThreat.getRow() - 1; i <= kingInThreat.getRow() + 1; i++) {
            for (int j = kingInThreat.getColumn() - 1; j <= kingInThreat.getColumn() + 1; j++) {
                if (i < 8 && i > -1 && j < 8 && j > -1 &&
                        !kingInThreat.puttingMyselfInCheck(board, i, j)
                        && board[i][j] != null
                        && board[i][j].getColor() != kingInThreat.getColor()) {
                    // Return without toggling chessmate
                    return;
                }
                if (i < 8 && i > -1 && j < 8 && j > -1 &&
                        !kingInThreat.puttingMyselfInCheck(board, i, j)
                        && board[i][j] == null) {
                    // Return without toggling chessmate
                    return;
                }
            }
        }

        // Check if a piece of the king's team can capture the piece
        int rowOfThreat = pieceChecking.getRow();
        int columnOfThreat = pieceChecking.getColumn();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] != null) {
                    if (!(board[i][j] instanceof King) &&
                            board[i][j].getColor() == kingInThreat.getColor() &&
                            board[i][j].possibleToMove(this, rowOfThreat, columnOfThreat)) {
                        // return without toggling checkmate
                        return;
                    }
                }
            }
        }

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] != null) {
                    int pieceInitialRow = board[i][j].getRow();
                    int pieceInitialColumn = board[i][j].getColumn();
                    for (int x = 0; x < 8; x++) {
                        for (int y = 0; y < 8; y++) {
                            if (!(board[i][j] instanceof King) &&
                                    board[i][j].getColor() == kingInThreat.getColor() &&
                                    x != i && y != j &&
                                    board[i][j].possibleToMove(this, x, y)) {
                                Piece pieceThatIAmMoving =
                                        board[pieceInitialRow][pieceInitialColumn];
                                Piece inCaseNotValid = board[x][y];
                                board[pieceInitialRow][pieceInitialColumn] = null;
                                board[x][y] = pieceThatIAmMoving;

                                if (!pieceChecking.checksKing(
                                        this,
                                        pieceChecking.getRow(),
                                        pieceChecking.getColumn()
                                )) {
                                    board[pieceInitialRow][pieceInitialColumn] = pieceThatIAmMoving;
                                    board[x][y] = inCaseNotValid;
                                    // return without toggling checkmate
                                    return;
                                }
                                board[pieceInitialRow][pieceInitialColumn] = pieceThatIAmMoving;
                                board[x][y] = inCaseNotValid;
                            }
                        }
                    }
                }
            }
        }

        checkMate = true;

    }

}
