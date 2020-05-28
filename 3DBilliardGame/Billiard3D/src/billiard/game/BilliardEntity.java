package billiard.game;

import billiard.game.infra.Entity;
import billiard.model.BilliardAI;
import billiard.model.BilliardGameModel;
import billiard.renderer3d.core.Renderer;

/**
 * BilliardEntity class.
 * 
 */
public class BilliardEntity extends Entity<BilliardScene> {
    
    public int instructionPointer;
    public long waitTime;
    public BilliardGameModel gameModel;
    public BilliardAI ai;
    
    public BilliardEntity(String name, BilliardScene scene) {
        super(name, scene);
        this.gameModel = scene.getGameModel();
        this.ai = scene.getAI();
    }

    @Override
    public void update(Renderer renderer) {
        update2DPhysics();
        preUpdate(renderer);
        switch(scene.state) {
            case INITIALIZING: updateInitializing(renderer); break;
            case TITLE: updateTitle(renderer); break;
            case GAME_START_OPTIONS: updateGameStartOptions(renderer); break;
            case GAME_START_PREPARATIONS: updateGameStartPreparations(renderer); break;
            case PLAYING_1: updatePlaying1(renderer); break;
            case PLAYING_2: updatePlaying2(renderer); break;
            case END: updateEnd(renderer); break;
        }
        posUpdate(renderer);
    }
    
    public void update2DPhysics() {
    }
    
    public void preUpdate(Renderer renderer) {
    }

    public void updateInitializing(Renderer renderer) {
    }

    public void updateTitle(Renderer renderer) {
    }

    public void updateGameStartOptions(Renderer renderer) {
    }

    public void updateGameStartPreparations(Renderer renderer) {
    }
    
    public void updatePlaying1(Renderer renderer) {
    }

    public void updatePlaying2(Renderer renderer) {
    }

    public void updateEnd(Renderer renderer) {
    }

    public void posUpdate(Renderer renderer) {
    }
    
    // broadcast messages
    
    public void stateChanged() {
    }


}
