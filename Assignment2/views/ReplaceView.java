package views;

import iterator.Iterator;
import iterator.PieceRepository;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.TetrisPiece;
import model.TetrisPoint;

import java.util.Arrays;

/**
 * Replace View
 *
 */
public class ReplaceView {
    TetrisView tetrisView;

    private Label feedbackLabel = new Label("");

    private int remainChance;

    private Canvas canvas;

    private GraphicsContext gc;

    private Iterator iterator;

    private Button next;
    private Button replace;

    private TetrisPiece newPiece;

    private TetrisPiece original;

    /**
     * Constructor
     *
     * @param tetrisView master view
     */
    public ReplaceView(TetrisView tetrisView) {
        remainChance = tetrisView.getChance();
        original = tetrisView.model.getNextPiece();
        newPiece = new TetrisPiece(TetrisPiece.STICK_STR);
        iterator = new PieceRepository().getIterator();

        canvas = new Canvas(82, 82);
        canvas.setId("Canvas");
        gc = canvas.getGraphicsContext2D();

        this.tetrisView = tetrisView;
        tetrisView.paused = true;
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(tetrisView.stage);
        VBox dialogVbox = new VBox(20);
        dialogVbox.setPadding(new Insets(20, 20, 20, 20));
        dialogVbox.setStyle("-fx-background-color: #121212;");

        feedbackLabel.setId("Feedback");
        feedbackLabel.setFont(new Font(16));
        feedbackLabel.setStyle("-fx-text-fill: #e8e6e3");

        next = new Button("Next type");
        next.setId("nextType");
        next.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");
        next.setPrefSize(200, 50);
        next.setFont(new Font(15));
        next.setOnAction(e -> next());

        replace = new Button("Replace");
        replace.setId("replaceNextPiece");
        replace.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");
        replace.setPrefSize(200, 50);
        replace.setFont(new Font(15));
        replace.setOnAction(e -> replace());

        gc.clearRect(0, 0, 82, 82);
        gc.setLineWidth(3);
        gc.setStroke(Color.BLACK);
        gc.setFill(Color.WHITE);
        int x = 1;
        int y = 61;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                TetrisPoint currPoint = new TetrisPoint(i, j);
                if (Arrays.asList(newPiece.getBody()).contains(currPoint)) {
                    gc.fillRect(82 - y, 61 - x, 20, 20);
                    gc.strokeRect(82 - y, 61 - x, 20, 20);
                }
                x += 20;
            }
            x = 1;
            y -= 20;
        }

        VBox replaceBox = new VBox(10, canvas, next, replace, feedbackLabel);

        dialogVbox.getChildren().add(replaceBox);
        Scene dialogScene = new Scene(dialogVbox, 400, 400);
        dialog.setScene(dialogScene);
        dialog.show();
        dialog.setOnCloseRequest(event -> {
            tetrisView.paused = false;
        });
    }

    public void next() {
        if (iterator.hasNext()) {
            newPiece = (TetrisPiece) iterator.next();
        } else {
            iterator = new PieceRepository().getIterator();
            newPiece = new TetrisPiece(TetrisPiece.STICK_STR);
        }
        gc.clearRect(0, 0, 82, 82);
        gc.setLineWidth(3);
        gc.setStroke(Color.BLACK);
        gc.setFill(Color.WHITE);
        int x = 1;
        int y = 61;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                TetrisPoint currPoint = new TetrisPoint(i, j);
                if (Arrays.asList(newPiece.getBody()).contains(currPoint)) {
                    gc.fillRect(82 - y, 61 - x, 20, 20);
                    gc.strokeRect(82 - y, 61 - x, 20, 20);
                }
                x += 20;
            }
            x = 1;
            y -= 20;
        }
    }

    public void replace() {
        TetrisPiece nextPiece = tetrisView.model.getNextPiece();
        if (nextPiece.equals(tetrisView.model.getBoom())) {
            tetrisView.model.setIsBoomFalse();
            tetrisView.model.setNextChanged(true);
        } else {
            tetrisView.model.setNextChanged(false);
        }
        if (remainChance > 0) {
            tetrisView.model.setNext(newPiece);
            tetrisView.reduceChance();
            remainChance -= 1;
            feedbackLabel.setText("The next piece has been replaced!" + "\n" + remainChance + " chances remain");
        } else {
            feedbackLabel.setText("No more chances to replace");
        }
    }
}
