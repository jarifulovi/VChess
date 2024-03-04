package UserInterface;

import Main.ChessBoard;
import Main.GamePanel;
import Piece.*;

import java.awt.*;

public class PawnPromotion {

    GamePanel gamePanel;

    public Piece promotedPiece; // The pawn which will be promoted
    public boolean isPromoting;

    Piece[] promotionPieces = new Piece[8];


    public PawnPromotion(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        isPromoting = false;
        // white pieces
        promotionPieces[0] = new Knight(gamePanel,GamePanel.WHITE,0,0);
        promotionPieces[1] = new Bishop(gamePanel,GamePanel.WHITE,0,1);
        promotionPieces[2] = new Rook(gamePanel,GamePanel.WHITE,0,2);
        promotionPieces[3] = new Queen(gamePanel,GamePanel.WHITE,0,3);
        // black pieces
        promotionPieces[4] = new Knight(gamePanel,GamePanel.BLACK,7,0);
        promotionPieces[5] = new Bishop(gamePanel,GamePanel.BLACK,7,1);
        promotionPieces[6] = new Rook(gamePanel,GamePanel.BLACK,7,2);
        promotionPieces[7] = new Queen(gamePanel,GamePanel.BLACK,7,3);
    }
    public void promoting(int color){

        // mouse pressed and changing pawn to promoted piece
        if(gamePanel.mouseControl.pressed){
            for(Piece p : promotionPieces) {
                if(p.color==color && p.row==gamePanel.mouseControl.y/ ChessBoard.SQUARE_SIZE
                        && p.col == gamePanel.mouseControl.x/ChessBoard.SQUARE_SIZE){

                    gamePanel.simPieces.remove(promotedPiece);
                    addingPromotedPiece(p,color);
                    isPromoting = false;
                    gamePanel.changeTurn();
                }
            }
        }
    }
    private void addingPromotedPiece(Piece piece,int color){

        if(piece instanceof Knight){
            gamePanel.simPieces.add(new Knight(gamePanel,color,promotedPiece.row,promotedPiece.col));
        }
        else if(piece instanceof Bishop){
            gamePanel.simPieces.add(new Bishop(gamePanel,color,promotedPiece.row,promotedPiece.col));
        }
        else if(piece instanceof Rook){
            gamePanel.simPieces.add(new Rook(gamePanel,color,promotedPiece.row,promotedPiece.col));
        }
        else if(piece instanceof Queen){
            gamePanel.simPieces.add(new Queen(gamePanel,color,promotedPiece.row,promotedPiece.col));
        }
    }
    public void draw(Graphics2D g2d){

        // Draw a black shadow for the background
        g2d.setColor(new Color(0, 0, 0, 100)); // Adjust the alpha (transparency) as needed
        g2d.fillRect(0, 0, gamePanel.getWidth(), gamePanel.getHeight());

        if(gamePanel.runningPiece.color==GamePanel.WHITE){
            for(int i=0;i<4;i++) promotionPieces[i].draw(g2d);
        }
        else {
            for(int i=4;i<8;i++) promotionPieces[i].draw(g2d);
        }
    }
    public void isPawnPromoted(){
        if(gamePanel.runningPiece instanceof Pawn){
            // White player
            if(gamePanel.runningPiece.color==GamePanel.WHITE && gamePanel.runningPiece.row==0){
                isPromoting = true;
                gamePanel.pawnPromotion.promotedPiece = gamePanel.runningPiece;

            }
            else if(gamePanel.runningPiece.color==GamePanel.BLACK && gamePanel.runningPiece.row==7){
                isPromoting = true;
                gamePanel.pawnPromotion.promotedPiece = gamePanel.runningPiece;
            }

        }
    }
}
