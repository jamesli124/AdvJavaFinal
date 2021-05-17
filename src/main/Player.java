package main;

import javafx.scene.image.Image;

public class Player extends Sprite{

    private double JUMP_SPEED = -200;
    private double WALK_SPEED = 120;

    public Player(double x, double y, double h, double w, String imgString)
    {
        super(x, y, h, w, imgString);
    }
    /*
    Makes the player jump
     */
    public void jump()
    {
        if(onGround)
        {
            velocityY = JUMP_SPEED;
            onGround = false;
        }
    }
    public void moveRight()
    {
        velocityX = WALK_SPEED;
    }
    public void moveLeft()
    {
        velocityX = -WALK_SPEED;
    }

}
