import model.EasyMode;
import model.TetrisModel;
import views.TetrisView;

import javafx.application.Application;
import javafx.stage.Stage;

/** 
 * A Tetris Application, in JavaFX
 * 
 * Based on the Tetris assignment in the Nifty Assignments Database, authored by Nick Parlante
 */
public class TetrisApp extends Application {
    TetrisModel model;
    TetrisView view;

    /** 
     * Main method
     * 
     * @param args agument, if any
     */
    public static void main(String[] args) {
        launch(args);
    }

    /** 
     * Start method.  Control of application flow is dictated by JavaFX framework
     * 
     * @param primaryStage stage upon which to load GUI elements
     */
    @Override
    public void start(Stage primaryStage) {
        // need to make some UI to choose difficulty
        // user pick their difficulty in the beginning
        // default to EasyMode
        this.model = new TetrisModel(); // create a model
        this.view = new TetrisView(model, primaryStage, new EasyMode()); //tie the model to the view
        this.model.startGame(); //begin
    }

}

