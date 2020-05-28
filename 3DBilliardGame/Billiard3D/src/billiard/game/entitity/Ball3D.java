package billiard.game.entitity;

import billiard.game.BilliardEntity;
import billiard.game.BilliardScene;
import billiard.game.BilliardScene.State;
import billiard.math.Mat4;
import billiard.math.Quaternion;
import billiard.math.Vec4;
import billiard.model.BallModel;
import billiard.renderer3d.core.Image;
import billiard.renderer3d.core.Renderer;
import billiard.renderer3d.parser.wavefront.Obj;
import billiard.renderer3d.parser.wavefront.WavefrontParser;

/**
 * Ball3D class.
 * 
 */
public class Ball3D extends BilliardEntity {
    
    private int id;
    private BallModel ball2D; // 2D physics ball
    
    public Mat4 rotationMatrix = new Mat4();
    public Quaternion accumulatedRotation = new Quaternion(0, new Vec4(1, 0, 0, 0));
    
    public Vec4 rotationDirection = new Vec4(1, 0, 0, 0);
    public Quaternion rotation = new Quaternion(Math.toRadians(1), rotationDirection);
    
    public Ball3D(int id, BilliardScene scene) {
        super("ball_" + id, scene);
        this.id = id;
        ball2D = gameModel.getBall(id);
        rotationDirection.set(10 * Math.random() - 5, 10 * Math.random() - 5, 10 * Math.random() - 5, 0);
        rotationDirection.normalize();
        accumulatedRotation.set(360 * Math.random(), rotationDirection);
    }

    @Override
    public void init() throws Exception {
        mesh = WavefrontParser.load("/res/ball.obj", 80);
        setTexture("/res/" + id + ".gif");
        setVisible(false);
    }

    private void setTexture(String textureResource) {
        for (Obj obj : mesh) {
            obj.material.map_kd = new Image(textureResource);
        }
    }
        
    @Override
    public void update(Renderer renderer) {
        setVisible(ball2D.isVisible() && scene.getState() != BilliardScene.State.INITIALIZING);

        transform.setIdentity();
        transform.translate(ball2D.getPosition().getX(), 9, ball2D.getPosition().getY());
        
        rotationDirection.set(ball2D.getVelocity().getY(), 0.01, -ball2D.getVelocity().getX(), 0);
        rotationDirection.normalize();
        
        double angle = ball2D.getVelocity().getLength() * 0.003;
        
        rotation.set(angle, rotationDirection);
        rotation.multiply(accumulatedRotation, accumulatedRotation);
        
        accumulatedRotation.convertToMatrix(rotationMatrix);
        transform.getMatrix().multiply(rotationMatrix);
    }
    
    // broadcast messages

    @Override
    public void stateChanged() {
        setVisible((scene.getState() == State.TITLE
            || scene.getState() == State.GAME_START_OPTIONS
            || scene.getState() == State.GAME_START_PREPARATIONS
            || scene.getState() == State.PLAYING_1
            || scene.getState() == State.PLAYING_2
            || scene.getState() == State.END) && !gameModel.isBallPocketed(id));
    }
    
}
