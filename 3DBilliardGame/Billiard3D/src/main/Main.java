package main;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import billiard.game.BilliardScene;
import billiard.game.infra.Engine;
import billiard.game.infra.Scene;

/**
 * Main class.
 * 
 */
public class Main {
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Scene scene = new BilliardScene();
                Engine engine = new Engine(scene);

                JFrame view = new JFrame();
                view.setTitle("Eight Ball 3D");
                view.setSize(800, 600);
                view.setLocationRelativeTo(null);
                view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                view.setResizable(false);
                view.getContentPane().add(engine);
                view.setVisible(true);

                engine.requestFocus();
                try {        
                    engine.init();
                } catch (Exception ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    System.exit(-1);
                }
            }
        });
    }
    
}
