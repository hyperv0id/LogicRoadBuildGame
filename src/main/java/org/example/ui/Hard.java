package org.example.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Hard implements Initializable {
    public  static AppModel model = new AppModel();
    @FXML
    private Button clo;
    @FXML
    private Label la1;
    @FXML
    private Button next;
    @FXML
    void exit(ActionEvent event) {
        Stage stage = (Stage) clo.getScene().getWindow();
        stage.close();
    }
    @FXML
    void result(ActionEvent event) throws Exception {
        Stage stage = (Stage) next.getScene().getWindow();
        stage.close();
        Stage stager = new Stage();
        Sta stre = new Sta();
        stre.stagere(stager);  //打开成功界面
        // 传用户名
        FXMLLoader loader = new FXMLLoader(getClass().getResource("result.fxml"));
        try
        {
            AnchorPane login = (AnchorPane) loader.load();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        Result control = (Result) loader.getController();
        control.model.setText(la1.getText());
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
