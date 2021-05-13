package main;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    public double GRAVITY = 40;
    private Player player;

    public Game()
    {
        objects = new ArrayList<GameObject>();
        sprites = new ArrayList<>();
        cameraX = 0;
        cameraY = 0;
    }

    public void readLevelFromFile(String levelString)
    {
        objects = new ArrayList<GameObject>();
        String currentPath = Paths.get("").toAbsolutePath().toString();
        System.out.println(currentPath.toString());
        try (BufferedReader br = Files.newBufferedReader(Path.of(currentPath + "/src/resources/levels/" + levelString)))
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
        type of object; parameter 1 and 2 are x and y; parameter 3 and 4 are height and width
        parameter 5 is the String representing location of the location of the Image
         */
        if (objType.equals("GameObject"))
        {
            System.out.println("Loading object!");
            objects.add(
                    new GameObject( Double.parseDouble(params[1]), Double.parseDouble(params[2]),
                            Double.parseDouble(params[3]), Double.parseDouble(params[4]),
                            getImageStringOf(params[5]))

                    );
        }
        else if (objType.equals("Player"))
        {
            player = new Player( Double.parseDouble(params[1]), Double.parseDouble(params[2]),
                    Double.parseDouble(params[3]), Double.parseDouble(params[4]),
                    getImageStringOf(params[5]));
            objects.add(player);
            sprites.add(player);


        }
    }

    private String getImageStringOf(String s)
    {
        return("resources/" + s + ".png");
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
            for(GameObject obj : objects)
            {
                if(sprite != obj) {
                    if (sprite.intersects(obj)) {
                        sprite.doCollision(obj);
                    }
                }
            }
            sprite.updatePos(deltat);
            sprite.updateVelocity(0, GRAVITY, deltat);
        }
    }
    /*
    Will check if this sprite is on the ground.
     */
    private boolean isOnGround(Sprite entity)
    {
        return true;
    }
    public void handleKeyboardInput(ArrayList<String> input)
    {
        // Set x velocity to 0, but leave y velocity untouched.
        player.setVelocity(0, player.getVelocity()[1]);
        if(input.contains("UP") && isOnGround(player))
        {
            player.jump();
        }
        if(input.contains("RIGHT"))
        {
            player.moveRight();
        }
        if(input.contains("LEFT"))
        {
            player.moveLeft();
        }

    }

}
