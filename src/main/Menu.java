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
import java.io.File;
import java.io.IOException;

public class Menu extends Application
{
    public int SCREEN_WIDTH = 600;
    public int SCREEN_HEIGHT = 400;
    private Canvas gameScreen;
    private Game game;
    private GraphicsContext gameGC;
    private ArrayList<String> input;
    private Button pauseButton;
    private boolean paused;
    private GameClock gameClock;
    private Scene mainMenu;
    private Stage primaryStage;

    public Menu()
    {
        gameScreen = new Canvas(SCREEN_WIDTH, SCREEN_HEIGHT);
        gameGC = gameScreen.getGraphicsContext2D();
        // initialize game clock
        gameClock = new GameClock();
        paused = false;
    }
    @Override
    public void init()
    {
    }

    @Override
    public void start(Stage primary)
    {
        primaryStage = primary;
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
        // Initialize buttons
        Button play = new Button("Play"); //Will load latest.level
        // Play button calls method startGame()
        play.setOnAction(e -> startGame(primary, "latest.level"));
        Button select = new Button("Level Select"); //opens level select menu

        Button options = new Button("Options");
        Button complaints = new Button("Complaints?");
        Button back = new Button("<-- Back");
        complaints.setOnAction(e -> {
            bpMain.setCenter(address);
            bpMain.setLeft(back);
        });
        // Quit button
        Button quit = new Button("Quit");
        quit.setOnAction(e -> Platform.exit() );
        VBox menuItems = new VBox();
        menuItems.getChildren().addAll(play, select, options, complaints, quit);

        back.setOnAction(e -> {
            bpMain.setLeft(null);
            bpMain.setCenter(menuItems);
        });

        bpMain.setCenter(menuItems);
        //bpMain.setStyle("-fx-background-color: black");

      mainMenu = new Scene(bpMain, screenWidth, screenHeight);
      iv1.setOnMouseClicked(e -> {
        primary.setScene(mainMenu);
      });

      // select button functionality
    select.setOnAction(e -> {
        primary.setScene(makeSelectScene(primary, mainMenu));
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

    /**
     * Sets scene to gameScene, starts gameClock, reads in a level
     * @param primary The main stage needing to be set
     */
    private void startGame(Stage primary, String levelString)
    {
        // unpause
        paused = false;
        // Initialize game object
        game = new Game();
        // set scene to return value of makeGameScene()
        primary.setScene(makeGameScene());
        // Initialize game clock
        //gameClock = new GameClock();
        // Read in level file
        game.readLevelFromFile(levelString);
        gameClock.start();
    }
    /**
     * Builds the levelSelect menu.
     *
     * @return Scene makeSelectScene that displays the level options.
     */
     private Scene makeSelectScene(Stage primary, Scene mainMenu)
     {
       BorderPane bpSelect = new BorderPane();

       Button backButton = new Button("Back");
       backButton.setOnAction(e -> primary.setScene(mainMenu));

       LevelButton savedLevel = new LevelButton("Saved Level", "latest.level");
       LevelButton lvlOne = new LevelButton("Level 1", "level1.level");
       LevelButton lvlTwo = new LevelButton("Level 2", "level2.level");
       LevelButton lvlThree = new LevelButton("Level 3", "level3.level");


       VBox levelMenu = new VBox();
       levelMenu.getChildren().addAll(savedLevel, lvlOne, lvlTwo, lvlThree);

       bpSelect.setTop(backButton);
       bpSelect.setCenter(levelMenu);

       Scene levelSelect = new Scene(bpSelect);

       return levelSelect;
     }
    /**
     * Builds the gameScene, which is a Canvas housed in a BorderPane.
     * Also defines input handling for the scene: inputs recorded to
     * ArrayList input, which holds all keys currently pressed by player
     * @return Scene gameScene that displays game graphics
     */
    private Scene makeGameScene()
    {
        BorderPane bpGame = new BorderPane();

        HBox topMenu = new HBox();

        // button that pauses game clock
        Button pauseButton = new Button(Character.toString((char) Integer.parseInt("23F8", 16)));

        // button that returns to mainMenu
        Button returnButton = new Button(Character.toString((char) Integer.parseInt("2190", 16)));
        returnButton.setOnAction(e ->
        {
            primaryStage.setScene(mainMenu);
        });

        // button that saves level
        Button saveButton = new Button("Save");
        saveButton.setOnAction( e ->
        {
          game.saveLevel();
        });
        topMenu.getChildren().addAll(pauseButton, returnButton, saveButton);

        // pause button that pauses game when pressed
        pauseButton = new Button(Character.toString((char) Integer.parseInt("23F8", 16)));
        pauseButton.setOnAction(e ->
        {
            if (paused)
            {
                gameClock.start();
                paused = false;
                // Will hard reset game clock so that elapsedTime is not equal to the pause time
                gameClock.lastNanoTime = 0;
            } else {
                gameClock.stop();
                paused = true;
            }
        });
        //TODO: spacebar pausing, GUI indication that the game is paused

        bpGame.setTop(topMenu);
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

        // same EventHandlers as before because jfx is being stupid and thinks the button needs to take all key presses
        pauseButton.setOnKeyPressed(
                new EventHandler<KeyEvent>()
                {
                    public void handle(KeyEvent e)
                    {
                        String code = e.getCode().toString();
                        if ( !input.contains(code) )
                            input.add( code );
                    }
                });

        pauseButton.setOnKeyReleased(
                new EventHandler<KeyEvent>()
                {
                    public void handle(KeyEvent e)
                    {
                        String code = e.getCode().toString();
                        input.remove( code );

                    }
                });

        returnButton.setOnKeyPressed(
                new EventHandler<KeyEvent>()
                {
                    public void handle(KeyEvent e)
                    {
                        String code = e.getCode().toString();
                        if ( !input.contains(code) )
                            input.add( code );
                    }
                });

        returnButton.setOnKeyReleased(
                new EventHandler<KeyEvent>()
                {
                    public void handle(KeyEvent e)
                    {
                        String code = e.getCode().toString();
                        input.remove( code );

                    }
                });

        saveButton.setOnKeyPressed(
                new EventHandler<KeyEvent>()
                {
                    public void handle(KeyEvent e)
                    {
                        String code = e.getCode().toString();
                        if ( !input.contains(code) )
                            input.add( code );
                    }
                });

        saveButton.setOnKeyReleased(
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
        public long lastNanoTime = 0;
        /*
        Runs every time frame is loaded in JavaFX program.
         */
        @Override
        public void handle(long currentNanoTime)
        {
            // calculate time since last update.
            // if this is the first frame, skip it
            if(lastNanoTime < 1)
            {
                lastNanoTime = currentNanoTime;
                return;
            }
            // Calculate elapsed time since last frame update
            double elapsedTime = (currentNanoTime - lastNanoTime) / 1000000000.0;
            lastNanoTime = currentNanoTime;

            // Pass input to Game.java and let it update game stuff
            game.handleKeyboardInput(input);
            game.updateSprites(elapsedTime);

            // Clear screen
            gameGC.clearRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

            // Render all objects in the camera frame
            for(Renderable item : game.getRenderList())
            {
                item.render(gameGC, game.getCameraX());
            }

        }


    }
    class LevelButton extends Button
    {
        private String levelString;

        LevelButton(String text, String levelString)//this could also be handled with an enum
        {
            super(text);
            this.levelString = levelString;
            setOnAction (e -> {
                startGame(primaryStage, levelString);
            });
        }
    }
}
