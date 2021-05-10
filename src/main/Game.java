package main;

import javafx.geometry.Rectangle2D;

import java.util.ArrayList;

public class Game {
    /*
    This class manages the game state, including the ArrayLists that need to be drawn
     */
    private ArrayList<GameObject> objects;
    private ArrayList<Sprite> sprites;
    private double cameraX;
    private double cameraY;
    public double CAMERA_WIDTH = 600;
    public double CAMERA_HEIGHT = 400;
    public double GRAVITY = 10;

    public Game()
    {
        objects = new ArrayList<GameObject>();
        cameraX = 0;
        cameraY = 0;
    }
    public void setCameraX(double x)
    {
        cameraX = x;
    }
    public void setCameraY(double y)
    {
        cameraY = y;
    }
    public Rectangle2D getCameraRectangle()
    {
        return new Rectangle2D(cameraX, cameraY, CAMERA_WIDTH, CAMERA_HEIGHT);
    }
    /*
    Detects what GameObjects are in the camera frame and makes a list of those.
    This will be drawn in the main game loop.
     */
    public ArrayList<Renderable> getRenderList()
    {
        ArrayList<Renderable> renderList = new ArrayList<Renderable>();
        Rectangle2D cameraBox = getCameraRectangle();
        for(GameObject obj : objects)
        {
            if(obj.intersects(cameraBox))
            {
                renderList.add(obj);
            }
        }
        return renderList;

    }
    public void updateSprites(double deltat)
    {
        for(Sprite sprite : sprites)
        {
            sprite.updatePos(deltat);
            sprite.updateVelocity(0, GRAVITY, deltat);
            for(GameObject obj : objects)
            {
                if(sprite.intersects(obj))
                {
                    sprite.doCollision(obj);
                }
            }
        }
    }

}
