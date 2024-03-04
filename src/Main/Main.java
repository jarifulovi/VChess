package Main;

import javax.swing.*;

public class Main extends JPanel{
    public static void main(String[] args) {

        JFrame window = new JFrame("MyChess");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        // Add GamePanel to Main
        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        window.pack();
        System.out.println("hello");
        window.setLocationRelativeTo(null);
        window.setVisible(true);

    }
}