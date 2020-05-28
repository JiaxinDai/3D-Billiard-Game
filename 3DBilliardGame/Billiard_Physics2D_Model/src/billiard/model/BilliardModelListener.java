package billiard.model;

import billiard.model.BallModel;
import billiard.model.BilliardGameModel.State;

/**
 * Eight Ball Model Listener interface.
 * 
 */
public interface BilliardModelListener {
    
    public void ballPocketed(BallModel ballModel);
    public void stateChanged(State newState);
    
}
