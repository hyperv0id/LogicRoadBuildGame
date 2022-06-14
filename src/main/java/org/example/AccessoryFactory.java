package org.example;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getAppHeight;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getAppWidth;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.texture.Texture;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.example.components.CloseToPlacePoints;
import org.example.components.DraggableEx;
import org.example.components.ReplaceAfterRelease;
import org.example.components.RightClickRotate;
import org.example.components.RotateCenter;


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
                .type(GameType.BasicRoadBuilder)
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
    @Spawns("Cross")
    public Entity newCross(SpawnData data){
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
                .with(new DraggableEx())
                .with(new CloseToPlacePoints())
                .with(new ReplaceAfterRelease())
                .build();
    }

    /**
     * 曲面方形组件，可拖拽旋转
     */
    @Spawns("Hyperbola")
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
                .with(new DraggableEx())
                .with(new CloseToPlacePoints())
                .with(new ReplaceAfterRelease())
                .build();
    }

    /**
     * 香蕉形组件，可拖拽旋转
     */
    @Spawns("Arc")
    public Entity newCurvedOrbit(SpawnData data) {
        // 最恶心的玩意儿
        Texture arcTexture = FXGL.texture("accessory/arc.png");
        arcTexture.setScaleX(0.56);
        arcTexture.setScaleY(0.56);
        arcTexture.setRotate(2);
        return FXGL.entityBuilder()
                .type(GameType.Arc)
                .viewWithBBox(arcTexture)
                .with(new DraggableEx())
                .with(new CloseToPlacePoints())
                .with(new RightClickRotate(45))
                .with(new ReplaceAfterRelease())
                .build();
    }


    /**
     * 开始方块
     */
    @Spawns("StartingPoint")
    public Entity newStartingPoint(SpawnData data) {
        Texture StartingPoint = FXGL.texture("accessory/starting_point.png",
                bottomSize/4, bottomSize/4);
        return   FXGL.entityBuilder()
                .type(GameType.StartingPoint)
                .view(StartingPoint)
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
    @Spawns("EndingPoint")
    public Entity newEndingPoint(SpawnData data) {
        Texture EndingPoint = FXGL.texture("accessory/ending_point.png",
                bottomSize/4, bottomSize/4);
        return   FXGL.entityBuilder()
                .type(GameType.EndingPoint)
                .viewWithBBox(EndingPoint)
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

    @Spawns("ReplaceButton")
    public Entity Replace(SpawnData data){
        Texture ReplacePic = FXGL.texture("ui/RESET.png");
        return FXGL.entityBuilder()
                .type(GameType.ReplaceButton)
                .view(ReplacePic)
                .build();
    }

    @Spawns("Obstacle")
    public Entity newObstacle(SpawnData data) {
        Rectangle obstacle = new Rectangle(90,90);
        Rectangle obstacle2 = new Rectangle(90,90);
        obstacle2.setRotate(45);
        obstacle.setFill(Color.BLACK);
        return FXGL.entityBuilder()
                .type(GameType.Obstacle)
                .collidable()
                .view(obstacle)
                .view(obstacle2)
                .bbox(BoundingShape.box(83,83))
                .build();
    }

    @Spawns("Star")
    public Entity newStar (SpawnData data){
        Texture Star= FXGL.texture(
                "accessory/Star.png",
                50,
                50);
        return   FXGL.entityBuilder()
                .type(GameType.Star)
                .viewWithBBox(Star)
                .collidable()
                //.bbox(BoundingShape.box(30,30))//目前应该没什么用
                .build();
    }


    @Spawns("GhostCar")
    public Entity newGhostCar(SpawnData data){
        Texture car = FXGL.texture("accessory/car.png");
        Entity e =  FXGL.entityBuilder()
                .type(GameType.Car)
                .viewWithBBox(car)
                .collidable()
                .with(new RotateCenter(RotateCenter.CENTER))
                .build();
        e.setOpacity(0);
        return e;
    }

//     //星星探测器，用来检测星星的位置，只有大小没有图像
//     @Spawns("StarDetector")
//     public Entity newStarDetector (SpawnData data) {
//         return FXGL.entityBuilder()
//         .type(GameType.StarDetector)
//         .bbox(BoundingShape.box(60,60))
//         .collidable()
//         .build();
//     }
}
