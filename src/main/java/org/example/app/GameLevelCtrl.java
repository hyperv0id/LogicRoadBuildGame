package org.example.app;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.example.GameType;
import org.example.components.MousePressLosePoint;
import org.example.info.BroadData;
import org.example.info.LevelInfo;

import java.util.ArrayList;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;

public class GameLevelCtrl {
    public static LevelInfo info;
    public static BroadData[][] broadDatas;
    public static Entity bottomAccessory;
    // 所有积木，包括开始结束，方圆弯道
    public static ArrayList<Entity> accessories = new ArrayList<>();

    public GameLevelCtrl(LevelInfo info) {
        this.info = info;
    }
    public GameLevelCtrl(String levelInfo) {
        this.info = FXGL.getAssetLoader().loadJSON(levelInfo,LevelInfo.class).get();
        FXGL.set("crossNum",info.crossNum());
        FXGL.set("hyperbolaNum",info.curveSquareNum());
        FXGL.set("arcNum",info.curveOrbitNum());
        broadDatas = new BroadData[info.bottomRows()][info.bottomCols()];
        for (int i = 0; i < broadDatas.length; i++) {
            for (int j = 0; j < broadDatas[0].length; j++) {
                broadDatas[i][j] = new BroadData();
            }
        }
    }

    public void init(){
        initBottom();
        generatePlacePoints();
        initStart();
        initEnd();
        initAccessories();
        // 初始化按钮
        initButton();
    }

    private void initButton() {

        Entity e = getGameWorld().create("StartRunCar",new SpawnData());
        e.setPosition(610,50);
        e.getViewComponent().addOnClickHandler(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                CarCtrl.startRunCar();
            }
        });
        getGameWorld().addEntity(e);
    }


    public void initAccessories() {
        double w=FXGL.getAppWidth(),h=FXGL.getAppHeight();
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

    private void initEnd() {
        // 创建终点
        Entity endAccessory = getGameWorld().create("EndAccessory",new SpawnData());
        // 设置终点位置
        BroadData endData = broadDatas[info.endX()][info.endY()];
        endAccessory.setPosition( endData.getPlace().subtract(endAccessory.getWidth()/2,endAccessory.getHeight()/2) );
        endData.setEntityType((GameType) endAccessory.getType());
        FXGL.getGameWorld().addEntity(endAccessory);
    }

    public void initBottom() {
        // 创建底板
        bottomAccessory = getGameWorld().create("BasicBottom",new SpawnData());
        // 设置底板位置，margin的左上下大小相同
        double bottomMargin = (FXGL.getAppHeight()-bottomAccessory.getHeight())/2;
        bottomAccessory.setPosition(bottomMargin,bottomMargin);
        FXGL.getGameWorld().addEntity(bottomAccessory);
    }
    public void initStart() {
        // 创建起点
        Entity startAccessory = getGameWorld().create("StartAccessory",new SpawnData());
        // 设置起始点位置，方向
        BroadData startData = broadDatas[info.startX()][info.startY()];
        startAccessory.setPosition( startData.getPlace().subtract(startAccessory.getWidth()/2,startAccessory.getHeight()/2) );
        startAccessory.setRotation(info.startAng());
        // 不能传形参，不然无法覆盖
        broadDatas[info.startX()][info.startY()].setEntityType((GameType) startAccessory.getType());
        broadDatas[info.startX()][info.startY()].setRotation(startAccessory.getRotation());
        FXGL.getGameWorld().addEntity(startAccessory);
    }
    /**
     * 生成放置点
     * @return
     */
    public void generatePlacePoints(){
        int rows = info.bottomRows(),
                cols = info.bottomCols();
        // 得到底板中心点
        Point2D center = new Point2D(bottomAccessory.getWidth()/2, bottomAccessory.getHeight()/2)
                .add(bottomAccessory.getPosition());
        FXGL.entityBuilder().view(new Rectangle(10,10, Color.RED)).at(center).build();
        double width = bottomAccessory.getWidth()/rows,
                height = bottomAccessory.getHeight()/cols;
        width*=0.95;
        height*=0.95;
        // 以中心点为基准，设置物块放置点
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                broadDatas[i][j].setPlace(
                        center.add(new Point2D((j-2)*height+height/2,(i-2)*width+width/2))
                );
            }
        }
    }
}
