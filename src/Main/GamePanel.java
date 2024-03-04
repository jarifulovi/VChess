package Main;
import DrawGame.DrawGame;
import Piece.*;
import Controls.*;
import UserInterface.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable {

    public static final int Width = 825;

    public static final int Height = 600;
    public static final int barHeight = 30;
    public static final int TotalHeight = Height+barHeight;
    public static final int TotalWidth = Width;
    final int FPS = 60;
    public static final int WHITE =  0;
    public static final int BLACK = 1;
    public int currentColor = WHITE;

    public ArrayList<Piece> pieces = new ArrayList<>();
    public ArrayList<Piece> simPieces = new ArrayList<>();
    public Piece runningPiece;
    public boolean[][] isOccupied = new boolean[8][8];
    public Point enPassantPoint = new Point();
    public ChessBoard board = new ChessBoard();
    public MouseControl mouseControl = new MouseControl();
    public DefaultSettings defaultSettings = new DefaultSettings(this);
    public PawnPromotion pawnPromotion = new PawnPromotion(this);
    public Simulation simulation = new Simulation(this);
    public IllegalMovement illegalMovement = new IllegalMovement(this);
    public Castling castling = new Castling(this);
    public CheckMate checkMate = new CheckMate(this);
    public DrawGame drawGame = new DrawGame(this);
    public GameOver gameOver = new GameOver(this);
    // game over scenarios

    Thread gameThread;

    public GamePanel(){
        setPreferredSize(new Dimension(TotalWidth,TotalHeight));
        setBackground(Color.gray);
        // adding mouse control ( moving, dragging )
        addMouseMotionListener(mouseControl);
        addMouseListener(mouseControl);
        // set the pieces
        defaultSettings.setPieces();
        defaultSettings.setDefaultValues();
        defaultSettings.copyPieces(pieces,simPieces);
        startGame();
    }
    public void restartGame(){

        defaultSettings.setPieces();
        defaultSettings.setDefaultValues();
        defaultSettings.copyPieces(pieces,simPieces);
        gameOver.isRestart = false;
    }
    public void startGame(){

        gameThread = new Thread(this);
        gameThread.start();
    }
    @Override
    public void run() {

        double drawInterval = (double)1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while(gameThread!=null){

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime)/drawInterval;
            lastTime = currentTime;

            if(delta>=1){
                update();
                repaint();
                delta--;
            }
        }
    }
    private void update(){


        if(gameOver.gameEnd || gameOver.isDraw){
            // gameOver main method will run
            gameOver.whenGameOver();
        }



        if(pawnPromotion.isPromoting){
            pawnPromotion.promoting(currentColor);
        }

        if(mouseControl.pressed){
            // check if the mouse pressed any piece or not

            if(runningPiece==null){

                for(Piece p : simPieces){
                    if(p.color==currentColor && p.row==mouseControl.y/ChessBoard.SQUARE_SIZE
                            && p.col == mouseControl.x/ChessBoard.SQUARE_SIZE){
                        runningPiece = p;
                    }
                }
            }
            else {
                // when dragging
                simulation.simulate();
            }
        }
        else {
            // when the mouse released
            if(runningPiece!=null) {

                //for(Point p : runningPiece.nextPositions) System.out.println(p.row+" "+p.col);

                // checking valid move
                if(runningPiece.withinBoard(runningPiece.row,runningPiece.col) && !simulation.collision) {
                    runningPiece.canMove = runningPiece.movePiece(runningPiece.row, runningPiece.col);
                }

                if (runningPiece.canMove) {
                    // en passant check
                    if (enPassantPoint.row != -1) simulation.removeEnPassantPawn();
                    simulation.capturePieces();


                    runningPiece.updatePosition();
                    pawnPromotion.isPawnPromoted();

                    if (!pawnPromotion.isPromoting) changeTurn();
                    for(Piece p : simPieces)
                        p.setNextPosition();

                    // after updating next position the checkmate or check will be checked
                    checkMate.findCheckPiece();
                    if(checkMate.givenCheckPiece!=null){
                        for(Piece p : simPieces){
                            p.setNextPosition();
                        }
                    }

                    gameOver.gameEnd = checkMate.checkMate();
                    gameOver.isDraw = checkMate.staleMate();
                    gameOver.isDraw = checkMate.insufficientMaterial();
                }
                else {
                    runningPiece.resetPosition();
                }

                runningPiece = null;

            }

        }

    }

    public void changeTurn(){
        if(currentColor==WHITE) currentColor = BLACK;
        else currentColor = WHITE;
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        drawGame.draw(g2d);
    }
}
