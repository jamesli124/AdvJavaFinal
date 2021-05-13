package main;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class Sprite extends GameObject
{
    double velocityX;
    double velocityY;
    boolean onGround;

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
        onGround = false;
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
    public ArrayList<Direction> getCollisionDirection(GameObject that)
    {
        //List to keep track of the edges of the sprite in contact with objects.
        // It can be in a corner, where bottom and right collide, for instance
        ArrayList<Direction> edges = new ArrayList<>();
        if(this.getMaxX() > that.getMinX() && this.getMinX() < that.getMinX())
        {
            edges.add(Direction.RIGHT);
        }
        else if(this.getMinX() < that.getMaxX() && this.getMaxX() > that.getMaxX())
        {
            edges.add(Direction.LEFT);
        }
        if(this.getMaxY() > that.getMinY() && this.getMinY() < that.getMinY())
        {
            edges.add(Direction.BOTTOM);
        }
        else if(this.getMinY() < that.getMaxY() && this.getMaxY() > that.getMinY())
        {
            edges.add(Direction.TOP);
        }
        return edges;
    }
    /*
    Action taken when collision occurs
     */
    public void doCollision(GameObject that)
    {
        ArrayList<Direction> edges = getCollisionDirection(that);
        System.out.printf("Colliding on %s edge\n", edges);
        if(edges.contains(Direction.TOP))
        {
            if(velocityY < 0)
            {
                velocityY = 0;
            }
        }
        if (edges.contains(Direction.BOTTOM))
        {
            onGround = true;
            if(velocityY > 0)
            {
                velocityY = 0;
            }
        }
        if(edges.contains(Direction.RIGHT))
        {
            if(velocityX > 0)
            {
                velocityX = 0;
            }
        }
        if(edges.contains(Direction.LEFT))
        {
            if(velocityX < 0)
            {
                velocityX = 0;
            }
        }
    }
    enum Direction
    {
        LEFT,
        RIGHT,
        TOP,
        BOTTOM
    }

}
