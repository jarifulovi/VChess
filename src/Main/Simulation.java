package Main;

import Piece.*;

public class Simulation {
    GamePanel gamePanel;
    int collidePieceIndex;
    boolean collision;
    Piece collidePiece;

    public Simulation(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }
    public void simulate(){
        // change the position of running piece after clicked a piece
        gamePanel.runningPiece.x = gamePanel.mouseControl.x - ChessBoard.SQUARE_SIZE/2;
        gamePanel.runningPiece.y = gamePanel.mouseControl.y - ChessBoard.SQUARE_SIZE/2;
        gamePanel.runningPiece.col = gamePanel.runningPiece.getCol(gamePanel.runningPiece.x);
        gamePanel.runningPiece.row = gamePanel.runningPiece.getRow(gamePanel.runningPiece.y);

        // set the default value
        gamePanel.runningPiece.canMove = false;
        collision = false;
        collidePiece = hittingPiece(gamePanel.runningPiece.row,gamePanel.runningPiece.col);

        // own piece collision
        if(collidePiece!=null) collision = (collidePiece.color==gamePanel.runningPiece.color);

    }
    public Piece hittingPiece(int row, int col){

        for(Piece p : gamePanel.simPieces){
            if(p.row==row && p.col==col && (p!=gamePanel.runningPiece)){
                collidePieceIndex = gamePanel.simPieces.indexOf(p);
                return p;
            }
        }
        return null;
    }
    public boolean isOwnPiece(int row,int col,int color){

        for(Piece p : gamePanel.simPieces){
            if(p.row==row && p.col==col && p.color==color) return true;
        }
        return false;
    }
    public void removeEnPassantPawn() {
        // Remove the en passant captured pawn
        if (gamePanel.runningPiece instanceof Pawn) {
            int enPassantRow = gamePanel.enPassantPoint.row;
            int enPassantCol = gamePanel.enPassantPoint.col;

            // Check if the en passant capture conditions are met
            boolean enPassantCapture = (Math.abs(gamePanel.runningPiece.preRow-gamePanel.runningPiece.row)==
                    Math.abs(gamePanel.runningPiece.preCol-gamePanel.runningPiece.col) &&
                    !gamePanel.isOccupied[gamePanel.runningPiece.row][gamePanel.runningPiece.col]);

            if (enPassantCapture) {
                // Remove the captured pawn from simPieces
                if(gamePanel.currentColor==GamePanel.WHITE)
                    gamePanel.simPieces.removeIf(piece -> piece.row == enPassantRow+1 && piece.col == enPassantCol);
                else
                    gamePanel.simPieces.removeIf(piece -> piece.row == enPassantRow-1 && piece.col == enPassantCol);

                // If you need to reference the captured piece, you can do so here
                // Example: Piece capturedPiece = gamePanel.simPieces.stream().filter(piece -> piece.row == enPassantRow && piece.col == enPassantCol).findFirst().orElse(null);
            }
        }
    }
    public void capturePieces(){
        // capture functionality
        if(collidePiece!=null) {
            if(collidePiece.color!=gamePanel.runningPiece.color) {

                gamePanel.isOccupied[collidePiece.row][collidePiece.col] = false;
                gamePanel.simPieces.removeIf(piece -> piece.equals(collidePiece));
            }
        }
    }
}
