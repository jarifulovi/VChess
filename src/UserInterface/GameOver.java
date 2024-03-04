package UserInterface;

import Main.GamePanel;
import Piece.Piece;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class GameOver {
    GamePanel gamePanel;
    public boolean isDraw;
    public boolean gameEnd;
    public boolean resign;  // I will make another class for this
    // Define the ratio for the main rectangle
    double mainRectRatio = 0.5;
    double heightWidthRatio = 0.8;

    RoundRectangle2D leftButton;
    RoundRectangle2D rightButton;
    public boolean isRestart;
    public boolean isExit;
    public GameOver(GamePanel gamePanel){
        this.gamePanel = gamePanel;
        isDraw = false;
        gameEnd = false;
        resign = false;
    }
    public void whenGameOver(){
        // check for restart
        if(gamePanel.mouseControl.pressed) {
            if (leftButton != null && rightButton != null) {
                isRestart = (leftButton.contains(gamePanel.mouseControl.x,gamePanel.mouseControl.y));
                isExit = (rightButton.contains(gamePanel.mouseControl.x,gamePanel.mouseControl.y));
            }
        }
        // when the mouse release
        else {
            if(isRestart) {
               gamePanel.restartGame();
               isRestart = false;
               isDraw = false;
               gameEnd = false;
            }
            else if(isExit){
                System.exit(1);
                isExit = false;
            }
        }
    }
    public void draw(Graphics2D g2d){
        // Set rendering hints for smooth rendering
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw a black shadow for the background
        g2d.setColor(new Color(0, 0, 0, 100)); // Adjust the alpha (transparency) as needed
        g2d.fillRect(0, 0, gamePanel.getWidth(), gamePanel.getHeight());
        // Calculate the center position
        int centerX = gamePanel.TotalWidth / 2;
        int centerY = gamePanel.TotalHeight / 2;

        // Calculate the dimensions of the main square round rectangle based on the ratio
        int mainRectWidth = (int) (gamePanel.TotalWidth * mainRectRatio);
        int mainRectHeight = (int) (gamePanel.TotalHeight * mainRectRatio * heightWidthRatio);

        int positionX = centerX - mainRectWidth / 2;
        int positionY = centerY - mainRectHeight / 2;

        // Draw the main square round rectangle with a border
        RoundRectangle2D mainRect = new RoundRectangle2D.Double(
                positionX, positionY, mainRectWidth, mainRectHeight, 50, 50);

        // Increase the width of the border
        int borderWidth = 10; // Adjust as needed
        BasicStroke borderStroke = new BasicStroke(borderWidth);

        g2d.setColor(Color.darkGray);
        g2d.fill(mainRect);

        g2d.setStroke(borderStroke); // Set the stroke width
        g2d.setColor(Color.GREEN);
        g2d.draw(mainRect);
        screenText(g2d,positionX,positionY,mainRectWidth,mainRectHeight);
        addButtons(g2d,positionX,positionY,mainRectWidth,mainRectHeight);
    }
    private void screenText(Graphics2D g2d, int rectPosX, int rectPosY, int mainRectWidth, int mainRectHeight) {
        String text = "";
        if(isDraw){
            text = "Draw";
        }
        else if(gameEnd){
            text = (gamePanel.currentColor == GamePanel.WHITE) ? "BLACK WINS" : "WHITE WINS";
        }


        // Set the font and color for the text
        g2d.setFont(new Font("Arial", Font.BOLD, 24)); // You can customize the font
        g2d.setColor(Color.WHITE); // Set the text color

        // Calculate the position to center the text inside the rectangle
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getHeight();

        int x = rectPosX + (mainRectWidth - textWidth) / 2;
        int y = rectPosY + (mainRectHeight - textHeight) / 2 + fm.getAscent();

        // Draw the text inside the rectangle
        g2d.drawString(text, x, y);
    }
    private void addButtons(Graphics2D g2d, int rectPosX, int rectPosY, int rectWidth, int rectHeight) {
        // Calculate the dimensions of each button
        int buttonWidth = rectWidth / 2 - (rectWidth/45);
        int buttonHeight = rectHeight / 4 - (rectHeight/60);

        int adjustPosX = 10;
        int adjustPosY = 10;
        // Calculate the positions of the left and right buttons
        int leftButtonX = rectPosX +adjustPosX;
        int leftButtonY = rectPosY + rectHeight - adjustPosY;

        int rightButtonX = rectPosX + buttonWidth + adjustPosX;
        int rightButtonY = rectPosY + rectHeight - adjustPosY;



        // Create rectangles for the left and right buttons

        leftButton =
                new RoundRectangle2D.Double(leftButtonX,leftButtonY-buttonHeight,buttonWidth,buttonHeight,10,10);
        rightButton =
                new RoundRectangle2D.Double(rightButtonX, rightButtonY-buttonHeight, buttonWidth, buttonHeight,10,10);

        // Fill the left button with a green color
        Color color = new Color(10,250,32);
        g2d.setColor(color);
        g2d.fill(leftButton);

        // Fill the right button with a green color
        g2d.setColor(color);
        g2d.fill(rightButton);

        // Draw the outline of the buttons (optional)
        g2d.setColor(Color.BLACK);
        g2d.draw(leftButton);
        g2d.draw(rightButton);

        // Set the font and color for the text
        g2d.setFont(new Font("Arial", Font.BOLD, 30)); // You can customize the font
        g2d.setColor(Color.WHITE); // Set the text color

        // Calculate the position for the "Restart" text inside the left button
        int restartTextX = (int) (leftButton.getX() + (leftButton.getWidth() - g2d.getFontMetrics().stringWidth("Restart")) / 2);
        int restartTextY = (int) (leftButton.getY() + (leftButton.getHeight() - g2d.getFontMetrics().getHeight()) / 2 + g2d.getFontMetrics().getAscent());

        // Draw the "Restart" text inside the left button
        g2d.drawString("Restart", restartTextX, restartTextY);

        // Calculate the position for the "Exit" text inside the right button
        int exitTextX = (int) (rightButton.getX() + (rightButton.getWidth() - g2d.getFontMetrics().stringWidth("Exit")) / 2);
        int exitTextY = (int) (rightButton.getY() + (rightButton.getHeight() - g2d.getFontMetrics().getHeight()) / 2 + g2d.getFontMetrics().getAscent());

        // Draw the "Exit" text inside the right button
        g2d.drawString("Exit", exitTextX, exitTextY);
    }

}
