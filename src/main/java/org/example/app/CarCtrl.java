package org.example.app;

import com.almasb.fxgl.animation.AnimatedPath;
import com.almasb.fxgl.core.asset.AssetType;
import com.almasb.fxgl.dsl.FXGL;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.Texture;
import javafx.animation.FadeTransition;
import javafx.animation.PathTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.*;
import javafx.util.Duration;
import org.example.GameType;
import org.example.components.RotateCenter;


public class CarCtrl {

    public static void startRunCar() {
        Path path = BottomCtrl.getPath();
        Image image = new Image("assets/textures/accessory/car.png");
        ImageView iv = new ImageView(image);
        FXGL.addUINode(iv);

        Texture t = FXGL.texture( "accessory/car.png");

//        Entity myCar = FXGL.getGameWorld().create("Car",new SpawnData());
//        myCar.getViewComponent().addChild(t);
//
//        FXGL.getGameWorld().addEntity(myCar);

//        AnimatedPath
        PathTransition pl = new PathTransition();
        pl.setNode(iv);
        pl.setDuration(Duration.seconds(3));
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
            System.out.println(iv.getX());
            System.out.println(iv.getY());
        });
    }





}
