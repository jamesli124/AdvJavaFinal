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

    /**
     * Returns the direction in which this sprite is colliding.
     * LEFT indicates this sprite is colliding on its left side, etc.
     * @param that GameObject being collided with
     * @return Direction
     */
    public Direction getCollisionDirection(GameObject that)
    {
        //List to keep track of the edges of the sprite in contact with the object.
        // Since a corner will be inside the object's rectangle, two sides are colliding
        // This function will find the axis along which the collision is smallest
        ArrayList<Direction> edges = new ArrayList<>();

        // These conditionals find which sides of the sprite are inside the
        // rectangle of that
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

        // This logic determines which edge is least "inside" that's rectangle
        // Since each collision should only be on the order of a few pixels,
        // the smallest collision magnitude is the direction where the collision is
        // actually occurring
        double biggestDiff = 1000;
        Direction smallestDir = Direction.BOTTOM;
        for(Direction edge : edges)
        {
            double thisDiff = getCollisionMagnitude(edge, that);
            if( thisDiff < biggestDiff)
            {
                biggestDiff = thisDiff;
                smallestDir = edge;
            }

        }
        // Return the direction where the collision magnitude is smallest
        return smallestDir;
    }

    /**
     * Returns how far this sprite's rectangle is inside that object's bounding rectangle.
     * @param direction The side of the sprite that is colliding
     * @param that The game object being collided with
     * @return A positive value indicating how deep this edge is inside that's bounding rectangle
     */
    private double getCollisionMagnitude(Direction direction, GameObject that)
    {
        double magnitude = 0;
        switch(direction){
            case TOP:
                magnitude = that.getMaxY() - this.getMinY();
                break;
            case BOTTOM:
                magnitude = this.getMaxY() - that.getMinY();
                break;
            case RIGHT:
                magnitude = this.getMaxX() - that.getMinX();
                break;
            case LEFT:
                magnitude = that.getMaxX() - this.getMinX();
                break;
        }
        return magnitude;
    }

    /**
     * Change the kinematic variables of the sprite when a collision occurs.
     * If it's colliding with the ground, set y velocity to 0, etc.
     * @param that GameObject being collided with
     */
    public void doCollision(GameObject that)
    {
        Direction direction = getCollisionDirection(that);

        switch(direction)
        {
            case TOP:
                if(velocityY < 0)
                {
                    velocityY = 0;
                }
                break;
            case BOTTOM:
                onGround = true;
                if(velocityY > 0)
                {
                    velocityY = 0;
                }
                break;
            case RIGHT:
                if(velocityX > 0)
                {
                    velocityX = 0;
                }
                break;
            case LEFT:
                if(velocityX < 0)
                {
                    velocityX = 0;
                }
                break;
        }
    }

    /**
     * An enum tracking the directions of potential collisions.
     */
    enum Direction
    {
        LEFT,
        RIGHT,
        TOP,
        BOTTOM
    }

}
