package billiard.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

import billiard.game.entitity.Ball3D;
import billiard.game.entitity.Ball3DShadow;
import billiard.game.entitity.Camera;
import billiard.game.entitity.Cursor;
import billiard.game.entitity.End;
import billiard.game.entitity.FadeEffect;
import billiard.game.entitity.GuiButton;
import billiard.game.entitity.GuiPanel;
import billiard.game.entitity.HUD;
import billiard.game.entitity.Initializer;
import billiard.game.entitity.PoolTable;
import billiard.game.entitity.Processing;
import billiard.game.entitity.Room;
import billiard.game.entitity.Title;
import billiard.game.infra.Entity;
import billiard.game.infra.Scene;
import billiard.model.BilliardAI;
import billiard.model.BilliardGameModel;
import billiard.renderer3d.core.Renderer;
import billiard.renderer3d.shader.BallShadowShader;
import billiard.renderer3d.shader.CursorShader;

/**
 * BilliardScene class.
 * 
 */
public class BilliardScene extends Scene {

    public static BallShadowShader shadowShader = new BallShadowShader();
    public static CursorShader cursorShader = new CursorShader();
    
    public static Font DEFAULT_FONT;
    public static enum State { INITIALIZING, TITLE, GAME_START_OPTIONS, GAME_START_PREPARATIONS, PLAYING_1, PLAYING_2, END }
    public State state = State.INITIALIZING;
    
    public BilliardGameModel gameModel;
    public BilliardAI ai;

    public BilliardScene() {
        loadDefaultFont();
        initGameModelAndAI();
        camera = new Camera("camera", this);
    }
    
    private void loadDefaultFont() {
        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/res/arial.ttf")));
            DEFAULT_FONT = new Font("Arial", Font.PLAIN, 14);
        } catch (Exception ex) {
            Logger.getLogger(BilliardScene.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(-1);
        }
    }
    
    private void initGameModelAndAI() {
        //Configuration configuration = new Configuration("2D physics engine", 800, 600, 2, 2, 30);
        gameModel = new BilliardGameModel();
        ai = new BilliardAI(gameModel);
        //gameModel.init();
    }
    
    public State getState() {
        return state;
    }

    public void setState(State state) {
        if (state != this.state) {
            this.state = state;
            broadcastMessage("stateChanged");
        }
    }

    public BilliardGameModel getGameModel() {
        return gameModel;
    }

    public BilliardAI getAI() {
        return ai;
    }
    
    @Override
    public void init() throws Exception {
        entities.add(new Initializer("initializer", this));
        entities.add(new Room("room", this));
        entities.add(new PoolTable("table", this));
        for (int ballId=1; ballId<=16; ballId++) {
            entities.add(new Ball3D(ballId, this));
            entities.add(new Ball3DShadow(ballId, this));
        }
        entities.add(new Cursor("cursor", this, (Camera) camera));
        entities.add(new Title("title", this));
        
        entities.add(new GuiPanel("gui_panel", this));
        for (int b = 1; b < 9; b++) {
            entities.add(new GuiButton("gui_button_" + b, this, b));
        }
        entities.add(new HUD("hud", this));
        
        entities.add(new End("end", this));
        entities.add(new Processing("processing", this)); //test
        entities.add(new FadeEffect("fade_effect", this));
        super.init();
    }

    public <T> T getProperty(String property, Class<T> returnType) {
        for (Entity entity : entities) {
            try {
                Method method = entity.getClass().getMethod(property);
                if (method != null) {
                    Object r = method.invoke(entity);
                    return returnType.cast(r);
                }
            } catch (Exception ex) {
            }
        };
        return null;
    }

    public void drawText(Graphics2D g, Font font, String text, int x, int y, Color borderColor) {
        g.setFont(font);
        g.setColor(borderColor);
        for (int y2=y-2; y2<=y+2; y2++) {
            for (int x2=x-2; x2<=x+2; x2++) {
                g.drawString(text, x2, y2);
            }
        }
        g.setColor(Color.WHITE);
        g.drawString(text, x, y);
    }
    
    public int getTextWidth(Graphics2D g, Font font, String text) {
        g.setFont(font);
        return g.getFontMetrics().stringWidth(text);
    }
    
    public void drawRect(Graphics2D g, int x, int y, int width, int height, Color borderColor) {
        g.setColor(borderColor);
        for (int y2=y-2; y2<=y+2; y2++) {
            for (int x2=x-2; x2<=x+2; x2++) {
                g.drawRect(x2, y2, width, height);
            }
        }
        g.setColor(Color.WHITE);
        g.drawRect(x, y, width, height);
    }

    @Override
    public void updatePhysics() {
        if (!ai.isProcessing()) {
            gameModel.getPhysics2D().update();
        }
    }

    @Override
    public void draw(Renderer renderer, Graphics2D g) {
        super.draw(renderer, g);
        // draw mini 2d top view 
//        AffineTransform at = g.getTransform();
//        g.translate(210, 45);
//        g.scale(0.25, 0.25);
//        gameModel.getPhysics2D().draw(g);
//        g.setTransform(at);
    }
    
}
