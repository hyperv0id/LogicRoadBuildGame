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
//这个和其他三个（hard，novice，advan）是中间游戏界面的page，但是感觉可以删掉，直接从modechoose跳转到他们已经做好的那个游戏page（要在Sta类里改一下l1-l4方法），这样的话会比较简单一些（只是把那个背景图片换成我的就行）
// The easy page controller
public class Easy implements Initializable {
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

    @FXML        //这个是那个goon按钮的触发事件。我做了两个结果界面，一个win，一个fail，但是我不知道游戏结果是啥，所以我只能随便挑了两个类分别绑定这两个结果。这个肯定是要么删要么改，看你们是想自动跳转还是按钮跳转
    void fail(ActionEvent event) throws Exception {
        Stage stage = (Stage) next.getScene().getWindow();
        stage.close();
        Stage stager = new Stage();
        Sta stre = new Sta();
        stre.stagefl(stager);  //打开失败的结果界面

        //把用户名传给结果界面
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fail.fxml"));
        try
        {
            AnchorPane login = (AnchorPane) loader.load();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        Fail control = (Fail) loader.getController();
        control.model.setText(la1.getText());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { // update text area during initialization
        model.textProperty().addListener((obs, oldText, newText) -> la1.setText(newText));
    }
    public static void setText(String text)
    {
        model.setText(text);

    }
}