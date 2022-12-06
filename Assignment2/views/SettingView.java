package views;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import command.*;

import java.util.*;

/**
 * Setting View
 *
 */
public class SettingView {

    TetrisView tetrisView;

    CommandManager commandManager;

    private Label settingErrorLabel = new Label("");

    private final ChoiceBox<Object> boardColorSelect; //the list of color options for board

    private final ChoiceBox<Object> blockColorSelect; //the list of color options for block

    private Button increaseColorContrastButton = new Button("Increase color contrast");
    private Button decreaseColorContrastButton = new Button("Decrease color contrast");

    private Button changeBoardColorButton = new Button("Change board's color");

    private Button changeBlockColorButton = new Button("Change block's color");
    /**
     * Constructor
     *
     * @param tetrisView master view
     */
    public SettingView(TetrisView tetrisView) {
        this.commandManager = new CommandManager();
        this.tetrisView = tetrisView;
        tetrisView.paused = true;
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(tetrisView.stage);
        VBox dialogVbox = new VBox(20);
        dialogVbox.setPadding(new Insets(20, 20, 20, 20));
        dialogVbox.setStyle("-fx-background-color: #121212;");

        settingErrorLabel.setId("settingErrorLabel");
        settingErrorLabel.setStyle("-fx-text-fill: #e8e6e3;");
        settingErrorLabel.setFont(new Font(16));

        //set up four buttons
        changeBoardColorButton = new Button("Change board's color");
        changeBoardColorButton.setId("increaseColorContrast"); // DO NOT MODIFY ID
        changeBoardColorButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");
        changeBoardColorButton.setPrefSize(200, 50);
        changeBoardColorButton.setFont(new Font(15));
        changeBoardColorButton.setOnAction(e -> changeBoardColor());

        changeBlockColorButton = new Button("Change block's color");
        changeBlockColorButton.setId("increaseColorContrast"); // DO NOT MODIFY ID
        changeBlockColorButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");
        changeBlockColorButton.setPrefSize(200, 50);
        changeBlockColorButton.setFont(new Font(15));
        changeBlockColorButton.setOnAction(e -> changeBlockColor());

        increaseColorContrastButton = new Button("Increase color contrast");
        increaseColorContrastButton.setId("increaseColorContrast"); // DO NOT MODIFY ID
        increaseColorContrastButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");
        increaseColorContrastButton.setPrefSize(200, 50);
        increaseColorContrastButton.setFont(new Font(15));
        increaseColorContrastButton.setOnAction(e -> increaseColorContrast());

        decreaseColorContrastButton = new Button("Decrease color contrast");
        decreaseColorContrastButton.setId("decreaseColorContrast"); // DO NOT MODIFY ID
        decreaseColorContrastButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");
        decreaseColorContrastButton.setPrefSize(200, 50);
        decreaseColorContrastButton.setFont(new Font(15));
        decreaseColorContrastButton.setOnAction(e -> decreaseColorContrast());

        //the list of colors
        List<String> colors= Arrays.asList("RED", "WHITE", "GREEN", "BLUE", "PURPLE", "ORANGE",
                "YELLOW", "BROWN", "CYAN", "PINK", "BLACK");
        Collections.sort(colors);

        //create the UI element to hold the colors
        this.boardColorSelect = new ChoiceBox<>(FXCollections
                .observableArrayList(colors));
        this.blockColorSelect = new ChoiceBox<>(FXCollections
                .observableArrayList(colors));

        VBox settingBox = new VBox(15, boardColorSelect, changeBoardColorButton, blockColorSelect, changeBlockColorButton,
                increaseColorContrastButton, decreaseColorContrastButton, settingErrorLabel);

        dialogVbox.getChildren().add(settingBox);
        Scene dialogScene = new Scene(dialogVbox, 400, 400);
        dialog.setScene(dialogScene);
        dialog.show();
        dialog.setOnCloseRequest(event -> {
            tetrisView.paused = false;
        });

    }

    public void increaseColorContrast() {
        IncreaseColorContrast increaseColorContrast = new IncreaseColorContrast(this.tetrisView);
        settingErrorLabel.setText(this.commandManager.executeCommand(increaseColorContrast));
    }

    public void decreaseColorContrast() {
        DecreaseColorContrast decreaseColorContrast = new DecreaseColorContrast(this.tetrisView);
        settingErrorLabel.setText(this.commandManager.executeCommand(decreaseColorContrast));
    }

    public void changeBoardColor() {
        if (boardColorSelect.getValue() == null) {
            settingErrorLabel.setText("Please select a color!");
        } else {
            ChangeBoardColor changeBoardColor = new ChangeBoardColor(this.tetrisView, boardColorSelect.getValue().toString());
            settingErrorLabel.setText(this.commandManager.executeCommand(changeBoardColor));
        }
    }

    public void changeBlockColor() {
        if (blockColorSelect.getValue() == null) {
            settingErrorLabel.setText("Please select a color!");
        } else {
            ChangeBlockColor changeBlockColor = new ChangeBlockColor(this.tetrisView, blockColorSelect.getValue().toString());
            settingErrorLabel.setText(this.commandManager.executeCommand(changeBlockColor));
        }
    }
}
