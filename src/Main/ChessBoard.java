package Main;

import java.awt.*;

public class ChessBoard {

    final int MAX_COL = 8;
    final int MAX_ROW = 8;

    public static final int SQUARE_SIZE = 75;

    public void draw(Graphics2D g2d){

        for(int row=0;row<MAX_ROW;row++){
            for(int col=0;col<MAX_COL;col++){
                if((col+row)%2==0){
                    // white squares
                    g2d.setColor(new Color(255,255,255));
                }
                else{
                    // black squares
                    g2d.setColor(new Color(100,100,100));
                }
                g2d.fillRect(col*SQUARE_SIZE,row*SQUARE_SIZE,SQUARE_SIZE,SQUARE_SIZE);
            }
        }
    }

}
