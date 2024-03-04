package Main;

import Piece.*;

public class CheckMate {

    GamePanel gamePanel;
    public boolean check;
    public Piece givenCheckPiece;
    public Piece WhiteKing;
    public Piece BlackKing;
    public CheckMate(GamePanel gamePanel){
        this.gamePanel = gamePanel;

    }
    public void findCheckPiece(){

        Piece opponentKing = (gamePanel.runningPiece.color==GamePanel.BLACK)?WhiteKing:BlackKing;

        if(gamePanel.runningPiece.nextPositions.contains(new Point(opponentKing.row,opponentKing.col))) {
            givenCheckPiece = gamePanel.runningPiece;
            check = true;
            System.out.println("Check");
        }
        else {
            givenCheckPiece = null;
            check = false;
        }
    }
    public boolean checkMate(){
        // checked and opponent has no move
        if(check){
            for(Piece piece : gamePanel.simPieces){
                if(gamePanel.runningPiece.color!=piece.color) {
                    if (!piece.nextPositions.isEmpty()) return false;
                }
            }
            return true;
        }
        return false;
    }
    public boolean staleMate(){
        // not checked and opponent has no legal move
        if(!check){
            for(Piece piece : gamePanel.simPieces){
                if(gamePanel.runningPiece.color!=piece.color) {
                    if (!piece.nextPositions.isEmpty()) return false;
                }
            }
            return true;
        }
        return false;
    }
    public boolean insufficientMaterial(){
        // scenarios

        int WhiteKnight=0,WhiteBishop=0;
        int BlackKnight=0,BlackBishop=0;

        for(Piece piece : gamePanel.simPieces){
            if(piece.color==GamePanel.WHITE) {
                if (piece instanceof Knight) WhiteKnight++;
                else if (piece instanceof Bishop) WhiteBishop++;
            }
            else {
                if (piece instanceof Knight) BlackKnight++;
                else if (piece instanceof Bishop) BlackBishop++;
            }
        }
        int totalKnight = WhiteKnight+BlackKnight;
        int totalBishop = WhiteBishop+BlackBishop;
        int totalPiece = gamePanel.simPieces.size();

        boolean kingVsKing = (totalPiece==2);
        boolean twoKnight = (totalPiece==4)&&(WhiteKnight==1&&BlackKnight==1);
        boolean twoBishop = (totalPiece==4)&&(WhiteBishop==1&&BlackBishop==1);
        boolean oneKnight =  (totalPiece==3)&&(totalKnight==1);
        boolean oneBishop = (totalPiece==3)&&(totalBishop==1);
        boolean knightAndBishop = (totalPiece==4)&&(totalKnight==1&&totalBishop==1)&&(WhiteKnight!=WhiteBishop);

        return kingVsKing || twoKnight || twoBishop || oneKnight || oneBishop || knightAndBishop;
    }
}
