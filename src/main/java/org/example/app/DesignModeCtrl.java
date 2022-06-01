package org.example.app;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;

import org.example.GameType;
import org.example.components.MousePressLosePoint;
import org.example.components.MyCloseToPlacePoints;
import org.example.components.MyDraggable;
import org.example.components.RightClickRotate;
import org.example.components.RotateCenter;
import org.example.info.LevelInfo;

import javafx.geometry.Point2D;

public class DesignModeCtrl extends GameLevelCtrl{


    public DesignModeCtrl(String levelInfo) {
        info = FXGL.getAssetLoader().loadJSON(levelInfo, LevelInfo.class).get();

        FXGL.set("crossNum",info.crossNum());
        FXGL.set("hyperbolaNum",info.curveSquareNum());
        FXGL.set("arcNum",info.curveOrbitNum());
        FXGL.set("starNum",info.starNum());
        FXGL.set("obstacleNum",info.obstacleNum());
    }

    /**
     *
     */
    public void init() {
        BottomCtrl.init(info);

        initStart();
        initEnd();
        initAccessories();
        // 初始化按钮
        initButton();
        initObstacle();
        initStar();
    }

    public void initObstacle(){
        super.initObstacle();
        for (Entity entity : obstacles) {
            entity.addComponent(new MyDraggable());
            entity.addComponent(new MyCloseToPlacePoints());
            entity.addComponent(new MousePressLosePoint());
        }
    }

    // 开始方块
    public void initStart() {
        int x = info.startX(),y = info.startY();
        // 创建起点
        Entity startE = getGameWorld().create("StartAccessory",new SpawnData());
        startE.addComponent(new MyDraggable());
        startE.addComponent(new RotateCenter(RotateCenter.CENTER));
        startE.addComponent(new RightClickRotate(45));
        startE.addComponent(new MyCloseToPlacePoints());
        startE.addComponent(new MousePressLosePoint());
        // 设置起始点位置，方向
        Point2D startPlace = BottomCtrl.getPlacePoint(x,y);
        startE.setPosition( startPlace.subtract(startE.getWidth()/2,startE.getHeight()/2) );
        startE.setRotation(info.startAng());
        // 设置行列参数，便于调用
        startE.setProperty("row", x);
        startE.setProperty("col", y);

        // 不能传形参，不然无法覆盖
        BottomCtrl.setType(x, y,(GameType) startE.getType());
        BottomCtrl.setRotation(x, y,startE.getRotation());
        FXGL.getGameWorld().addEntity(startE);
    }

    /**
     * 初始化结束方块
     */
    protected void initEnd() {
        int x = info.endX(), y = info.endY();
        // 创建终点
        Entity endE = getGameWorld().create("EndAccessory",new SpawnData());
        endE.addComponent(new MyDraggable());
        endE.addComponent(new RotateCenter(RotateCenter.CENTER));
        endE.addComponent(new RightClickRotate(45));
        endE.addComponent(new MyCloseToPlacePoints());
        endE.addComponent(new MousePressLosePoint());
        
        // 设置行列参数，便于调用
        endE.setProperty("row", x);
        endE.setProperty("col", y);
        
        // 设置终点位置
        Point2D endPlace = BottomCtrl.getPlacePoint(x, y);
        endE.setPosition( endPlace.subtract(endE.getWidth()/2,endE.getHeight()/2) );
        BottomCtrl.setType( x, y ,(GameType) endE.getType());
        FXGL.getGameWorld().addEntity(endE);
    }

}
