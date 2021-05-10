package main;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Sprite extends GameObject
{
    double velocityX;
    double velocityY;
    public Sprite(double x, double y, Image img)
    {
        super(x, y, img);
        velocityX = 0;
        velocityY = 0;
    }
    public void setVelocity(double x, double y)
    {
        velocityX = x;
        velocityY = y;
    }
    /*
    Update velocity based on the acceleration parameters
    */
    public void updateVelocity(double accelX, double accelY, double deltat)
    {
        this.velocityX += accelX * deltat;
        this.velocityY += accelY * deltat;
    }
    /*
    Update position based on a time delta in seconds
    */
    public void updatePos(double deltat)
    {
        this.posX += velocityX * deltat;
        this.posY += velocityY * deltat;
    }
    /*
    Action taken when collision occurs
     */
    public void doCollision(GameObject that)
    {
        //If right or left edge of sprite collides, make x velocity 0
        if(that.getBoundary().getMinX() < this.getBoundary().getMaxX() || that.getBoundary().getMaxX() < this.getBoundary().getMinX())
        {
            this.setVelocity(0, velocityY);
        }
        //If top or bottom collides, set y velocity to 0
        if(that.getBoundary().getMinY() > (this.getBoundary().getMaxY()) || that.getBoundary().getMaxY() < this.getBoundary().getMinY())
        {
            this.setVelocity(velocityX, 0);
        }
    }

}
