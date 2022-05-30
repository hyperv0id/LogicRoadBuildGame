package org.example.app;

import com.almasb.fxgl.dsl.FXGL;

import javafx.animation.FadeTransition;
import javafx.animation.PathTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.*;
import javafx.util.Duration;


public class CarCtrl {

    public static void startRunCar() {
        Path path = BottomCtrl.getPath();

        // Entity myCar = getGameWorld().create("Car",new SpawnData());
        Image image = new Image("assets/textures/accessory/car.png");
        ImageView iv = new ImageView(image);
        iv.setX(2);
        iv.setY(1000);
        FXGL.addUINode(iv,500,500);

//        AnimatedPath
        PathTransition pl = new PathTransition();
        pl.setNode(iv);
        pl.setDuration(Duration.seconds(10));
        pl.setPath(path);
        pl.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pl.setAutoReverse(false);
        pl.play();
        // 结束动画
        pl.setOnFinished(e-> {
            FadeTransition ft = new FadeTransition();
            ft.setDuration(Duration.seconds(1));
            ft.setNode(iv);
            ft.setToValue(0);
            ft.play();
            ft.setOnFinished(ee-> FXGL.removeUINode(iv));
        });
    }





}
