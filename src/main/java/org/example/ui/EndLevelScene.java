package org.example.ui;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.scene.SubScene;
import com.almasb.fxgl.texture.Texture;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.awt.*;

public class EndLevelScene extends SubScene {
    private final TranslateTransition tt1;
    private final TranslateTransition tt2;
    private final TranslateTransition tt3;
    private final ParallelTransition pt;

    public EndLevelScene() {

        ImageView endImage = new ImageView(FXGL.image("ui/images/renew.png", 503, 496));
        Text starNum = FXGL.getUIFactoryService()
                .newText(FXGL.getip("starCount").asString("%d"));
//        starNum.setFont(Font.font(35));
        Text pointsGot = FXGL.getUIFactoryService()
                .newText(FXGL.getip("points").asString("%d"));
//        pointsGot.setFont(Font.font(35));
        FXGL.set("points",(int)(((16 - FXGL.geti("stepCount")) / 16.0) * 100));
        //System.out.println(FXGL.geti("points"));//测试用，可删
        ImageButton endBtn = new ImageButton("images/quit",170,161,() -> {
            FXGL.getGameController().exit();
        });
        ImageButton restartBtn = new ImageButton("images/restart",192 ,141, () -> {
            FXGL.getGameController().gotoMainMenu();
        });

        VBox pointAndStars = new VBox(pointsGot, starNum);

        HBox btnBox = new HBox(endBtn, restartBtn);
//        VBox finalBox = new VBox(btnBox, pointAndStars);

        endImage.setLayoutX(250);
        endImage.setLayoutY(FXGL.getAppHeight());
        btnBox.setLayoutX(300);
        btnBox.setLayoutY(FXGL.getAppHeight() + 330);
        btnBox.setSpacing(50);
        pointAndStars.setLayoutX(450);
        pointAndStars.setLayoutY(FXGL.getAppHeight() + 180);
        pointAndStars.setSpacing(70);
//        finalBox.setLayoutX(300);
//        finalBox.setLayoutY(FXGL.getAppHeight() + 330);

        tt1 = new TranslateTransition(Duration.seconds(3),endImage);
        tt1.setFromY(800);
        tt1.setToY(-600);
        tt2 = new TranslateTransition(Duration.seconds(3),btnBox);
        tt2.setFromY(800);
        tt2.setToY(-600);
        tt3 = new TranslateTransition(Duration.seconds(3),pointAndStars);
        tt3.setFromY(800);
        tt3.setToY(-600);
        pt = new ParallelTransition(tt1, tt2, tt3);
        getContentRoot().getChildren().addAll(endImage, pointAndStars, btnBox);
    }

    @Override
    public void onCreate() {
        pt.play();
    }
}
