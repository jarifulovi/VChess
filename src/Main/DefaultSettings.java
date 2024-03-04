package Main;

import Piece.*;

import java.util.ArrayList;

public class DefaultSettings {

    private GamePanel gamePanel;

    public DefaultSettings(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }
    public void setPieces(){

        gamePanel.pieces.clear();
        // pawn starts with row 6 for white and 1 for black
        gamePanel.pieces.add(new Pawn(gamePanel,GamePanel.WHITE,6,0));
        gamePanel.pieces.add(new Pawn(gamePanel,GamePanel.WHITE,6,1));
        gamePanel.pieces.add(new Pawn(gamePanel,GamePanel.WHITE,6,2));
        gamePanel.pieces.add(new Pawn(gamePanel,GamePanel.WHITE,6,3));
        gamePanel.pieces.add(new Pawn(gamePanel,GamePanel.WHITE,6,4));
        gamePanel.pieces.add(new Pawn(gamePanel,GamePanel.WHITE,6,5));
        gamePanel.pieces.add(new Pawn(gamePanel,GamePanel.WHITE,6,6));
        gamePanel.pieces.add(new Pawn(gamePanel,GamePanel.WHITE,6,7));
        gamePanel.pieces.add(new Pawn(gamePanel,GamePanel.BLACK,1,0));
        gamePanel.pieces.add(new Pawn(gamePanel,GamePanel.BLACK,1,1));
        gamePanel.pieces.add(new Pawn(gamePanel,GamePanel.BLACK,1,2));
        gamePanel.pieces.add(new Pawn(gamePanel,GamePanel.BLACK,1,3));
        gamePanel.pieces.add(new Pawn(gamePanel,GamePanel.BLACK,1,4));
        gamePanel.pieces.add(new Pawn(gamePanel,GamePanel.BLACK,1,5));
        gamePanel.pieces.add(new Pawn(gamePanel,GamePanel.BLACK,1,6));
        gamePanel.pieces.add(new Pawn(gamePanel,GamePanel.BLACK,1,7));
        // Queen
        gamePanel.pieces.add(new Queen(gamePanel,GamePanel.WHITE,7,3));
        gamePanel.pieces.add(new Queen(gamePanel,GamePanel.BLACK,0,3));
        // Bishop
        gamePanel.pieces.add(new Bishop(gamePanel,GamePanel.WHITE,7,2));
        gamePanel.pieces.add(new Bishop(gamePanel,GamePanel.WHITE,7,5));
        gamePanel.pieces.add(new Bishop(gamePanel,GamePanel.BLACK,0,2));
        gamePanel.pieces.add(new Bishop(gamePanel,GamePanel.BLACK,0,5));
        // Knight
        gamePanel.pieces.add(new Knight(gamePanel,GamePanel.WHITE,7,1));
        gamePanel.pieces.add(new Knight(gamePanel,GamePanel.WHITE,7,6));
        gamePanel.pieces.add(new Knight(gamePanel,GamePanel.BLACK,0,1));
        gamePanel.pieces.add(new Knight(gamePanel,GamePanel.BLACK,0,6));
        // rook
        Piece WhiteLeftRook = new Rook(gamePanel,GamePanel.WHITE,7,0);
        Piece WhiteRightRook = new Rook(gamePanel,GamePanel.WHITE,7,7);
        Piece BlackLeftRook = new Rook(gamePanel,GamePanel.BLACK,0,0);
        Piece BlackRightRook = new Rook(gamePanel,GamePanel.BLACK,0,7);
        gamePanel.pieces.add(WhiteLeftRook);
        gamePanel.pieces.add(WhiteRightRook);
        gamePanel.pieces.add(BlackLeftRook);
        gamePanel.pieces.add(BlackRightRook);
        gamePanel.castling.setCastledRooks(WhiteLeftRook,WhiteRightRook,BlackLeftRook,BlackRightRook);
        // The King
        Piece WhiteKing = new King(gamePanel,GamePanel.WHITE,7,4);
        Piece BlackKing = new King(gamePanel,GamePanel.BLACK,0,4);
        gamePanel.pieces.add(WhiteKing);
        gamePanel.pieces.add(BlackKing);
        // add the kings in illegal movement and checkMate class
        gamePanel.illegalMovement.WhiteKing = WhiteKing;
        gamePanel.illegalMovement.BlackKing = BlackKing;
        gamePanel.checkMate.WhiteKing = WhiteKing;
        gamePanel.checkMate.BlackKing = BlackKing;

    }
    public void setDefaultValues(){

        gamePanel.currentColor = GamePanel.WHITE;
        gamePanel.runningPiece = null;
        gamePanel.isOccupied = new boolean[8][8];
        // set isOccupied and next positions
        for(Piece piece : gamePanel.pieces){
            gamePanel.isOccupied[piece.row][piece.col] = true;
        }
        for(Piece piece : gamePanel.pieces) piece.setNextPosition();
    }

    public void copyPieces(ArrayList<Piece> source,ArrayList<Piece> target){
        // first time usage : copy pieces to simPiece
        // restarting a game
        target.clear();
        target.addAll(source);
    }

}
