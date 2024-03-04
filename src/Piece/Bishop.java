package Piece;

import Main.GamePanel;
import Main.Point;

import java.util.Iterator;

public class Bishop extends Piece{

    public Bishop(GamePanel gamePanel,int color,int row,int col){
        super(gamePanel,color,row,col);

        if(color == GamePanel.WHITE){
            getImage("Pieces/white-bishop.png");
        }
        else{
            getImage("Pieces/black-bishop.png");
        }
    }

    public void setNextPosition() {
        if (nextPositions != null)
            nextPositions.clear();

        // Set the next positions of a bishop
        int[][] moves = {
                {-1, -1}, // Move diagonally up and left
                {-1, 1},  // Move diagonally up and right
                {1, -1},  // Move diagonally down and left
                {1, 1}    // Move diagonally down and right
        };

        for (int[] move : moves) {
            int newRow = preRow + move[0];
            int newCol = preCol + move[1];

            // Add positions until an occupied square is encountered
            while (withinBoard(newRow, newCol) && !gamePanel.isOccupied[newRow][newCol]) {
                nextPositions.add(new Point(newRow, newCol));
                newRow += move[0];
                newCol += move[1];
            }
            // If an occupied square is encountered, add it for capturing purposes
            if (withinBoard(newRow, newCol)) {
                nextPositions.add(new Point(newRow, newCol));
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
