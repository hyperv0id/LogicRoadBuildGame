package org.example.ui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class Novice implements Initializable {
    public  static AppModel model = new AppModel();
    @FXML
    private Button clo;
    @FXML
    private Label la1;

    @FXML
    void exit(ActionEvent event) {
        Stage stage = (Stage) clo.getScene().getWindow();
        stage.close();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // update text area if text in model changes:
        model.textProperty().addListener((obs, oldText, newText) -> la1.setText(newText));
    }
    public static void setText(String text)
    {
        model.setText(text);
    }
}