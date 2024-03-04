package Piece;

import Main.GamePanel;
import Main.Point;

public class King extends Piece{

    public King(GamePanel gamePanel,int color,int row,int col){
        super(gamePanel,color,row,col);
        canCastle = true;

        if(color == GamePanel.WHITE){
            getImage("Pieces/white-king.png");
        }
        else{
            getImage("Pieces/black-king.png");
        }
    }
    public void setNextPosition() {
        if(nextPositions!=null)
            nextPositions.clear();
        // Set the next positions of a king
        int[][] moves = {
                {-1, -1}, {-1, 0}, {-1, 1},
                {0, -1},           {0, 1},
                {1, -1}, {1, 0}, {1, 1}
        };

        for (int[] move : moves) {
            int newRow = preRow + move[0];
            int newCol = preCol + move[1];

            if (withinBoard(newRow, newCol) && isSafePosition(new Point(newRow,newCol))) {
                nextPositions.add(new Point(newRow, newCol));
            }
        }
        if(canCastle) {
            // adds a move (king's two move ply)
            gamePanel.castling.checkCasting(row,col,this);
            // needs to remove additional and rook pos if under attack
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