package org.example.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import java.io.IOException;
// A controller class for the "mode-choose" page
public class Modechoose {
    private String text;
    @FXML
    private Button easy;

    @FXML
    private Button hard;

    @FXML
    private Button adv;

    @FXML
    private Button Nov;
    @FXML
    public TextField usern;
    @FXML
    private Button clo;
    @FXML
    private ImageView tipo;
    @FXML
    private ImageView tipo2;

    @FXML
    private ImageView tipo3;

    @FXML
    private ImageView tipo4;


    @FXML
    void exit(ActionEvent event) {
        Stage stage = (Stage) clo.getScene().getWindow();       //click this button then close the game
        stage.close();
    }

    @FXML
    void l1(ActionEvent event) throws Exception {  // The event would happen when "Easy" button is clicked
        Stage stage = (Stage) clo.getScene().getWindow();
        stage.close();
        Stage stage3 = new Stage();
        Sta st3 = new Sta();
        st3.leve1(stage3);         //jump to the "easy" page

        // to give the text content(username) to next page
        FXMLLoader loader = new FXMLLoader(getClass().getResource("easy.fxml"));
        try
        {
            AnchorPane login = (AnchorPane) loader.load();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        Easy control = (Easy) loader.getController();
        control.model.setText(usern.getText());

    }

    @FXML
    void l2(ActionEvent event) throws Exception {  // The event would happen when "Novice" button is clicked
        Stage stage = (Stage) clo.getScene().getWindow();
        stage.close();
        Stage stage4 = new Stage();
        Sta st4 = new Sta();
        st4.leve2(stage4);    //jump to the "novice" page

        // to give the text content(username) to next page
        FXMLLoader loader = new FXMLLoader(getClass().getResource("novice.fxml"));
        try
        {
            AnchorPane login = (AnchorPane) loader.load();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        Novice control = (Novice) loader.getController();
        control.model.setText(usern.getText());

    }

    @FXML
    void l3(ActionEvent event) throws Exception {    // The event would happen when "Advanced" button is clicked
        Stage stage = (Stage) clo.getScene().getWindow();
        stage.close();
        Stage stage5 = new Stage();
        Sta st5 = new Sta();
        st5.leve3(stage5);    //jump to the "advanced" page

        // to give the text content(username) to next page
        FXMLLoader loader = new FXMLLoader(getClass().getResource("advan.fxml"));
        try
        {
            AnchorPane login = (AnchorPane) loader.load();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        Advan control = (Advan) loader.getController();
        control.model.setText(usern.getText());

    }

    @FXML
    void l4(ActionEvent event) throws Exception {    // The event would happen when "Hard" button is clicked
        Stage stage = (Stage) clo.getScene().getWindow();
        stage.close();
        Stage stage6 = new Stage();
        Sta st6 = new Sta();
        st6.leve4(stage6);    //jump to the "hard" page
        // to give the text content(username) to next page
        FXMLLoader loader = new FXMLLoader(getClass().getResource("hard.fxml"));
        try
        {
            AnchorPane login = (AnchorPane) loader.load();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        Hard control = (Hard) loader.getController();
        control.model.setText(usern.getText());
    }

    @FXML
    void tip1(MouseEvent event) {
        tipo.setVisible(true);
    }  //when mouse put on the easy button, the corresponding tip would be visible; same for another three tips

    @FXML
    void tip1c(MouseEvent event) {
        tipo.setVisible(false);
    }//when mouse leave the easy button, the corresponding tip would be invisible; same for another three tips

    @FXML
    void tip2(MouseEvent event) {
        tipo2.setVisible(true);
    }

    @FXML
    void tip2c(MouseEvent event) {
        tipo2.setVisible(false);
    }

    @FXML
    void tip3(MouseEvent event) {
        tipo3.setVisible(true);
    }

    @FXML
    void tip3c(MouseEvent event) {
        tipo3.setVisible(false);
    }

    @FXML
    void tip4(MouseEvent event) {
        tipo4.setVisible(true);
    }

    @FXML
    void tip4c(MouseEvent event) {
        tipo4.setVisible(false);
    }

    @FXML
    void enterpre(KeyEvent event) {      //when "enter" key is pressed, the content in text field is saved
        if (event.getCode() == KeyCode.ENTER) {
            text = usern.getText();
            usern.setEditable(false);
        }
    }

    @FXML
    void clickit(MouseEvent event) {
      usern.setEditable(true);
    }   //when the text field is clicked, it is editable
    @FXML
    void newusen(ActionEvent event) {
        usern.setEditable(true);
    }   //set the text field editable initially

}
