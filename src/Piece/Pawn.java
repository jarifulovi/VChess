package Piece;

import Main.GamePanel;
import Main.Point;

public class Pawn extends Piece{


    public Pawn(GamePanel gamePanel,int color,int row,int col){
        super(gamePanel,color,row,col);
        pawnOpening = true;

        if(color == GamePanel.WHITE){
            getImage("Pieces/white-pawn.png");
        }
        else{
            getImage("Pieces/black-pawn.png");
        }
    }
    public void setNextPosition() {
        if(nextPositions!=null)
            nextPositions.clear(); // Clear previous positions

        int forwardDirection = (color == GamePanel.WHITE) ? -1 : 1; // -1 for WHITE, 1 for BLACK

        // Move forward
        int newRow = preRow + forwardDirection;
        int newCol = preCol;

        if (withinBoard(newRow, newCol) && !gamePanel.isOccupied[newRow][newCol]) {
            nextPositions.add(new Point(newRow, newCol));

            // Move two squares forward (for the initial double move)
            if (pawnOpening) {
                newRow += forwardDirection;
                if (withinBoard(newRow, newCol) && !gamePanel.isOccupied[newRow][newCol]) {
                    nextPositions.add(new Point(newRow, newCol));
                }
            }
        }

        // Capture diagonally and en passant
        int[] captureMoves = { -1, 1 };

        for (int captureMove : captureMoves) {
            newRow = preRow + forwardDirection;
            newCol = preCol + captureMove;

            if (withinBoard(newRow, newCol) && gamePanel.isOccupied[newRow][newCol]) {
                nextPositions.add(new Point(newRow, newCol));
            }
            // En passant capture
            if (gamePanel.enPassantPoint.row != -1 && newRow == gamePanel.enPassantPoint.row &&
                    newCol == gamePanel.enPassantPoint.col && !gamePanel.isOccupied[newRow][newCol] &&
                    gamePanel.illegalMovement.notPutOwnKingInCheck(newRow,newCol, this)) {
                Point p = new Point(gamePanel.enPassantPoint.row,gamePanel.enPassantPoint.col);
                nextPositions.add(p);
                gamePanel.enPassantPoint.setDefault();
            }
        }

        Point captureCheckedPiecePoint = new Point();
        if(gamePanel.checkMate.givenCheckPiece!=null){
            Point point = new Point();
            point.row = gamePanel.checkMate.givenCheckPiece.row;
            point.col = gamePanel.checkMate.givenCheckPiece.col;
            if(nextPositions.contains(point))  captureCheckedPiecePoint = point;

        }
        // Iterate over added positions to remove those not satisfying notPutOwnKingInCheck
        nextPositions.removeIf(pos -> !gamePanel.illegalMovement.notPutOwnKingInCheck(pos.row, pos.col, this));
        // add the check piece position for capturing the given checked piece
        if(captureCheckedPiecePoint.row!=-1) {
            nextPositions.add(captureCheckedPiecePoint);
        }

    }

}
