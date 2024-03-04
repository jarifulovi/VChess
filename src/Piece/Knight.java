package Piece;

import Main.GamePanel;
import Main.Point;

public class Knight extends Piece{

    public Knight(GamePanel gamePanel,int color,int row,int col){
        super(gamePanel,color,row,col);

        if(color == GamePanel.WHITE){
            getImage("Pieces/white-knight.png");
        }
        else{
            getImage("Pieces/black-knight.png");
        }
    }
    public void setNextPosition(){
        if(nextPositions!=null)
            nextPositions.clear();
        // set the next position of a knight
        // set it in the nextPosition arraylist of point which has x and y int var
        int[][] moves = {
                {-2, -1}, {-2, 1}, {-1, -2}, {-1, 2},
                {1, -2}, {1, 2}, {2, -1}, {2, 1}
        };

        for (int[] move : moves) {
            int newRow = preRow + move[0];
            int newCol = preCol + move[1];

            if(withinBoard(newRow,newCol))
                nextPositions.add(new Point(newRow,newCol));
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
