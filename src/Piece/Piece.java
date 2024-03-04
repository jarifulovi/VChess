package Piece;
import Main.Point;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.logging.Logger;

import Main.*;

import javax.imageio.ImageIO;

public class Piece {

    public GamePanel gamePanel;
    public BufferedImage image;
    private static final Logger logger = Logger.getLogger(Piece.class.getName());

    public int x,y;
    public int row,col, preRow, preCol;
    public int color;
    public ArrayList<Point> nextPositions;
    public boolean canMove;

    // some extra move fields
    public boolean pawnOpening;
    public boolean canCastle;


    public Piece(GamePanel gamePanel,int color,int row,int col){
        this.gamePanel = gamePanel;
        nextPositions = new ArrayList<>();
        this.color = color;
        this.row = row;
        this.col = col;
        this.preRow = row;
        this.preCol = col;
        x = getX(col);
        y = getY(row);
        // extra field
        pawnOpening = true;
        canCastle = false;  // only true for king and rook for once
    }
    public int getX(int col){
        return col*ChessBoard.SQUARE_SIZE;
    }
    public int getY(int row){
        return row*ChessBoard.SQUARE_SIZE;
    }
    public int getCol(int x){
        return (x + ChessBoard.SQUARE_SIZE/2)/ChessBoard.SQUARE_SIZE;
    }
    public int getRow(int y){
        return (y + ChessBoard.SQUARE_SIZE/2)/ChessBoard.SQUARE_SIZE;
    }
    public void updatePosition(){

        // update the en passant position
        if(gamePanel.runningPiece instanceof Pawn){
            gamePanel.runningPiece.pawnOpening = false;
            if(Math.abs(gamePanel.runningPiece.preRow-gamePanel.runningPiece.row)==2) {
                gamePanel.enPassantPoint.row = (gamePanel.runningPiece.preRow + gamePanel.runningPiece.row) / 2;
                gamePanel.enPassantPoint.col = gamePanel.runningPiece.col;
            }
            else{
                gamePanel.enPassantPoint.row = -1;
                gamePanel.enPassantPoint.col = -1;
            }
        }
        // update the rook when castling done
        gamePanel.castling.update(row,col);

        if (gamePanel.runningPiece instanceof Rook) gamePanel.runningPiece.canCastle = false;

        gamePanel.isOccupied[preRow][preCol] = false;

        x = getX(col);
        y = getY(row);
        preCol = getCol(x);
        preRow = getRow(y);

        gamePanel.isOccupied[preRow][preCol]  = true;

    }
    public void resetPosition(){

        col = preCol;
        row = preRow;
        x = getX(col);
        y = getY(row);
    }
    public boolean movePiece(int newRow,int newCol){
        //setNextPosition();
        // as it is called at the default setting method
        if(nextPositions!=null) {
            for (Point positions : nextPositions) {
                if (positions.isEqual(newRow, newCol)) return true;
            }
        }
        return false;
    }

    public void setNextPosition(){
        // calculate the next valid positions for a piece
    }

    public boolean isOccupiedHorizontal(int row,int startCol,int endCol){
        for(int i=startCol;i<=endCol;i++) if(gamePanel.isOccupied[row][i]) return true;
        return  false;
    }
    public boolean isSafePosition(Point point){
        for(Piece piece : gamePanel.simPieces){
            if(piece.color!=this.color){
                if(piece.nextPositions.contains(point)) return false;
            }
        }
        return true;
    }

    public boolean withinBoard(int row,int col){
        return row>=0 && row<8 && col>=0 && col<8;
    }
    public void getImage(String path){

        image = null;

        try{
            image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(path)));
        }
        catch (IOException e){
            logger.severe("Error loading image from path : "+path);
        }
    }
    public void draw(Graphics2D g2d){
        g2d.drawImage(image,x,y,ChessBoard.SQUARE_SIZE,ChessBoard.SQUARE_SIZE,null);
    }
    public boolean equals(Piece piece) {

        return piece.color==this.color && piece.row==this.row && piece.col==this.col;
    }
}
