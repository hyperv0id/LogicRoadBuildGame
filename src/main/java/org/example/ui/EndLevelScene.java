package org.example.ui;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.scene.SubScene;
import com.almasb.fxgl.texture.Texture;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.awt.*;

public class EndLevelScene extends SubScene {
    private final TranslateTransition tt;

    public EndLevelScene() {
        Texture endTexture = FXGL.texture("ui/images/resu.png");
        ImageButton endBtn = new ImageButton("assets/textures/ui/images/quit.png",170,161,() -> {});
        endTexture.setLayoutX(200);
        endTexture.setLayoutY(FXGL.getAppHeight());
        tt = new TranslateTransition(Duration.seconds(3),endTexture);
        tt.setFromY(800);
        tt.setToY(-600);
        System.out.println("Arrive");
        getContentRoot().getChildren().addAll(endTexture);
    }

    @Override
    public void onCreate() {
        tt.play();
    }
}
