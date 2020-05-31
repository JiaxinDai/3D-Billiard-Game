package billiard.model;

import billiard.model.BallModel;
import billiard.physics2d.Circle;
import billiard.physics2d.RigidBody;

/**
 * BallModel rigid body class.
 * 
 */
public class BallModel extends RigidBody {
    
    private final BilliardGameModel model;
    private final int id;
    
    public BallModel(BilliardGameModel model, int id, double x, double y, double radius) {
        super(model.getPhysics2D(), new Circle(radius));
        this.model = model;
        this.id = id;
        getPosition().set(x, y);
        setMass(0.6);
    }

    public int getId() {
        return id;
    }

    public void set(BallModel ballModel) {
        getPosition().set(ballModel.getPosition());
        getVelocity().set(ballModel.getVelocity());
        setVisible(ballModel.isVisible());
    }
    
    @Override
    public void update() {
        super.update();
        if (isVisible() && !PoolTableModel.insideTable.contains(getPosition().getX(), getPosition().getY())) {
            setVisible(false);
            model.addPocketedBall(this);
        }
        if (!PoolTableModel.movableArea.contains(getPosition().getX(), getPosition().getY())) {
            getVelocity().set(0, 0);
        }
        if (Math.abs(getVelocity().getX()) < 3) {
            getVelocity().setX(0);
        }
        if (Math.abs(getVelocity().getY()) < 3) {
            getVelocity().setY(0);
        }
    }
    
}
