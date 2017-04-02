/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mangrove;

import java.awt.Point;
import java.awt.geom.Point2D;

/**
 *
 * @author Hasan Mangrove
 */
public class DoublePoint extends Point2D{
    
    public double x;
    public double y;

    
    //konstruktor
    public DoublePoint() {
        x = 0;
        y = 0;
    }
    
    public DoublePoint(double x, double y) {
        setLocation(x, y);
    }
    
    
    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public void setLocation(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public String toString() {
        return "DoublePoint[x: " + x + ", y: " + y + "]";
    }
    
    public Point toPoint() {
        return new Point((int) Math.round(this.x), (int) Math.round(this.y));
    }
}
