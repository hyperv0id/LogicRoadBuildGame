package org.example;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.texture.Texture;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.example.components.*;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getAppHeight;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getAppWidth;


public class AccessoryFactory implements EntityFactory {

    double bottomSize;
    public AccessoryFactory(){
        bottomSize =0.9* Math.min(getAppWidth(),getAppHeight());
    }

    @Spawns("BasicBottom")
    public Entity newBasicBottom(SpawnData data) {
        // 左上∠的方块
        Texture textureLU = FXGL.texture("accessory/Bottom.png",
                bottomSize/2,
                bottomSize/2);
        double height=textureLU.getHeight(),width = textureLU.getWidth();
        Texture textureLD = textureLU.copy();
        // 上下翻转
        textureLD.setScaleY(-1);
        // 上下平移
        textureLD.setTranslateY(height);


        Texture textureRU = textureLU.copy();
        textureRU.setScaleX(-1);
        textureRU.setTranslateX(width);

        Texture textureRD = textureLU.copy();
        textureRD.setScaleY(-1);
        textureRD.setScaleX(-1);
        textureRD.setTranslateX(width);
        textureRD.setTranslateY(height);

        return FXGL.entityBuilder()
                .type(GameType.BasicRoadBuilder_4)
                .view(textureLU)
                .view(textureLD)
                .view(textureRU)
                .view(textureRD)
                .bbox(BoundingShape.box(bottomSize,bottomSize))
                .build();
    }

    /**
     * 十字形组件，可拖动旋转
     */
    @Spawns("CrossAccessory")
    public Entity newCrossAccessory(SpawnData data){
        Texture crossAccessories = FXGL.texture(
                "accessory/cross2.png",
                bottomSize/4,
                bottomSize/4);
        crossAccessories.setScaleX(1/Math.sqrt(2));
        crossAccessories.setScaleY(1/Math.sqrt(2));

        return FXGL.entityBuilder()
                .type(GameType.Cross)
                .viewWithBBox(crossAccessories)
//                .bbox(BoundingShape.box(75,75))
                .with(new RotateCenter(RotateCenter.CENTER))
                .with(new RightClickRotate(45))
                .with(new MyDraggable())
                .with(new MyCloseToPlacePoints())
                .with(new ReplaceAfterRelease())
                .build();
    }

    /**
     * 曲面方形组件，可拖拽旋转
     */
    @Spawns("CurveSquareAccessory")
    public Entity newCurveSquare(SpawnData data) {
        Texture curveSquare = FXGL.texture("accessory/hyperbola.png",
                bottomSize/4, bottomSize/4);
        curveSquare.setScaleX(1/Math.sqrt(2));
        curveSquare.setScaleY(1/Math.sqrt(2));
        return FXGL.entityBuilder()
                .type(GameType.Hyperbola)
                .viewWithBBox(curveSquare)
                .with(new RotateCenter(RotateCenter.CENTER))
                .with(new RightClickRotate(45))
                .with(new MyDraggable())
                .with(new MyCloseToPlacePoints())
                .with(new ReplaceAfterRelease())
                .build();
    }

    /**
     * 香蕉形组件，可拖拽旋转
     */
    @Spawns("CurvedOrbitAccessory")
    public Entity newCurvedOrbit(SpawnData data) {
        // 最恶心的玩意儿
        Texture arcTexture = FXGL.texture("accessory/arc.png");
        arcTexture.setScaleX(0.56);
        arcTexture.setScaleY(0.56);
        arcTexture.setRotate(2);
        return FXGL.entityBuilder()
                .type(GameType.Arc)
                .viewWithBBox(arcTexture)
                .with(new MyDraggable())
                .with(new MyCloseToPlacePoints())
                .with(new RightClickRotate(45))
                .with(new ReplaceAfterRelease())
                .build();
    }


    /**
     * 开始方块
     */
    @Spawns("StartAccessory")
    public Entity newStartAccessory(SpawnData data) {
        Texture startAccessory = FXGL.texture("accessory/starting_point.png",
                bottomSize/4, bottomSize/4);
        return   FXGL.entityBuilder()
                .type(GameType.Starting_Point)
                .view(startAccessory)
                .bbox(BoundingShape.box(bottomSize/4, bottomSize/4))
                .with(new RotateCenter(RotateCenter.CENTER))
                .build();
    }

    /**
     * 装载方块的盒子
     */
    @Spawns("box")
    public Entity newBox (SpawnData data) {
        Texture box = FXGL.texture("accessory/box.png",200, 600);
        return FXGL.entityBuilder()
                .at(740,20)
                .type(GameType.BOX)
                .viewWithBBox(box)
                .build();
    }

    /**
     * 结束方块
     */
    @Spawns("EndAccessory")
    public Entity newEndAccessory(SpawnData data) {
        Texture endAccessory = FXGL.texture("accessory/ending_point.png",
                bottomSize/4, bottomSize/4);
        return   FXGL.entityBuilder()
                .type(GameType.EndingPoint)
                .viewWithBBox(endAccessory)
                .build();
    }


    @Spawns("BallInCar")
    public Entity newBallInCar(SpawnData data) {
        Texture ballInCar = FXGL.texture("accessory/small_ball.png", 10,10);
        return FXGL.entityBuilder()
                .type(GameType.Small_Ball)
                .viewWithBBox(ballInCar)
                .with(new RotateCenter(RotateCenter.CENTER))
                .build();
    }

    @Spawns("Car")
    public Entity newCar(SpawnData data) {
        Texture car = FXGL.texture("accessory/car.png", 35, 56);
        return FXGL.entityBuilder()
                .type(GameType.Car)
                .viewWithBBox(car)
                .collidable()
                .with(new RotateCenter(RotateCenter.CENTER))
                .build();
    }


    @Spawns("StartRunCar")
    public Entity newStart(SpawnData data){
        Texture startPic = FXGL.texture("ui/start.png");

        return FXGL.entityBuilder()
                .type(GameType.StartRunCarButton)
                .view(startPic)
                .build();
    }

    @Spawns("Obstacle")
    public Entity newObstacle(SpawnData data) {
        Rectangle obstacle = new Rectangle(90,90);
        obstacle.setFill(Color.WHITE);

        return FXGL.entityBuilder()
                .type(GameType.Obstacle)
                .collidable()
                .viewWithBBox(obstacle)
                .build();
    }

    @Spawns("StarAccessory")
    public Entity newStarAccessory (SpawnData data){
        Texture StarAccessory= FXGL.texture(
                "accessory/Star.png",
                50,
                50);
        return   FXGL.entityBuilder()
                .type(GameType.Star)
                .viewWithBBox(StarAccessory)
                .collidable()
                //.bbox(BoundingShape.box(30,30))//目前应该没什么用
                .build();
    }


    @Spawns("GhostCar")
    public Entity newGhostCar(SpawnData data){
        Texture car = FXGL.texture("accessory/car.png", 35, 56);
        Entity e =  FXGL.entityBuilder()
                .type(GameType.Car)
                .viewWithBBox(car)
                .collidable()
                .with(new RotateCenter(RotateCenter.CENTER))
                .build();
        e.setOpacity(0);
        return e;
    }
}
