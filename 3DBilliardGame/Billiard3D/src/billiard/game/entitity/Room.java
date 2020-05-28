package billiard.game.entitity;

import billiard.game.BilliardEntity;
import billiard.game.BilliardScene;
import billiard.renderer3d.core.Renderer;
import billiard.renderer3d.parser.wavefront.WavefrontParser;

/**
 * Room class.
 * 
 */
public class Room extends BilliardEntity {

    public Room(String name, BilliardScene scene) {
        super(name, scene);
    }

    @Override
    public void init() throws Exception {
        mesh = WavefrontParser.load("/res/room1.obj", 100);
        setVisible(false);
    }
    
    @Override
    public void update(Renderer renderer) {
        transform.setIdentity();
        //transform.translate(0, 0, 0);
        //transform.rotateX(Time.delta * 0.00000000005);
        //transform.rotateY(Time.delta * 0.00000000004);
        //transform.rotateZ(Time.delta * 0.00000000006);
    }
    
    // broadcast messages

    @Override
    public void stateChanged() {
        setVisible(scene.getState() == BilliardScene.State.TITLE
            || scene.getState() == BilliardScene.State.GAME_START_OPTIONS
            || scene.getState() == BilliardScene.State.GAME_START_PREPARATIONS
            || scene.getState() == BilliardScene.State.PLAYING_1
            || scene.getState() == BilliardScene.State.PLAYING_2
            || scene.getState() == BilliardScene.State.END);
    }
    
}
