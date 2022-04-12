/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Game;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import javax.imageio.ImageIO;


/**
 *
 * @author ilyad
 */
// dunaev ilya
public class Game extends JFrame {
    private static Game game_window;
    private static long last_frame_time;
    private static Image background;
    private static Image game_over;
    private static Image drop;
    private static float drop_left = 200;
    private static float drop_top = -100;
    private static float drop_v = 200;
    private static int score = 0;
    // dunaev ilya
    public static void main(String[] args) throws IOException {
        background = ImageIO.read(Game.class.getResourceAsStream("background.jpg"));
        game_over = ImageIO.read(Game.class.getResourceAsStream("end.jpg"));
        drop = ImageIO.read(Game.class.getResourceAsStream("object.png"));
        //dunaev ilya
        game_window = new Game();
        game_window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        game_window.setLocation(100, 50);
        game_window.setSize(1280, 720);
        game_window.setResizable(false);
        last_frame_time = System.nanoTime();
        GameField game_field = new GameField();
        game_field.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                float drop_right = drop_left + drop.getWidth(null);
                float drop_bottom = drop_top + drop.getHeight(null);
                boolean is_drop = x >= drop_left && x <= drop_right && y >= drop_top && y<= drop_bottom;
                
                if(is_drop) {
                drop_top = -100;
                drop_left = (int) (Math.random() * (game_field.getWidth() - drop.getWidth(null)));
                drop_v = drop_v + 10;
                score++;
                game_window.setTitle("Score: " + score);
                }       
            }//dunaev
        });
        game_window.add(game_field);
        game_window.setVisible(true);
    }
    private static void onRepaint(Graphics g){
        //dunaev ilya
        long current_time = System.nanoTime();
        float delt_time = (current_time - last_frame_time) * 0.000000001f;
        last_frame_time = current_time;
        drop_top = drop_top + drop_v * delt_time;
        g.drawImage(background, 0, 0, null);
        g.drawImage(drop, (int) drop_left, (int) drop_top, null);
        if(drop_top > game_window.getHeight())g.drawImage(game_over, 300, 50, null);
    }//dunaev
    private static class GameField extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            onRepaint(g);
            repaint();
        }
    }
}
