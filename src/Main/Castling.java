package Main;

import Piece.*;

public class Castling {
    private  final GamePanel gamePanel;
    private Piece LeftWhiteRook;
    private Piece RightWhiteRook;
    private Piece LeftBlackRook;
    private Piece RightBlackRook;

    public Castling(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }
    public void setCastledRooks(Piece LeftWhiteRook,Piece RightWhiteRook,Piece LeftBlackRook,Piece RightBlackRook){
        this.LeftWhiteRook = LeftWhiteRook;
        this.RightWhiteRook = RightWhiteRook;
        this.LeftBlackRook = LeftBlackRook;
        this.RightBlackRook = RightBlackRook;
    }

    // checks the king can castle or not and add the positions to the next position
    public void checkCasting(int newRow,int newCol,Piece activePiece){

        if(activePiece!=null && activePiece.canCastle) {
            int preRow = activePiece.preRow;
            int preCol = activePiece.preCol;



            rightSideCastling(newRow, preCol, activePiece);
            leftSideCastling(newRow, preCol, activePiece);

        }
    }
    // checks the right side castling
    private void rightSideCastling(int newRow,int preCol,Piece activePiece){

        boolean isObstacle = activePiece.isOccupiedHorizontal(newRow,preCol+1,preCol+2);

        boolean isWhiteRookExist = gamePanel.simPieces.contains(RightWhiteRook);
        boolean isBlackRookExist = gamePanel.simPieces.contains(RightBlackRook);

        boolean canCastle = !isObstacle &&
                ((activePiece.color==GamePanel.WHITE)?(RightWhiteRook.canCastle&&isWhiteRookExist):
                        (RightBlackRook.canCastle&&isBlackRookExist));

        if(canCastle){
            Point targetPoint = new Point(newRow,preCol+2);
            Point rookPoint = new Point(newRow,preCol+3);

            if(activePiece.isSafePosition(targetPoint) && activePiece.isSafePosition(rookPoint))
                activePiece.nextPositions.add(targetPoint);

        }
    }
    // check the left side castling
    private void leftSideCastling(int newRow,int preCol,Piece activePiece){
        boolean isObstacle =  activePiece.isOccupiedHorizontal(newRow,preCol-3,preCol-1);

        boolean isWhiteRookExist = gamePanel.simPieces.contains(LeftWhiteRook);
        boolean isBlackRookExist = gamePanel.simPieces.contains(LeftBlackRook);

        boolean canCastle = !isObstacle &&
                ((activePiece.color==GamePanel.WHITE)?(LeftWhiteRook.canCastle&&isWhiteRookExist):
                        (LeftBlackRook.canCastle&&isBlackRookExist));

        if(canCastle){
            Point targetPoint = new Point(newRow,preCol-2);
            Point rookPoint = new Point(newRow,preCol-3);

            if(activePiece.isSafePosition(targetPoint)&&activePiece.isSafePosition(rookPoint))
                activePiece.nextPositions.add(targetPoint);

        }
    }
    public void update(int newRow,int newCol){

        int preRow = gamePanel.runningPiece.preRow;
        int preCol = gamePanel.runningPiece.preCol;

        if(gamePanel.runningPiece instanceof King){
            if(Math.abs(gamePanel.runningPiece.preCol-gamePanel.runningPiece.col)==2) {
                if(newCol-preCol==2) {
                    Point rookPoint = new Point(newRow, preCol + 1);
                    gamePanel.castling.updateRookPosition("right",rookPoint,gamePanel.runningPiece);
                }
                else if(preCol-newCol==2) {
                    Point rookPoint = new Point(newRow,preCol-1);
                    gamePanel.castling.updateRookPosition("left",rookPoint,gamePanel.runningPiece);
                }
            }
        }
        gamePanel.runningPiece.canCastle = false;
    }
    public void updateRookPosition(String side,Point point,Piece activePiece){

        if(side.equals("right")){
            if(activePiece.color==GamePanel.WHITE) {
                RightWhiteRook.row = point.row;
                RightWhiteRook.col = point.col;
                RightWhiteRook.updatePosition();
            }
            else {
                RightBlackRook.row = point.row;
                RightBlackRook.col = point.col;
                RightBlackRook.updatePosition();
            }
        }
        else {
            if(activePiece.color==GamePanel.WHITE){
                LeftWhiteRook.row = point.row;
                LeftWhiteRook.col = point.col;
                LeftWhiteRook.updatePosition();
            }
            else {
                LeftBlackRook.row = point.row;
                LeftBlackRook.col = point.col;
                LeftBlackRook.updatePosition();
            }
        }
    }

}
