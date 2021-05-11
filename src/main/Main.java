import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.layout.GridPane;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.geometry.Pos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.Priority;
import javafx.scene.input.MouseButton;
import javafx.stage.Screen;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;

/**
 * Main class which displays different scenes
 */
public class Main extends Application
{
    public Main()
    {

    }

    @Override
    public void init()
    {
    }

    @Override
    public void start(Stage primary)
    {

        Rectangle2D screenDimensions = Screen.getPrimary().getVisualBounds();
        double screenWidth = screenDimensions.getWidth();
        double screenHeight = screenDimensions.getHeight();

        // Creating Scene splash
        Image splashImage = new Image("https://imgs.xkcd.com/comics/excel_lambda_2x.png"); //"https://utility0.ncssm.edu/~morrison/images/samGetsIt.jpeg"
        ImageView iv1 = new ImageView();
        iv1.setImage(splashImage);
        if (iv1.getImage().getWidth() > screenWidth) {
            iv1.setFitWidth(screenWidth);
            iv1.setPreserveRatio(true);
            iv1.setSmooth(true);
            iv1.setCache(true);
        }
        BorderPane bpSplash = new BorderPane();
        bpSplash.setCenter(iv1);
        bpSplash.setStyle("-fx-background-color: black");
        Scene splash = new Scene(bpSplash, screenWidth, screenHeight);
        primary.setWidth(screenDimensions.getWidth());
        primary.setHeight(screenDimensions.getHeight());


        /*
        try {
        Thread.sleep(7500);
        }
      catch(InterruptedException ex)
        {
        Thread.currentThread().interrupt();
        }
        */

        // Creating Scene menu
        BorderPane bpMain = new BorderPane();
        bpMain.setPrefSize(screenWidth / 4, screenHeight / 4);
        Label address = new Label();
        address.setFont(new Font ("Comic Sans MS", 24));
        // address.setEditable(false);
        address.setPrefWidth(screenWidth * .6);
        /*
        address.setStyle("-fx-text-fill: white");
        address.setStyle("-fx-opacity: 1");
        address.setStyle("-fx-background-color: black");
        */
        address.setText("If you have any complaints, please mail them to 612 CONCORDIA CT CHAPEL HILL, NC 27514");
        Button play = new Button("Play");
        Button options = new Button("Options");
        Button complaints = new Button("Complaints?");
        Button back = new Button("<-- Back");
        complaints.setOnAction(e -> {
            bpMain.setCenter(address);
            bpMain.setLeft(back);
        });
        Button quit = new Button("Quit");
        quit.setOnAction(e -> Platform.exit() );
        VBox menuItems = new VBox();
        menuItems.getChildren().addAll(play, options, complaints, quit);

        back.setOnAction(e -> {
            bpMain.setLeft(null);
            bpMain.setCenter(menuItems);
        });

        bpMain.setCenter(menuItems);
        //bpMain.setStyle("-fx-background-color: black");

        Scene mainMenu = new Scene(bpMain, screenWidth, screenHeight);

        iv1.setOnMouseClicked(e -> {
            primary.setScene(mainMenu);
        });
        primary.setScene(splash);
        primary.setWidth(screenDimensions.getWidth());
        primary.setHeight(screenDimensions.getHeight());
        primary.sizeToScene();
        primary.show();
    }

    @Override
    public void stop()
    {

    }
}
