package main;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
public class GameObject implements Renderable
{
    /*
    This is any object in the game. Anything that can be drawn in the game. By definition, it will be intersectable 
    */
    protected double posX;
    protected double posY;
    protected double height;
    protected double width;
    protected Image img;
    /**
     * Class constructor.
     *
     * @param x     horizontal location
     * @param y     vertical location
     * @param h     height
     * @param w     width
     * @param img   image to be displayed.
    */
    public GameObject(double x, double y, double h, double w, Image img)
    {
        this.posX = x;
        this.posY = y;
        this.height = h;
        this.width = w;
        this.img = img;
    }
    /**
     * Class constructor that bases the dimensions of the object based on the Image dimensions.
     *
     * @param x     horizontal location
     * @param y     vertical location
     * @param img   image to be displayed.
    */
    public GameObject(double x, double y, Image img)
    {
        this.posX = x;
        this.posY = y;
        this.height = img.getHeight();
        this.width = img.getWidth();
        this.img = img;
    }
    /**
     * Draws this object's image on the canvas.
     *
     * @param pen   pen for the canvas to be drawn on.
    */
    public void render(GraphicsContext pen)
    {
        pen.drawImage(img, posX, posY);
    }
    /**
     * Returns a boundary object. Used for determining intersection on canvas.
     *
     * @return      a Rectangle2D representing the bounding box.
    */
    public Rectangle2D getBoundary()
    {
        return new Rectangle2D(posX, posY, width, height);
    }

    /**
     * Test if this GameObject intersects with that GameObject
     *
     * @param that  the GameObject to check for intersection with.
     * @return      boolean representing whether or not there is an intersection.
    */
    public boolean intersects(GameObject that)
    {
        if (this.getBoundary().intersects(that.getBoundary()))
        {
            return true;
        }
        return false;
    }

    /**
     * Test if this GameObject intersects with that Rectangle2D
     *
     * @param that  the Rectangle2D to check for intersection with.
     * @return      boolean representing whether or not there is an intersection.
     */
    public boolean intersects(Rectangle2D that)
    {
        if (this.getBoundary().intersects(that))
        {
            return true;
        }
        return false;
    }

    /**
     * Sets the x position.
     *
     * @param x     double to set x to
     */
    public void setX(double x)
    {
        this.posX = x;
    }

    /**
     * Sets the y position.
     *
     * @param y     double to set y to
     */
    public void setY(double y)
    {
        this.posY = y;
    }

    /**
     * Sets position of the GameObject
     *
     * @param x     double to set x to
     * @param y     double to set y to
     */
    public void setPos(double x, double y)
    {
        this.setX(x);
        this.setY(y);
    }

    /**
     * Returns location of top right corner
     *
     * @return      Point2D containing location of top right corner of GameObject
     */
    public Point2D getTopRight()
    {
        return new Point2D(this.posX + this.width, this.posY);
    }

    /**
     * Returns location of top left corner
     *
     * @return      Point2D containing location of top left corner of GameObject
     */
    public Point2D getTopLeft()
    {
        return new Point2D(this.posX, this.posY);
    }

    /**
     * Returns location of bottom left corner
     *
     * @return      Point2D containing location of bottom left corner of GameObject
     */
    public Point2D getBottomLeft()
    {
        return new Point2D(this.posX, this.posY + this.height);
    }

    /**
     * Returns location of bottom right corner
     *
     * @return      Point2D containing location of top right corner of GameObject
     */
    public Point2D getBottomRight()
    {
        return new Point2D(this.posX + this.width, this.posY + this.height);
    }

    /**
     * The main method to test this class.
     *
     * @param args  String array of command line arguments
     */
    public static void main(String[] args)
    {
        System.out.println("Running tests for GameObject!");
    }
}
