package org.example.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
//the controller for success page
public class Result implements Initializable {
    public  static AppModel model = new AppModel();
    @FXML
    private Label la1;
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
    void replay(ActionEvent event) throws Exception {            //the event would happen when "restart" button is clicked
        Stage stage = (Stage) repl.getScene().getWindow();
        stage.close();
        Stage stage3 = new Stage();
        Sta st3 = new Sta();
        st3.start(stage3);
    }
    //要删掉我中间的游戏界面牵扯到一个最后的结果界面中的用户名没法传的问题，因为我的所有用户名传递是通过前一个page中点击按钮的触发事件里设置，所以建议也在中间的游戏界面加一个label存用户名，然后按钮跳转再传到这儿，代码直接copy我已经写的就行
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