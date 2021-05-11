package main;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.layout.GridPane;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
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

import java.util.ArrayList;

public class Menu extends Application
{
    public int SCREEN_WIDTH = 600;
    public int SCREEN_HEIGHT = 400;
    private Canvas gameScreen;
    private Game game;
    private GraphicsContext gameGC;
    private ArrayList<String> input;
    public Menu()
    {
        gameScreen = new Canvas(SCREEN_WIDTH, SCREEN_HEIGHT);
        game = new Game();
        gameGC = gameScreen.getGraphicsContext2D();
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


      /**
      try {
        Thread.sleep(7500);
      }
      catch(InterruptedException ex)
      {
        Thread.currentThread().interrupt();
      }
      */

      BorderPane bpMain = new BorderPane();
      bpMain.setPrefSize(screenWidth / 4, screenHeight / 4);
      TextField address = new TextField();
      address.setFont(new Font ("Comic Sans MS Regular", 24));
      address.setEditable(false);
      address.setPrefWidth(screenWidth * .6);
      /**
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
    private Scene makeGameScene()
    {
        BorderPane bpGame = new BorderPane();

        bpGame.setCenter(gameScreen);

        Scene gameScene = new Scene(bpGame);

        // Initialize input list
        input = new ArrayList<>();

        gameScene.setOnKeyPressed(
                new EventHandler<KeyEvent>()
                {
                    public void handle(KeyEvent e)
                    {
                        String code = e.getCode().toString();
                        if ( !input.contains(code) )
                            input.add( code );
                    }
                });

        gameScene.setOnKeyReleased(
                new EventHandler<KeyEvent>()
                {
                    public void handle(KeyEvent e)
                    {
                        String code = e.getCode().toString();
                        input.remove( code );
                    }
                });

        return gameScene;
    }
    /*
    Inner class GameClock updates the game with AnimationTimer functionality
     */
    class GameClock extends AnimationTimer
    {
        private long lastNanoTime = System.currentTimeMillis() * 1000;
        /*
        Runs every time frame is loaded in JavaFX program.
         */
        @Override
        public void handle(long currentNanoTime)
        {
            // calculate time since last update.
            double elapsedTime = (currentNanoTime - lastNanoTime) / 1000000000.0;
            lastNanoTime = currentNanoTime;

            // Pass input to Game.java and let it update game stuff

            // Game logic

            game.handleKeyboardInput(input);

            // Render all images
            for(Renderable item : game.getRenderList())
            {
                item.render(gameGC);
            }

        }


    }
}


