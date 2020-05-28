package billiard.game.entitity;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import billiard.game.BilliardEntity;
import billiard.game.BilliardScene;
import billiard.game.infra.Engine;
import billiard.renderer3d.core.Renderer;

/**
 * Processing class.
 * 
 * Shows when ball is still moving or AI is processing
 * 
 */
public class Processing extends BilliardEntity {
    
    private final AffineTransform rotation = new AffineTransform();
    private static BufferedImage image;
    
    private double angle;
    private double angle30Degrees = Math.toRadians(30);
    
    private double pivotX;
    private double pivotY;
    
    public Processing(String name, BilliardScene scene) {
        super(name, scene);
    }

    @Override
    public void init() throws Exception {
        image = ImageIO.read(getClass().getResourceAsStream("/res/processing.png"));
        pivotX = Engine.SCREEN_WIDTH - 17;
        pivotY = Engine.SCREEN_HEIGHT - 30;
    }

    @Override
    public void updatePlaying1(Renderer renderer) {
        updateRotating();
    }
    
    @Override
    public void updatePlaying2(Renderer renderer) {
        updateRotating();
    }
    
    private void updateRotating() {
        setVisible(ai.isProcessing() || gameModel.isBallsMoving());
        yield:
        while (true) {
            switch (instructionPointer) {
                case 0:
                    waitTime = System.currentTimeMillis();
                    instructionPointer = 1;
                case 1:
                    if (System.currentTimeMillis() - waitTime < 50) {
                        break yield;
                    }
                    angle -= angle30Degrees;
                    instructionPointer = 0;
                    break yield;
            }
        }

        rotation.setToIdentity();
        rotation.translate(pivotX, pivotY);
        rotation.rotate(angle);
        rotation.translate(-8, -8);
    }
    
    @Override
    public void draw(Renderer renderer, Graphics2D g) {
        if (!visible) {
            return;
        }
        g.drawImage(image, rotation, null);
    }

    // broadcast messages

    @Override
    public void stateChanged() {
        visible = false;
        instructionPointer = 0;
    }

}
