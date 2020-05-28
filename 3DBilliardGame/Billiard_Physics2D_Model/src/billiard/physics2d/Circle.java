package billiard.physics2d;

import java.awt.Color;
import java.awt.Graphics2D;

import billiard.physics2d.Shape;

/**
 * Circle shape class.
 * 
 */
public class Circle extends Shape {
    
    private double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }
    
    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.LIGHT_GRAY);
        g.fillOval((int) -radius, (int) -radius, (int) (2 * radius), (int) (2 * radius));
        g.drawLine(0, 0, (int) radius, 0);
    }
    
}
