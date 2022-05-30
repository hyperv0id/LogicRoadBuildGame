package org.example.app;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.time.LocalTimer;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import org.example.GameType;
import org.example.components.MousePressLosePoint;
import org.example.info.LevelInfo;

import java.util.ArrayList;
import java.util.Random;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;

public class GameLevelCtrl {
    // 所有积木，包括开始结束，方圆弯道
    private static ArrayList<Entity> accessories = new ArrayList<>();
    private static ArrayList<Entity> obstacles = new ArrayList<>();
    public static LevelInfo getInfo() {
        return info;
    }

    private static LevelInfo info;

    public GameLevelCtrl(LevelInfo info) {
        GameLevelCtrl.info = info;
    }
    public GameLevelCtrl(String levelInfo) {
        GameLevelCtrl.info = FXGL.getAssetLoader().loadJSON(levelInfo,LevelInfo.class).get();
        FXGL.set("crossNum",info.crossNum());
        FXGL.set("hyperbolaNum",info.curveSquareNum());
        FXGL.set("arcNum",info.curveOrbitNum());
        FXGL.set("starNum",info.starNum());
        FXGL.set("obstacleNum",info.obstacleNum());
    }

    public void init(){
        BottomCtrl.init(info.bottomRows(), info.bottomCols());
        initStart();
        initEnd();
        initAccessories();
        // 初始化按钮
        initButton();
        initObstacle();
        initStar();
    }

    // 基本可放置方块
    public void initAccessories() {
        // double w=FXGL.getAppWidth();
        double h=FXGL.getAppHeight();
        // 创建多个十字形组件
        for (int i = 0; i < info.crossNum(); i++) {
            Entity cross = getGameWorld().create("CrossAccessory",new SpawnData());
            cross.setPosition(740,h/4);
            accessories.add(cross);
        }
        // 创建多个曲面方形组件
        for (int i = 0; i < info.curveSquareNum(); i++) {
            Entity hyperbola = getGameWorld().create("CurveSquareAccessory",new SpawnData());
            hyperbola.setPosition(740,h/2);
            accessories.add(hyperbola);
        }
        //创建多个香蕉形组件
        for (int i = 0; i < info.curveOrbitNum(); i++) {
            Entity arc = getGameWorld().create("CurvedOrbitAccessory",new SpawnData());
            arc.setPosition(740,2*h/3);
            accessories.add(arc);
        }

        for (Entity e : accessories) {
            e.setProperty("originPlace", e.getPosition());
            e.setProperty("lastPos","out");
            e.addComponent(new MousePressLosePoint());
            FXGL.getGameWorld().addEntity(e);
        }
    }

    //随机在棋盘内产生一个障碍物
    int[] obstaclePos = new int[2];
    private void initObstacle() {
        Random rand = new Random();
        // int j = 0;
        int x = rand.nextInt(4);
        int y = rand.nextInt(4);
        for (int i = 0; i < info.obstacleNum(); i++) {
            Entity obstacle = getGameWorld().create("Obstacle", new SpawnData());
            while (true) {
                if (!((x == 0 && y == 0)
                        ||(x == 3 && y == 3)
                        ||(x == 1 && y == 1))) {
                            BottomCtrl.setPosition(obstacle, x, y);
                            obstacle.setProperty("row", x);
                            obstacle.setProperty("col", y);
                            BottomCtrl.setType(x, y, (GameType)obstacle.getType());
                            FXGL.getGameWorld().addEntity(obstacle);
                            obstacles.add(obstacle);
                        break;
                }
            }
        }
        //记录生成障碍物所在的位置，防止与星星重合
        obstaclePos[0] = x;
        obstaclePos[1] = y;
        System.out.println("Obstacle position:" + x + "," + y);
    }

    //随机在棋盘内产生星星
    static int[][] starPos = new int[2][2];//记录星星的位置
    private void initStar() {
        Random rand = new Random();
        // int j = 0;
        for (int i = 0; i < info.starNum(); i++) {
            Entity star = getGameWorld().create("StarAccessory", new SpawnData());

            while (true) {
                int x = rand.nextInt(4);
                int y = rand.nextInt(4);
                if ((x == 0 && y == 0)
                        ||(x == 3 && y == 3)
                        ||(BottomCtrl.getType(x, y)==GameType.Obstacle)
                        ||(x == starPos[0][0] && x == starPos[0][1]))
                        continue;
                BottomCtrl.setPosition(star,x, y);
                BottomCtrl.setStar(x,y, true);
                FXGL.getGameWorld().addEntity(star);
                starPos[i][0] = x;
                starPos[i][1] = y;
                break;
            }
        }
    }

    // 开始方块
    public void initStart() {
        // 创建起点
        Entity startAccessory = getGameWorld().create("StartAccessory",new SpawnData());
        // 设置起始点位置，方向
        Point2D startPlace = BottomCtrl.getPlacePoint(info.startX(),info.startY());
        startAccessory.setPosition( startPlace.subtract(startAccessory.getWidth()/2,startAccessory.getHeight()/2) );
        startAccessory.setRotation(info.startAng());
        // 不能传形参，不然无法覆盖
        BottomCtrl.setType(info.startX(), info.startY(),(GameType) startAccessory.getType());
        BottomCtrl.setRotation(info.startX(), info.startY(),startAccessory.getRotation());
        FXGL.getGameWorld().addEntity(startAccessory);
    }
    // 结束方块
    private void initEnd() {
        // 创建终点
        Entity endAccessory = getGameWorld().create("EndAccessory",new SpawnData());
        // 设置终点位置
        Point2D endPlace = BottomCtrl.getPlacePoint(info.endX(), info.endY());
        endAccessory.setPosition( endPlace.subtract(endAccessory.getWidth()/2,endAccessory.getHeight()/2) );
        BottomCtrl.setType( info.endX(), info.endY() ,(GameType) endAccessory.getType());
        FXGL.getGameWorld().addEntity(endAccessory);
    }


    // 按钮
    private void initButton() {
        Entity entity = getGameWorld().create("StartRunCar",new SpawnData());
        entity.setPosition(610,50);
        LocalTimer clickTimer = FXGL.newLocalTimer();
        Duration timeGap = Duration.seconds(1);
        EventHandler<MouseEvent> onClick = e-> {
            if(!clickTimer.elapsed(timeGap))return;
            clickTimer.capture();
            CarCtrl.startRunCar();
        };
        entity.getViewComponent().addOnClickHandler(onClick);
        getGameWorld().addEntity(entity);
    }

    public static ArrayList<Entity> getObstacles() {
        return obstacles;
    }
}
