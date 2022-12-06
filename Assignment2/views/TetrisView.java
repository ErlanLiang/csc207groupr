package views;

import model.TetrisModel;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.TetrisPiece;
import model.TetrisPoint;

import java.util.Arrays;


/**
 * Tetris View
 *
 * Based on the Tetris assignment in the Nifty Assignments Database, authored by Nick Parlante
 */
public class TetrisView {

    //0 refers to green board and red blocks (default), 1 refers to grey board and black blocks,
    //2 refers white board and black blocks
    public int colorContrast = 0;

    private int replaceChance = 3;   //chances for player to replace upcoming piece

    //the color for board and block
    public Color boardColor = Color.GREEN;
    public Color blockColor = Color.RED;

    TetrisModel model; //reference to model
    Stage stage;

    Button startButton, stopButton, loadButton, saveButton, newButton, settingButton, replaceButton; //buttons for functions
    Label scoreLabel = new Label("");
    Label gameModeLabel = new Label("");

    Label nextPieceLabel = new Label("");

    Label replaceChanceLabel = new Label("");

    BorderPane borderPane;
    Canvas canvas;

    Canvas nextOne;
    GraphicsContext gc; //the graphics context will be linked to the canvas

    GraphicsContext draw;

    Boolean paused;
    Timeline timeline;

    private int pieceWidth = 20; //width of block on display
    private double width; //height and width of canvas
    private double height;

    /**
     * Constructor
     *
     * @param model reference to tetris model
     * @param stage application stage
     */

    public TetrisView(TetrisModel model, Stage stage) {
        this.model = model;
        this.stage = stage;
        initUI();
    }

    /**
     * Initialize interface
     */
    private void initUI() {
        this.paused = false;
        this.stage.setTitle("CSC207 Tetris");
        this.width = this.model.getWidth()*pieceWidth + 2;
        this.height = this.model.getHeight()*pieceWidth + 2;

        borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: #121212;");

        //add canvas
        canvas = new Canvas(this.width, this.height);
        canvas.setId("Canvas");
        gc = canvas.getGraphicsContext2D();

        // add canvas for drawing next piece
        nextOne = new Canvas(82, 82);
        nextOne.setId("NextOne");
        draw = nextOne.getGraphicsContext2D();

        //labels
        gameModeLabel.setId("GameModeLabel");
        scoreLabel.setId("ScoreLabel");

        gameModeLabel.setText("Player is: Human");
        gameModeLabel.setMinWidth(250);
        gameModeLabel.setFont(new Font(20));
        gameModeLabel.setStyle("-fx-text-fill: #e8e6e3");

        final ToggleGroup toggleGroup = new ToggleGroup();

        RadioButton pilotButtonHuman = new RadioButton("Human");
        pilotButtonHuman.setToggleGroup(toggleGroup);
        pilotButtonHuman.setSelected(true);
        pilotButtonHuman.setUserData(Color.SALMON);
        pilotButtonHuman.setFont(new Font(16));
        pilotButtonHuman.setStyle("-fx-text-fill: #e8e6e3");

        RadioButton pilotButtonComputer = new RadioButton("Computer (Default)");
        pilotButtonComputer.setToggleGroup(toggleGroup);
        pilotButtonComputer.setUserData(Color.SALMON);
        pilotButtonComputer.setFont(new Font(16));
        pilotButtonComputer.setStyle("-fx-text-fill: #e8e6e3");

        scoreLabel.setText("Score is: 0");
        scoreLabel.setFont(new Font(20));
        scoreLabel.setStyle("-fx-text-fill: #e8e6e3");

        nextPieceLabel.setText("Next piece: ");
        nextPieceLabel.setFont(new Font(20));
        nextPieceLabel.setStyle("-fx-text-fill: #e8e6e3");

        replaceChanceLabel.setText("You have " + replaceChance + " replace chances");
        replaceChanceLabel.setFont(new Font(20));
        replaceChanceLabel.setStyle("-fx-text-fill: #e8e6e3");

        //add buttons
        replaceButton = new Button("Replace");
        replaceButton.setId("Replace");
        replaceButton.setPrefSize(150, 50);
        replaceButton.setFont(new Font(12));
        replaceButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");

        settingButton = new Button("Setting");
        settingButton.setId("Setting");
        settingButton.setPrefSize(150, 50);
        settingButton.setFont(new Font(12));
        settingButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");

        startButton = new Button("Start");
        startButton.setId("Start");
        startButton.setPrefSize(150, 50);
        startButton.setFont(new Font(12));
        startButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");

        stopButton = new Button("Stop");
        stopButton.setId("Start");
        stopButton.setPrefSize(150, 50);
        stopButton.setFont(new Font(12));
        stopButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");

        saveButton = new Button("Save");
        saveButton.setId("Save");
        saveButton.setPrefSize(150, 50);
        saveButton.setFont(new Font(12));
        saveButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");

        loadButton = new Button("Load");
        loadButton.setId("Load");
        loadButton.setPrefSize(150, 50);
        loadButton.setFont(new Font(12));
        loadButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");

        newButton = new Button("New Game");
        newButton.setId("New");
        newButton.setPrefSize(150, 50);
        newButton.setFont(new Font(12));
        newButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");

        HBox controls = new HBox(20, saveButton, loadButton, newButton, startButton, stopButton, settingButton, replaceButton);
        controls.setPadding(new Insets(20, 20, 20, 20));
        controls.setAlignment(Pos.CENTER);

        Slider slider = new Slider(0, 100, 50);
        slider.setShowTickLabels(true);
        slider.setStyle("-fx-control-inner-background: palegreen;");

        VBox vBox = new VBox(20, slider);
        vBox.setPadding(new Insets(20, 20, 20, 20));
        vBox.setAlignment(Pos.TOP_CENTER);

        VBox scoreBox = new VBox(20, scoreLabel, replaceChanceLabel, nextPieceLabel, nextOne, gameModeLabel, pilotButtonHuman, pilotButtonComputer);
        scoreBox.setPadding(new Insets(20, 20, 20, 20));
        vBox.setAlignment(Pos.TOP_CENTER);

        toggleGroup.selectedToggleProperty().addListener((observable, oldVal, newVal) -> swapPilot(newVal));

        //timeline structures the animation, and speed between application "ticks"
        timeline = new Timeline(new KeyFrame(Duration.seconds(0.25), e -> updateBoard()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        // edit the upcoming piece when the user hits the replaceButton
        replaceButton.setOnAction(e -> {
            if (replaceChance > 0) {
                this.createReplaceView();
                this.borderPane.requestFocus();
            }
        });

        //configure this such that you edit the setting when the user hits the settingButton
        settingButton.setOnAction(e -> {
            this.createSettingView();
            this.borderPane.requestFocus();
        });

        //configure this such that you start a new game when the user hits the newButton
        //Make sure to return the focus to the borderPane once you're done!
        newButton.setOnAction(e -> {
            this.model.newGame();
            this.borderPane.requestFocus();
        });

        //configure this such that you restart the game when the user hits the startButton
        //Make sure to return the focus to the borderPane once you're done!
        startButton.setOnAction(e -> {
            this.model.restartGame();
            this.borderPane.requestFocus();
        });

        //configure this such that you pause the game when the user hits the stopButton
        //Make sure to return the focus to the borderPane once you're done!
        stopButton.setOnAction(e -> {
            this.model.stopGame();
            this.borderPane.requestFocus();
        });

        //configure this such that the save view pops up when the saveButton is pressed.
        //Make sure to return the focus to the borderPane once you're done!
        saveButton.setOnAction(e -> {
            this.createSaveView();
            this.borderPane.requestFocus();
        });

        //configure this such that the load view pops up when the loadButton is pressed.
        //Make sure to return the focus to the borderPane once you're done!
        loadButton.setOnAction(e -> {
            this.createLoadView();
            this.borderPane.requestFocus();
        });

        //configure this such that you adjust the speed of the timeline to a value that
        //ranges between 0 and 3 times the default rate per model tick.  Make sure to return the
        //focus to the borderPane once you're done!
        slider.setOnMouseReleased(e -> {
            this.timeline.setRate(slider.getValue() * 0.03);
            this.borderPane.requestFocus();
        });

        //configure this such that you can use controls to rotate and place pieces as you like!!
        //You'll want to respond to tie key presses to these moves:
        // TetrisModel.MoveType.DROP, TetrisModel.MoveType.ROTATE, TetrisModel.MoveType.LEFT
        //and TetrisModel.MoveType.RIGHT
        //make sure that you don't let the human control the board
        //if the autopilot is on, however.
        borderPane.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent k) {
                if (!model.getAutoPilotMode()) {
                    KeyCode code = k.getCode();
                    if (code == KeyCode.UP) {
                        model.modelTick(TetrisModel.MoveType.ROTATE);
                    } else if (code == KeyCode.DOWN) {
                        model.modelTick(TetrisModel.MoveType.DROP);
                    } else if (code == KeyCode.LEFT) {
                        model.modelTick(TetrisModel.MoveType.LEFT);
                    } else if (code == KeyCode.RIGHT) {
                        model.modelTick(TetrisModel.MoveType.RIGHT);
                    }
                }
            }
        });

        borderPane.setTop(controls);
        borderPane.setRight(scoreBox);
        borderPane.setCenter(canvas);
        borderPane.setBottom(vBox);

        var scene = new Scene(borderPane, 800, 800);
        this.stage.setScene(scene);
        this.stage.show();
    }

    /**
     * Get user selection of "autopilot" or human player
     *
     * @param value toggle selector on UI
     */
    private void swapPilot(Toggle value) {
        RadioButton chk = (RadioButton)value.getToggleGroup().getSelectedToggle();
        String strVal = chk.getText();
        if (strVal.equals("Computer (Default)")){
            this.model.setAutoPilotMode();
            gameModeLabel.setText("Player is: Computer (Default)");
        } else if (strVal.equals("Human")) {
            this.model.setHumanPilotMode();
            gameModeLabel.setText("Player is: Human");
        }
        borderPane.requestFocus(); //give the focus back to the pane with the blocks.
    }

    /**
     * Update board (paint pieces and score info)
     */
    private void updateBoard() {
        if (this.paused != true) {
            paintBoard();
            this.model.modelTick(TetrisModel.MoveType.DOWN);
            updateNextChance();
            updateScore();
        }
    }

    /**
     * Update score on UI
     */
    private void updateScore() {
        if (this.paused != true) {
            scoreLabel.setText("Score is: " + model.getScore() + "\nPieces placed:" + model.getCount());
        }
    }

    /**
     * Update next piece and replace chance on UI
     */
    private void updateNextChance() {
        replaceChanceLabel.setText(replaceChance + " replace chances");
        drawNext(model.getNextPiece());
    }

    /**
     * Methods to calibrate sizes of pixels relative to board size
     */
    private final int yPixel(int y) {
        return (int) Math.round(this.height -1 - (y+1)*dY());
    }
    private final int xPixel(int x) {
        return (int) Math.round((x)*dX());
    }
    private final float dX() {
        return( ((float)(this.width-2)) / this.model.getBoard().getWidth() );
    }
    private final float dY() {
        return( ((float)(this.height-2)) / this.model.getBoard().getHeight() );
    }

    /**
     * Draw the board
     */
    public void paintBoard() {
        // Draw a rectangle around the whole screen
        gc.setStroke(Color.GREEN);
        //fill the board with boardColor
        gc.setFill(boardColor);
        gc.fillRect(0, 0, this.width-1, this.height-1);

        // Draw the line separating the top area on the screen
        gc.setStroke(Color.BLACK);
        int spacerY = yPixel(this.model.getBoard().getHeight() - this.model.BUFFERZONE - 1);
        gc.strokeLine(0, spacerY, this.width-1, spacerY);

        // Factor a few things out to help the optimizer
        final int dx = Math.round(dX()-2);
        final int dy = Math.round(dY()-2);
        final int bWidth = this.model.getBoard().getWidth();

        int x, y;
        // Loop through and draw all the blocks; sizes of blocks are calibrated relative to screen size
        for (x=0; x<bWidth; x++) {
            int left = xPixel(x);	// the left pixel
            // draw from 0 up to the col height
            final int yHeight = this.model.getBoard().getColumnHeight(x);
            for (y=0; y<yHeight; y++) {
                if (this.model.getBoard().getGrid(x, y)) {
                    if (model.Isboom) { //boom: the piece will change to black if current piece is boom.
                        gc.setFill(Color.BLACK);
                    }
                    else {
                        //fill the block with blockColor
                        gc.setFill(blockColor);
                    }
                    gc.fillRect(left+1, yPixel(y)+1, dx, dy);
                    gc.setFill(Color.GREEN);
                }
            }
        }

    }

    /**
     * Create the view to save a board to a file
     */
    private void createSaveView(){
        SaveView saveView = new SaveView(this);
    }

    /**
     * Create the view to select a board to load
     */
    private void createLoadView(){
        LoadView loadView = new LoadView(this);
    }

    /**
     * Create the view to edit the setting of the game
     */
    private void createSettingView(){
        SettingView settingViewView = new SettingView(this);
    }

    /**
     * Create the view to replace the upcoming piece
     */
    private void createReplaceView(){
        ReplaceView replaceView = new ReplaceView(this);
    }

    /**
     * Draw the next piece on the UI
     */
    public void drawNext(TetrisPiece piece) {
        draw.clearRect(0, 0, 82, 82);
        draw.setLineWidth(3);
        draw.setStroke(Color.BLACK);
        draw.setFill(Color.WHITE);
        TetrisPiece[] pieces = model.getPieces();
        boolean flag = true;
        for (TetrisPiece type : pieces) {
            if (type.equals(piece)) {
                flag = false;
                int x = 1;
                int y = 61;
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 4; j++) {
                        TetrisPoint curr = new TetrisPoint(i, j);
                        if (Arrays.asList(piece.getBody()).contains(curr)) {
                            draw.fillRect(82 - y, 61 - x, 20, 20);
                            draw.strokeRect(82 - y, 61 - x, 20, 20);
                        }
                        x += 20;
                    }
                    x = 1;
                    y -= 20;
                }
            }
        }
        if (flag) {
            draw.fillRect(31, 31, 20, 20);
            draw.strokeRect(31, 31, 20, 20);
        }
    }

    /**
     * Reduce the replace chance by 1 after
     * each replacement
     */
    public void reduceChance() {
        replaceChance -= 1;
    }

    /**
     * Getter for replace chance
     *
     * @return replace chance
     */
    public int getChance() {
        return replaceChance;
    }
}