package billiard.game.entitity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import billiard.game.BilliardEntity;
import billiard.game.BilliardScene;
import billiard.game.BilliardScene.State;
import billiard.model.BilliardGameModel.Mode;
import billiard.renderer3d.core.Renderer;

/**
 * GuiPanel class.
 * 
 */
public class GuiPanel extends BilliardEntity {
    
    private final Rectangle rectangle = new Rectangle();
    private static final Color backgroundColor = new Color(0, 0, 0, 150);
    
    public GuiPanel(String name, BilliardScene scene) {
        super(name, scene);
    }

    @Override
    public void init() throws Exception {
        setVisible(false);
        rectangle.setBounds(40, 50, 340, 150);
    }

    @Override
    public void updateGameStartOptions(Renderer renderer) {
        transform.setIdentity();
    }

    @Override
    public void draw(Renderer renderer, Graphics2D g) {
        if (!isVisible()) {
            return;
        }
        g.setColor(backgroundColor);
        g.fill(rectangle);
        scene.drawRect(g, rectangle.x, rectangle.y, rectangle.width, rectangle.height, Color.BLACK);
        Font font = BilliardScene.DEFAULT_FONT;
        scene.drawText(g, font, "GAME OPTIONS:", 40, 44, Color.DARK_GRAY);
        scene.drawText(g, font, " MODE:", 50, 90, Color.DARK_GRAY);
        scene.drawText(g, font, "BREAK:", 50, 130, Color.DARK_GRAY);
        if (gameModel.getMode() == Mode._1UP_CPU) {
            scene.drawText(g, font, "LEVEL:", 50, 170, Color.DARK_GRAY);
        }
    }
    
    // broadcast messages

    @Override
    public void stateChanged() {
        setVisible(scene.getState() == State.GAME_START_OPTIONS
            || scene.getState() == State.GAME_START_PREPARATIONS);
    }

    public void hideGui() {
        setVisible(false);
    }
    
}
