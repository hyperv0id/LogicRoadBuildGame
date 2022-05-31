package org.example.app;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.texture.Texture;

import org.example.GameType;
import org.example.components.RotateCenter;

import javafx.scene.shape.Path;


public class CarCtrl {

    public static void startRunCar() {
        Path path = BottomCtrl.getPath();
        
        Texture carTexture = FXGL.texture("accessory/car.png");
        Entity carEntity = FXGL
            .entityBuilder()
            .type(GameType.Car)
            .viewWithBBox(carTexture)
            .collidable()
            .with(new RotateCenter(RotateCenter.CENTER))
            .build();
        BottomCtrl.setPosition(carEntity, BottomCtrl.Starting_Point);
        // carEntity.setRotation(GameLevelCtrl.info.startAng());
        // Entity carEntity = FXGL.getGameWorld().create("Car", new SpawnData());
        FXGL.getGameWorld().addEntity(carEntity);
        System.out.println(carEntity.getBoundingBoxComponent());

        var anim = FXGL.animationBuilder()
            .translate(carEntity)
            .alongPath(path);
        anim.setOnFinished(new MyRunnable(carEntity, carTexture));
        anim.buildAndPlay();
        
    //     Image image = new Image("assets/textures/accessory/car.png");
    //     ImageView iv = new ImageView(image);            
    // //    AnimatedPath
    //     PathTransition pl = new PathTransition();
    //     pl.setNode(iv);
    //     pl.setDuration(Duration.seconds(3));
    //     pl.setPath(path);
    //     pl.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
    //     pl.setAutoReverse(false);
    //     FXGL.addUINode(iv);


    //     pl.play();
    //     // 结束动画
    //     pl.setOnFinished(e-> {
    //         FadeTransition ft = new FadeTransition();
    //         ft.setDuration(Duration.seconds(1));
    //         ft.setNode(iv);
    //         ft.setToValue(0);
    //         ft.play();
    //         ft.setOnFinished(ee-> FXGL.removeUINode(iv));
    //     });
    }





}
