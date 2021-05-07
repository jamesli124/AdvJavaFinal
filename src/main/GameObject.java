package main;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
public class GameObject 
{
    /*
    This is any object in the game. Anything that can be drawn in the game. By definition, it will be intersectable 
    */
    protected double posX;
    protected double posY;
    protected double height;
    protected double width;
    protected Image img;
    /*
    Will draw the object on the game screen.
    */
    public GameObject(double x, double y, double h, double w, Image img)
    {
        this.posX = x;
        this.posY = y;
        this.height = h;
        this.width = w;
        this.img = img;
    }
    /*
    This constructor bases the dimensions of the object based on the Image dimensions.
    */
    public GameObject(double x, double y, Image img)
    {
        this.posX = x;
        this.posY = y;
        this.height = img.getHeight();
        this.width = img.getWidth();
        this.img = img;
    }
    /*
    Draws this object's image on the canvas.
    */
    public void render(GraphicsContext pen)
    {
        pen.drawImage(img, posX, posY);
    }
    /*
    Returns a boundary object. Used for determining insersection on canvas.
    */
    public Rectangle2D getBoundary()
    {
        return new Rectangle2D(posX, posY, width, height);
    }

    /*
    Test if this GameObject intersects with that GameObject
    */
    public boolean intersects(GameObject that)
    {
        if (this.getBoundary().intersects(that.getBoundary()))
        {
            return true;
        }
        return false;
    }
    public void setX(double x)
    {
        this.posX = x;
    }
    public void setY(double y)
    {
        this.posY = y;
    }
    public void setPos(double x, double y)
    {
        this.setX(x);
        this.setY(y);
    }
    public static void main(String[] args)
    {
        System.out.println("Running tests for GameObject!");
    }
}
