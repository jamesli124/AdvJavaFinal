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

}
