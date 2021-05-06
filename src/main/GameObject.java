import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
public abstract class GameObject 
{
    /*
    This is any object in the game. Anything that can be drawn in the game. By definition, it will be intersectable 
    */
    private GraphicsContext pen;
    private double xPos;
    private double yPos;
    private double height;
    private double width;
    private Image img;
    /*
    Will draw the object on the game screen.
    */
    public GameObject(double x, double y, double h, double w, Image img, GraphicsContext pen)
    {
        xPos = x;
        yPos = y;
        height = h;
        width = w;
        pen = pen;
        img = img;
    }
    /*
    This constructor bases the dimensions of the object based on the Image dimensions.
    */
    public GameObject(double x, double y, Image img, GraphicsContext pen)
    {
        xPos = x;
        yPos = y;
        height = img.getHeight();
        width = img.getWidth();
        pen = pen;
        img = img;
    }
    /*
    Draws this object's image on the canvas.
    */
    public void render()
    {
        pen.drawImage(img, xPos, yPos);
    }
    /*
    Returns a boundary object. Used for determining insersection on canvas. 
    */
    public Rectangle2D getBoundary()
    {
        return new Rectangle2D(xPos, yPos, width, height);
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
    public static void main(String[] args)
    {
        System.out.println("Running tests for GameObject!");
    }
}
