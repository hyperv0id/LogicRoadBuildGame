package org.example.app;
import com.almasb.fxgl.animation.AnimationBuilder.TranslationAnimationBuilder;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;

import javafx.animation.FadeTransition;
import javafx.animation.PathTransition;
import javafx.animation.PathTransition.OrientationType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import org.example.ui.EndLevelScene;


public class CarCtrl {
    private static Path path;
    private static TranslationAnimationBuilder ghostAnim;
    private static PathTransition pt;
    static Duration timeCost = Duration.seconds(5);
    public static void startRunCar() {
        genPath();
        genGhostAnim();
        genPathT();
        
        ghostAnim.buildAndPlay();
        pt.play();
        FXGL.addUINode(pt.getNode());
        FXGL.play("car_running.wav");
    }

    private static void genPath(){

        // 关于path
        path = BottomCtrl.getPath();
        path.setStrokeWidth(2);
        path.setStroke(Color.web("#bc646c"));
//        path.setVisible(true);
        // TODO 开发阶段显示path，演示阶段删除
        FXGL.addUINode(path);
    }

    /**
     * 创建幽灵车，拥有实体但不能拐弯
     * @return
     */
    private static void genGhostAnim(){
        // ====================
        // 给幽灵实体添加动画
        Entity ghostEntity = FXGL.getGameWorld().create("GhostCar", new SpawnData());
        ghostAnim = FXGL.animationBuilder()
                .duration(Duration.seconds(5))
                .translate(ghostEntity)
                .alongPath(path);
        FXGL.getGameWorld().addEntity(ghostEntity);

        ghostAnim.setOnFinished(new Runnable(){

            @Override
            public void run() {
                System.out.println("Ghost has finished his path");
                FadeTransition ft = new FadeTransition();
                ft.setDuration(Duration.seconds(1));
                ft.setToValue(0);
                ft.setOnFinished(e->{
                    // TODO 演示阶段删除
                    System.out.println("Delete path");
                    FXGL.removeUINode(path);
                });
                ft.play();
            }
        });
    }


    private static void genPathT(){
        Image img = new Image("assets/textures/accessory/car.png");
        ImageView iv = new ImageView(img);
        pt = new PathTransition();
        pt.setDuration(timeCost);
        pt.setNode(iv);
        pt.setPath(path);
        pt.setAutoReverse(false);
        pt.setOrientation(OrientationType.ORTHOGONAL_TO_TANGENT);

        pt.setOnFinished(e-> {
            FadeTransition ft = new FadeTransition();
            ft.setDuration(Duration.seconds(1));
            ft.setNode(iv);
            ft.setToValue(0);
            ft.play();
            ft.setOnFinished(e1-> {
                FXGL.removeUINode(path);
                FXGL.removeUINode(iv);
            });
            FXGL.getSceneService().pushSubScene(new EndLevelScene());
            System.out.println("stars:" + FXGL.geti("starCount"));
        });
    }
}
