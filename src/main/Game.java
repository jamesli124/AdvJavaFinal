package main;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
    public void readLevelFromFile(String levelString)
    {
        objects = new ArrayList<GameObject>();
        try (BufferedReader br = Files.newBufferedReader(Path.of(levelString)))
        {
            String line = "";
            while((line = br.readLine()) != null)
            {
                loadObject(line);
            }
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
    }
    private void loadObject(String info)
    {
        //Split line according to white space
        String[] params = info.split("\\s");
        String objType = params[0];
        /*
        Each line of the file specifies a game object. Parameter 0 is
        type of object; parameter 2 and 3 are x and y; parameter 4 is image url
         */
        if (objType.equals("GameObject"))
        {
            objects.add(
                    new GameObject( Double.parseDouble(params[1]), Double.parseDouble(params[2]), new Image(params[3]))

                    );
        }
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
