package org.example.ui;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import javafx.animation.PathTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.example.RoadBuilderApp;

import java.awt.*;

public class MainMenu extends FXGLMenu {
    PathTransition pt=new PathTransition();
    public MainMenu() {
        super(MenuType.MAIN_MENU);

        ImageView bg = new ImageView(FXGL.image("ui/images/Choose one game mode.png",960,640));
        ImageButton btn1 = new ImageButton("images/x1(1)",72, 67, () -> {});
        ImageButton btn2 = new ImageButton("images/x2(1)",69, 76, () -> getController().startNewGame());
        ImageButton btn3 = new ImageButton("images/x3(1)",122, 73, () -> getController().startNewGame());
        ImageButton btn4 = new ImageButton("images/x4(1)",85, 73, () -> getController().startNewGame());

        Text btn1Text = new Text("EASY");
        btn1Text.setFont(Font.font(30));
        btn1Text.setTranslateY(15);
        HBox btnBox1 = new HBox(btn1,btn1Text);
        Text btn2Text = new Text("NOVICE");
        btn2Text.setFont(Font.font(30));
        btn2Text.setTranslateY(15);
        HBox btnBox2 = new HBox(btn2,btn2Text);
        Text btn3Text = new Text("ADVANCED");
        btn3Text.setFont(Font.font(30));
        btn3Text.setTranslateY(15);
        HBox btnBox3 = new HBox(btn3,btn3Text);
        Text btn4Text = new Text("HARD");
        btn4Text.setFont(Font.font(30));
        btn4Text.setTranslateY(15);
        HBox btnBox4 = new HBox(btn4,btn4Text);

        Image tip1 = new Image("assets/textures/ui/images/tt1.png",113,84,true,true);
        ImageView tip1View = new ImageView(tip1);
        tip1View.setVisible(false);
        Image tip2 = new Image("assets/textures/ui/images/tip2.png",136,78,true,true);
        ImageView tip2View = new ImageView(tip2);
        tip2View.setVisible(false);
        Image tip3 = new Image("assets/textures/ui/images/tip3.png",120,76,true,true);
        ImageView tip3View = new ImageView(tip3);
        tip3View.setVisible(false);
        Image tip4 = new Image("assets/textures/ui/images/tip4.png",146,116,true,true);
        ImageView tip4View = new ImageView(tip4);
        tip4View.setVisible(false);

        //消息盒子，将鼠标放在盒子上就会出现对应消息，松开则消失
        HBox tipBox = new HBox(tip1View, tip2View, tip3View, tip4View);
        btnBox1.setOnMouseMoved(e -> {
            tip1View.setVisible(true);
        });
        btnBox1.setOnMouseExited(e -> {
            tip1View.setVisible(false);
        });
        btnBox2.setOnMouseMoved(e -> {
            tip2View.setVisible(true);
        });
        btnBox2.setOnMouseExited(e -> {
            tip2View.setVisible(false);
        });
        btnBox3.setOnMouseMoved(e -> {
            tip3View.setVisible(true);
        });
        btnBox3.setOnMouseExited(e -> {
            tip3View.setVisible(false);
        });
        btnBox4.setOnMouseMoved(e -> {
            tip4View.setVisible(true);
        });
        btnBox4.setOnMouseExited(e -> {
            tip4View.setVisible(false);
        });

        //选择难度
        btnBox1.setOnMouseClicked(e -> {
            RoadBuilderApp.chosenLevel = 1;
//            System.out.println(FXGL.geti("level"));
            getController().startNewGame();
        });

        HBox box = new HBox(
                btnBox1,btnBox2,btnBox3,btnBox4
        );

        box.setLayoutX(50);
        box.setLayoutY(400);
        box.setSpacing(15);
        tipBox.setLayoutX(130);
        tipBox.setLayoutY(300);
        tipBox.setSpacing(100);
        getContentRoot().getChildren().setAll(bg, box, tipBox);


    }


}
