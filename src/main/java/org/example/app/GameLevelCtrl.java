package org.example.app;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.time.LocalTimer;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
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
    protected static ArrayList<Entity> obstacles = new ArrayList<>();
    protected static ArrayList<Entity> stars = new ArrayList<>();

    protected static LevelInfo info;
    private static Entity EndingPoint;
    private static Entity StartingPoint;
    protected GameLevelCtrl(){}


    public GameLevelCtrl(LevelInfo info) {
        GameLevelCtrl.info = info;
    }


    public static LevelInfo getInfo() {
        return info;
    }


    public GameLevelCtrl(String levelInfo) {
        info = FXGL.getAssetLoader().loadJSON(levelInfo,LevelInfo.class).get();
        FXGL.set("crossNum",info.crossNum());
        FXGL.set("hyperbolaNum",info.curveSquareNum());
        FXGL.set("arcNum",info.curveOrbitNum());
        FXGL.set("starNum",info.starNum());
        FXGL.set("obstacleNum",info.obstacleNum());
    }

    public void init(){
        BottomCtrl.init(info);
        initStart();
        initEnd();
        initAccessories();
        // 初始化按钮
        initButton();
        // todo 取消注释
        initObstacle();
        initStar();
        initUI();
    }

    /**
     * 将UI组件放到关卡中，而不是app中
     */
    private void initUI() {
        // 显示组件的剩余数量
        Text crossText = FXGL.getUIFactoryService().newText(FXGL.getip("crossNum").asString("x%d"));
        Text hyperbolaText = FXGL.getUIFactoryService().newText(FXGL.getip("hyperbolaNum").asString("x%d"));
        Text arcText = FXGL.getUIFactoryService().newText(FXGL.getip("arcNum").asString("x%d"));// x:890,y:580
        crossText.setLayoutX(890);
        crossText.setLayoutY(280);
        FXGL.addUINode(crossText);
        hyperbolaText.setLayoutX(890);
        hyperbolaText.setLayoutY(440);
        FXGL.addUINode(hyperbolaText);
        arcText.setLayoutX(890);
        arcText.setLayoutY(580);
        FXGL.addUINode(arcText);
    }
    // 基本可放置方块
    public void initAccessories() {
        // double w=FXGL.getAppWidth();
        double h=FXGL.getAppHeight();
        // 创建多个十字形组件
        for (int i = 0; i < info.crossNum(); i++) {
            Entity cross = getGameWorld().create("Cross",new SpawnData());
            cross.setPosition(740,h/4);
            accessories.add(cross);
        }
        // 创建多个曲面方形组件
        for (int i = 0; i < info.curveSquareNum(); i++) {
            Entity hyperbola = getGameWorld().create("Hyperbola",new SpawnData());
            hyperbola.setPosition(740,h/2);
            accessories.add(hyperbola);
        }
        //创建多个香蕉形组件
        for (int i = 0; i < info.curveOrbitNum(); i++) {
            Entity arc = getGameWorld().create("Arc",new SpawnData());
            arc.setPosition(740,2*h/3);
            accessories.add(arc);
        }

        for (Entity e : accessories) {
            e.setProperty("originPlace", e.getPosition());
            e.setProperty("row", -1);
            e.setProperty("col", -1);
            e.setProperty("lastPos","out");
            e.addComponent(new MousePressLosePoint());
            FXGL.getGameWorld().addEntity(e);
        }
    }

    //随机在棋盘内产生一个障碍物
    protected void initObstacle() {
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
                    obstacle.setProperty("lastPos", "in");
                    BottomCtrl.setType(x, y, (GameType)obstacle.getType());
                    FXGL.getGameWorld().addEntity(obstacle);
                    obstacles.add(obstacle);
                    break;
                }
            }
        }
    }

    /**
     * 随机在棋盘内产生星星
     */
    protected void initStar() {
        Random rand = new Random();
        // int j = 0;
        for (int i = 0; i < info.starNum(); i++) {
            Entity star = getGameWorld().create("Star", new SpawnData());

            while (true) {
                int x = rand.nextInt(4);
                int y = rand.nextInt(4);
                if ((x == 0 && y == 0)
                        ||(x == 3 && y == 3)
                        ||(BottomCtrl.getType(x, y)==GameType.Obstacle)
                        ||(BottomCtrl.haveStar(x,y)))
                    continue;
                BottomCtrl.setPosition(star,x, y);
                BottomCtrl.setStar(x,y, true);
                FXGL.getGameWorld().addEntity(star);
                stars.add(star);
                break;
            }
        }
    }

    // 开始方块
    public void initStart() {
        // 创建起点
        StartingPoint = getGameWorld().create("StartingPoint",new SpawnData());
        // 设置起始点位置，方向
        Point2D startPlace = BottomCtrl.getPlacePoint(info.startX(),info.startY());
        StartingPoint.setPosition( startPlace.subtract(StartingPoint.getWidth()/2,StartingPoint.getHeight()/2) );
        StartingPoint.setRotation(info.startAng());
        // 不能传形参，不然无法覆盖
        BottomCtrl.setType(info.startX(), info.startY(),(GameType) StartingPoint.getType());
        BottomCtrl.setRotation(info.startX(), info.startY(),StartingPoint.getRotation());
        FXGL.getGameWorld().addEntity(StartingPoint);
    }
    // 结束方块
    protected void initEnd() {
        // 创建终点
        EndingPoint = getGameWorld().create("EndingPoint",new SpawnData());
        // 设置终点位置
        Point2D endPlace = BottomCtrl.getPlacePoint(info.endX(), info.endY());
        EndingPoint.setPosition( endPlace.subtract(EndingPoint.getWidth()/2,EndingPoint.getHeight()/2) );
        BottomCtrl.setType( info.endX(), info.endY() ,(GameType) EndingPoint.getType());
        FXGL.getGameWorld().addEntity(EndingPoint);
    }


    // 按钮
    protected void initButton() {
        initStartRunBtn();
        initReplace();
    }

    private void initStartRunBtn() {
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
    private void initReplace(){
        Entity entity1 = getGameWorld().create("ReplaceButton",new SpawnData());
        entity1.setPosition(610,200);
        LocalTimer clickTimer1 = FXGL.newLocalTimer();
        Duration timeGap1 = Duration.seconds(1);
        EventHandler<MouseEvent> onClick1 = e-> {
            if(!clickTimer1.elapsed(timeGap1))return;
            clickTimer1.capture();
            for(Entity entity2:accessories){
                GameType type = (GameType)entity2.getType();
                switch (type) {
                    // TODO 0602-10:49 修改
                    // 已经在原位的不作动画
                    case Arc,Cross,Hyperbola->{
                        if(entity2.getPosition().distance((Point2D)entity2.getProperties().getObject("originPlace")) > 1 ){
                            EntityCtrl.backToCardSlot(entity2);
                        }
                    }
                    default ->{}
                }
            }
        };
        entity1.getViewComponent().addOnClickHandler(onClick1);
        getGameWorld().addEntity(entity1);
    }

    public static ArrayList<Entity> getObstacles() {
        return obstacles;
    }


    public Entity getEndingPoint() {
        return EndingPoint;
    }


    public Entity getStartingPoint() {
        return StartingPoint;
    }
}
