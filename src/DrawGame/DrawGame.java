package DrawGame;

import Main.GamePanel;
import Main.ChessBoard;
import Main.Point;
import Piece.Piece;

import java.awt.*;
import java.util.ArrayList;

public class DrawGame {

    private final GamePanel gamePanel;
    private static final AlphaComposite TRANSPARENCY_COMPOSITE = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f);
    private static final AlphaComposite DEFAULT_COMPOSITE = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f);


    // Add any additional fields you may need

    public DrawGame(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        // Initialize additional fields if needed
    }

    public void draw(Graphics2D g2d) {
        drawBoard(g2d);
        drawRunningPieceBackground(g2d);

        // Draw Pieces
        drawPieces(g2d);

        // Draw pawn promotion if promoting
        if (gamePanel.pawnPromotion.isPromoting) {
            gamePanel.pawnPromotion.draw(g2d);
        }
        if(gamePanel.gameOver.gameEnd){
            gamePanel.gameOver.draw(g2d);
        }
    }

    private void drawBoard(Graphics2D g2d) {
        gamePanel.board.draw(g2d);
    }

    private void drawRunningPieceBackground(Graphics2D g2d) {
        if (gamePanel.runningPiece != null) {
            // Create a defensive copy to avoid concurrent modification issues
            Piece activePiece = gamePanel.runningPiece;

            drawValidPosition(g2d);
            g2d.setColor(Color.gray);
            g2d.setComposite(TRANSPARENCY_COMPOSITE);
            g2d.fillRect(
                    activePiece.col * ChessBoard.SQUARE_SIZE,
                    activePiece.row * ChessBoard.SQUARE_SIZE,
                    ChessBoard.SQUARE_SIZE,
                    ChessBoard.SQUARE_SIZE
            );
            g2d.setComposite(DEFAULT_COMPOSITE);
        }
    }

    private void drawPieces(Graphics2D g2d) {
        // Create a defensive copy to avoid concurrent modification issues
        ArrayList<Piece> copy = new ArrayList<>(gamePanel.simPieces);
        for (Piece piece : copy) {
            piece.draw(g2d);
        }
    }
    private void drawValidPosition(Graphics2D g2d){

        if(gamePanel.runningPiece==null) return;

        g2d.setColor(Color.green);
        g2d.setComposite(TRANSPARENCY_COMPOSITE);

        // Create a defensive copy to avoid concurrent modification issues
        ArrayList<Point> nextDrawPositions = new ArrayList<>(gamePanel.runningPiece.nextPositions);
        int color = gamePanel.runningPiece.color;

        for(Point p : nextDrawPositions) {


            if(!gamePanel.isOccupied[p.row][p.col] ||
                    !gamePanel.simulation.isOwnPiece(p.row,p.col,color))
                g2d.fillRect(
                        p.col * ChessBoard.SQUARE_SIZE,
                        p.row * ChessBoard.SQUARE_SIZE,
                        ChessBoard.SQUARE_SIZE,
                        ChessBoard.SQUARE_SIZE
                );
        }

        g2d.setComposite(DEFAULT_COMPOSITE);
    }
}
