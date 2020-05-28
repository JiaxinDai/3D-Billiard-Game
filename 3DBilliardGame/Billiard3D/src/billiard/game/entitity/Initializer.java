package billiard.game.entitity;

import billiard.game.BilliardEntity;
import billiard.game.BilliardScene;
import billiard.game.BilliardScene.State;
import billiard.renderer3d.core.Renderer;

/**
 * Initializer class.
 * 
 */
public class Initializer extends BilliardEntity {

    public Initializer(String name, BilliardScene scene) {
        super(name, scene);
    }

    @Override
    public void updateInitializing(Renderer renderer) {
    	scene.setState(State.TITLE);
    }
    
}
