package billiard.game.entitity;

import billiard.game.BilliardEntity;
import billiard.game.BilliardScene;
import billiard.game.infra.Mouse;
import billiard.game.infra.Time;
import billiard.math.MathUtil;
import billiard.renderer3d.core.Renderer;
import billiard.renderer3d.core.Transform;

/**
 * Camera class.
 * 
 */
public class Camera extends BilliardEntity {
    
    public double targetAngleX;
    public double targetAngleY;

    public double angleX;
    public double angleY;

    public double mouseAngleX;
    public double mouseAngleY;

    public double mousePressedX;
    public double mousePressedY;
    public double mousePressedAngleX;
    public double mousePressedAngleY;
    
    public double vp;
    
    public boolean mouseEnabled = false;
    
    public Transform invertTransform = new Transform();
    
    public Camera(String name, BilliardScene scene) {
        super(name, scene);
    }
        
    @Override
    public void init() throws Exception {
        transform.setIdentity();
    }

    private void updateAutoRotating() {
        mouseAngleX = -0.6;
        mouseAngleY += Time.delta * 0.00000000025;
        transform.setIdentity();
        transform.translate(0, 0, -500);
        transform.rotateX(mouseAngleX);
        transform.rotateY(-mouseAngleY);
    }
    
    @Override
    public void updateTitle(Renderer renderer) {
        updateAutoRotating();
    }

    @Override
    public void updateGameStartOptions(Renderer renderer) {
        updateAutoRotating();
    }

    @Override
    public void updateGameStartPreparations(Renderer renderer) {
        // dragCameraRotation();
    }

    @Override
    public void updatePlaying1(Renderer renderer) {
        dragCameraRotation();
    }
    
    @Override
    public void updatePlaying2(Renderer renderer) {
        dragCameraRotation();
    }
    
    // change camera view using mouse dragging with right button
    private void dragCameraRotation() {
        if (Mouse.pressed2 && !Mouse.pressedConsumed2) {
            Mouse.pressedConsumed2 = true;
            mousePressedX = Mouse.x;
            mousePressedY = Mouse.y;
            mousePressedAngleX = mouseAngleX;
            mousePressedAngleY = mouseAngleY;
        }
        else if (Mouse.pressed2) {
            double difX = (Mouse.x - mousePressedX);
            double difY = (Mouse.y - mousePressedY);
            targetAngleX = mousePressedAngleX + Math.toRadians(difY * 0.5);
            targetAngleY = mousePressedAngleY + Math.toRadians(difX * 0.5);
        }
        
        mouseAngleX = mouseAngleX + (targetAngleX - mouseAngleX) * 0.1;
        mouseAngleY = mouseAngleY + (targetAngleY - mouseAngleY) * 0.1;
        
        mouseAngleX = MathUtil.clamp(mouseAngleX, -0.8388, -0.2978);
        
        transform.setIdentity();
        transform.translate(0, 0, -500);
        transform.rotateX(mouseAngleX);
        transform.rotateY(-mouseAngleY);
    }

    @Override
    public void updateEnd(Renderer renderer) {
    }
    
    public Transform getInvertTransform() {
        invertTransform.setIdentity();
        invertTransform.rotateY(mouseAngleY);
        invertTransform.rotateX(-mouseAngleX);
        invertTransform.translate(0, 0, 500);
        return invertTransform;
    }
    
    public void setTargetAngle(double ax, double ay, double vp) {
        targetAngleX = ax;
        targetAngleY = ay;
        this.vp = vp;
    }
    
    public boolean isRotationToTargetAngleFinished() {
        return Math.abs(targetAngleX + mouseAngleX - angleX) < 0.01 
                && Math.abs(targetAngleY + mouseAngleY - angleY) < 0.01;
    }
    
    public void resetCameraAngle() {
        targetAngleX = mouseAngleX = -0.60056;
        targetAngleY = mouseAngleY = 0.72384;
        dragCameraRotation();
    }
    
}
