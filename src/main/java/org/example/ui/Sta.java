package org.example.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

//A class to build new stages

public class Sta {
    private Stage prist;
    public void start(Stage pm) throws Exception {
        prist=pm;
        Parent root = FXMLLoader.load(getClass().getResource("open.fxml"));  //load to the corresponding fxml
        Scene scene = new Scene(root);    //new a scene which get the fxml above
        prist.setTitle("Opening");
        prist.setScene(scene);
        prist.show();
    }
    public void secon(Stage pm) throws Exception {
        prist=pm;
        Parent root = FXMLLoader.load(getClass().getResource("modechoose.fxml"));
        Scene scene = new Scene(root);
        prist.setScene(scene);
        prist.setTitle("Mode choose");
        prist.show();

    }
    public void leve1(Stage pm) throws Exception {
        prist=pm;
        Parent root = FXMLLoader.load(getClass().getResource("easy.fxml"));
        Scene scene = new Scene(root);
        prist.setScene(scene);
        prist.setTitle("Easy");
        prist.show();
    }
    public void leve2(Stage pm) throws Exception {
        prist=pm;
        Parent root = FXMLLoader.load(getClass().getResource("novice.fxml"));
        Scene scene = new Scene(root);
        prist.setScene(scene);
        prist.setTitle("Novice");
        prist.show();
    }
    public void leve3(Stage pm) throws Exception {
        prist=pm;
        Parent root = FXMLLoader.load(getClass().getResource("advan.fxml"));
        Scene scene = new Scene(root);
        prist.setScene(scene);
        prist.setTitle("Advanced");
        prist.show();
    }
    public void leve4(Stage pm) throws Exception {
        prist=pm;
        Parent root = FXMLLoader.load(getClass().getResource("hard.fxml"));
        Scene scene = new Scene(root);
        prist.setScene(scene);
        prist.setTitle("Hard");
        prist.show();

    }
    public void stagere(Stage pm) throws Exception {
        prist=pm;
        Parent root = FXMLLoader.load(getClass().getResource("result.fxml"));
        Scene scene = new Scene(root);
        prist.setScene(scene);
        prist.setTitle("Success");
        prist.show();
    }
    public void stagefl(Stage pm) throws Exception {
        prist=pm;
        Parent root = FXMLLoader.load(getClass().getResource("fail.fxml"));
        Scene scene = new Scene(root);
        prist.setScene(scene);
        prist.setTitle("Fail");
        prist.show();
    }

    public void close(){
        prist.close();
    }
}
