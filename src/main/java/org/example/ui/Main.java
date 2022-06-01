package org.example.ui;
import javafx.application.Application;
import javafx.stage.Stage;
// The entrance
public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
       Sta st=new Sta();
       st.start(stage);  //Open the opening page
    }
    public static void main(String args[]) {
    Application.launch(Main.class, args);
    }
}