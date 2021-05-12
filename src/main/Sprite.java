package main;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Sprite extends GameObject
{
    double velocityX;
    double velocityY;

    /**
     * Class constructor
     *
     * @param x     x position
     * @param y     y position
     * @param h     height
     * @param w     width
     * @param img   Image to be displayed
     */
    public Sprite(double x, double y, double h, double w, Image img)
    {
        super(x, y, h, w, img);
        velocityX = 0;
        velocityY = 0;
    }
    public Sprite(double x, double y, double h, double w, String imgString)
    {
        super(x, y, h, w, imgString);
        velocityX = 0;
        velocityY = 0;
    }

    /**
     * Set velocity of sprite.
     *
     * @param x     x velocity
     * @param y     y velocity
     */
    public void setVelocity(double x, double y)
    {
        velocityX = x;
        velocityY = y;
    }

    public double[] getVelocity()
    {
        return new double[] {velocityX, velocityY};
    }

    /**
     * Update velocity based on the acceleration parameters.
     * 
     * @param accelX
     * @param accelY
     * @param deltat
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
