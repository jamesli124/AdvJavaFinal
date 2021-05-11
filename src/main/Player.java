package main;

import javafx.scene.image.Image;

public class Player extends Sprite{

    private double JUMP_SPEED = -30;
    private double WALK_SPEED = 20;

    public Player(double x, double y, double h, double w, String imgString)
    {
        super(x, y, h, w, imgString);
    }
    /*
    Makes the player jump
     */
    public void jump()
    {
        velocityY = JUMP_SPEED;
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
