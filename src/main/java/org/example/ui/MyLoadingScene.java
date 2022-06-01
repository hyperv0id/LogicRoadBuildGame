package org.example.ui;

import com.almasb.fxgl.app.scene.LoadingScene;
import com.almasb.fxgl.dsl.FXGL;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import javax.swing.text.Element;
import java.awt.*;

public class MyLoadingScene extends LoadingScene {
    Image trainIamge = new Image("assets/textures/ui/images/p2.png");
    ImageView iv = new ImageView(trainIamge);
    TranslateTransition tt = new TranslateTransition(Duration.seconds(5), iv);
    ImageView bg = new ImageView(FXGL.image("ui/images/cover.png",960,640));
    ImageButton start = new ImageButton("images/tra2(1)",128,195,() -> {});

    public MyLoadingScene() {
        VBox box = new VBox(
                start
        );
        box.setLayoutX(300);
        box.setLayoutY(400);
        getContentRoot().getChildren().setAll(bg, iv, box);

    }

    @Override
    public void onCreate() {
        tt.setFromX(0);
        tt.setFromY(50);
        tt.setToX(1000);
        tt.play();
    }

    @Override
    public void onDestroy() {
        tt.stop();
    }
}
