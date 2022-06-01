package org.example.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
//the controller for fail page
public class Fail implements Initializable {

    @FXML
    private Label la1;
    @FXML
    private Label score;       //这俩没用的label用来放成绩和获得星星数量
    @FXML
    private Label star;
    public  static AppModel model = new AppModel();
    @FXML
    private Button clo;

    @FXML
    private Button repl;

    @FXML
    void exit(ActionEvent event) {
        Stage stage = (Stage) clo.getScene().getWindow();
        stage.close();
    }

    @FXML
    void replay(ActionEvent event) throws Exception {       //the event would happen when "restart" button is clicked
        Stage stage = (Stage) repl.getScene().getWindow();
        stage.close();
        Stage stage3 = new Stage();
        Sta st3 = new Sta();
        st3.start(stage3);   //open the Open page
    }

    //get the username from previous page
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
