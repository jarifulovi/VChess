package Main;

import Piece.*;
import java.util.ArrayList;

public class IllegalMovement {

    GamePanel gamePanel;
    public Piece BlackKing; // added in the default setting class
    public Piece WhiteKing; // added in the default setting class
    public Piece ownKing;
    public IllegalMovement(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }
    public boolean notPutOwnKingInCheck(int newRow, int newCol, Piece activePiece){

        boolean flag = true;
        ownKing = (activePiece.color==GamePanel.WHITE)? (WhiteKing) : (BlackKing);
        // update the position of the active piece for temporary checking
        int preRow = activePiece.preRow;
        int preCol = activePiece.preCol;

        boolean newPosition = gamePanel.isOccupied[newRow][newCol];
        boolean oldPosition = gamePanel.isOccupied[preRow][preCol];

        gamePanel.isOccupied[newRow][newCol] = true;
        gamePanel.isOccupied[preRow][preCol] = false;

        flag = checkHorizontallyAndVertically(ownKing.preRow,ownKing.preCol,ownKing) &&
                    checkDiagonally(ownKing.preRow,ownKing.preCol,ownKing) && checkLShape(ownKing)
                        && checkPawnDiagonal(ownKing);

        // reset the position after checking
        gamePanel.isOccupied[newRow][newCol] = newPosition;
        gamePanel.isOccupied[preRow][preCol] = oldPosition;

        return flag;
    }
    private boolean checkHorizontallyAndVertically(int preRow, int preCol, Piece ownKing){

        ArrayList<Point> points= new ArrayList<>();
        int[][] moves = {
                {-1, 0}, // Move up
                {1, 0},  // Move down
                {0, -1}, // Move left
                {0, 1}   // Move right
        };

        for (int[] move : moves) {
            int newRow = preRow + move[0];
            int newCol = preCol + move[1];

            // go to next positions until an occupied square is encountered
            while (ownKing.withinBoard(newRow, newCol) && !gamePanel.isOccupied[newRow][newCol]) {
                newRow += move[0];
                newCol += move[1];
            }

            // If an occupied square is encountered, add it for rook and queen checking
            if (ownKing.withinBoard(newRow, newCol)) {
                points.add(new Point(newRow, newCol));
            }
        }

        return !isRookAndQueenPosition(points); // checks if there is a rook or queen
    }
    private boolean isRookAndQueenPosition(ArrayList<Point> points){
        for(Point p : points){
            for(Piece piece : gamePanel.simPieces){
                if((piece instanceof Rook || piece instanceof Queen)&& p.isEqual(piece.row,piece.col)
                       && (piece.color!=ownKing.color))
                    return true;
            }
        }
        return false;
    }
    private boolean checkDiagonally(int preRow, int preCol, Piece ownKing){

        ArrayList<Point> points = new ArrayList<>();
        int[][] moves = {
                {-1, -1}, // Move diagonally up and left
                {-1, 1},  // Move diagonally up and right
                {1, -1},  // Move diagonally down and left
                {1, 1}    // Move diagonally down and right
        };

        for (int[] move : moves) {
            int newRow = preRow + move[0];
            int newCol = preCol + move[1];

            // Add positions until an occupied square is encountered
            while (ownKing.withinBoard(newRow, newCol) && !gamePanel.isOccupied[newRow][newCol]) {
                newRow += move[0];
                newCol += move[1];
            }

            // If an occupied square is encountered, add it for bishop and queen checking
            if (ownKing.withinBoard(newRow, newCol)) {
                points.add(new Point(newRow, newCol));
            }
        }
        return !isBishopAndQueenPosition(points);
    }
    private boolean isBishopAndQueenPosition(ArrayList<Point> points){
        for(Point p : points){
            for(Piece piece : gamePanel.simPieces){
                if((piece instanceof Bishop || piece instanceof Queen)&& p.isEqual(piece.row,piece.col)
                        && (piece.color!=ownKing.color))
                    return true;
            }
        }
        return false;
    }
    private boolean checkLShape(Piece ownKing){

        ArrayList<Point> points = new ArrayList<>();
        int preRow = ownKing.preRow;
        int preCol = ownKing.preCol;

        int[][] moves = {
                {-2, -1}, {-2, 1}, {-1, -2}, {-1, 2},
                {1, -2}, {1, 2}, {2, -1}, {2, 1}
        };
        for(int[] move : moves){
            int newRow = preRow+move[0];
            int newCol = preCol+move[1];

            if(ownKing.withinBoard(newRow,newCol) && gamePanel.isOccupied[newRow][newCol]){
                points.add(new Point(newRow,newCol));
            }
        }
        return !isKnightPosition(points);
    }
    private boolean isKnightPosition(ArrayList<Point> points){
        for(Point p : points){
            for(Piece piece : gamePanel.simPieces){
                if((piece instanceof Knight)&& p.isEqual(piece.row,piece.col)
                        && (piece.color!=ownKing.color))
                    return true;
            }
        }
        return false;
    }
    private boolean checkPawnDiagonal(Piece ownKing){
        ArrayList<Point> points = new ArrayList<>();
        int preRow = ownKing.preRow;
        int preCol = ownKing.preCol;

        int[][] moves;
        if (ownKing.color==GamePanel.WHITE) {
            moves = new int[][]{{-1, -1}, {-1, 1}}; // White pawn moves
        } else {
            moves = new int[][]{{1, 1}, {1, -1}}; // Black pawn moves
        }
        for(int[] move : moves){
            int newRow = preRow + move[0];
            int newCol = preCol + move[1];

            if(ownKing.withinBoard(newRow,newCol) && gamePanel.isOccupied[newRow][newCol]){
                points.add(new Point(newRow,newCol));
            }
        }
        return !isPawnPosition(points);
    }
    private boolean isPawnPosition(ArrayList<Point> points){
        for(Point p : points){
            for(Piece piece : gamePanel.simPieces){
                if((piece instanceof Pawn)&& p.isEqual(piece.row,piece.col)
                        && (piece.color!=ownKing.color))
                    return true;
            }
        }
        return false;
    }
}
