package org.example;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameScene;

import java.util.Map;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;

import org.example.app.DesignModeCtrl;
import org.example.app.GameLevelCtrl;

import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * Main 类，继承自 GameApplication
 */
public class GameApp extends GameApplication {
    public static void main(String[] args) {
        launch(args);
    }
    // 随便改的一点代码
    @Override
    protected void initSettings(GameSettings gameSettings) {
        /*
         * 基本游戏设置
         */
        gameSettings.setTitle("Logic Road Game");
        gameSettings.setVersion("0.1");
        gameSettings.setWidth(960);
        gameSettings.setHeight(640);
        gameSettings.setAppIcon("ui/icon.png");
    }

    @Override
    protected void initGame() {
        // 背景颜色
        getGameScene().setBackgroundColor(Color.web("#bc646c"));
        // 配件工厂
        FXGL.getGameWorld().addEntityFactory(new AccessoryFactory());
        FXGL.spawn("box");
        // TODO: 2022/5/31 再添加UI后一个根据选择的游戏模式的不同执行不同函数
       new GameLevelCtrl("data/levelInfo.json").init();
        // new DesignModeCtrl("data/DesignInfo.json").init();
    }


    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("crossNum",0);
        vars.put("hyperbolaNum",0);
        vars.put("arcNum",0);
    }


    @Override
    protected void initUI() {
        //显示组件的剩余数量
        Text crossText = FXGL.getUIFactoryService().newText(FXGL.getip("crossNum").asString("x%d"));
        Text hyperbolaText = FXGL.getUIFactoryService().newText(FXGL.getip("hyperbolaNum").asString("x%d"));
        Text arcText = FXGL.getUIFactoryService().newText(FXGL.getip("arcNum").asString("x%d"));//x:890,y:580
        crossText.setLayoutX(890);
        crossText.setLayoutY(280);
        FXGL.addUINode(crossText);
        hyperbolaText.setLayoutX(890);
        hyperbolaText.setLayoutY(440);
        FXGL.addUINode(hyperbolaText);
        arcText.setLayoutX(890);
        arcText.setLayoutY(580);
        FXGL.addUINode(arcText);
//       FXGL.addUINode(crossText, 700,700);
//       FXGL.addUINode(hyperbolaText, 700,720);
//       FXGL.addUINode(arcText, 700,740);
    }

    @Override

    protected void initPhysics() {
        FXGL.getPhysicsWorld().addCollisionHandler(
                new CollisionHandler(GameType.Car,GameType.Star) {
                    @Override
                    protected void onCollisionBegin(Entity car, Entity star) {
                        System.out.println("collision to star");
                        star.removeFromWorld();
                    }
                });
    }

    @Override
    protected void onPreInit() {
        // 预加载资源，如设置游戏初始化音量
        FXGL.getSettings().setGlobalMusicVolume(0.1);
        FXGL.getSettings().setGlobalSoundVolume(0.1);

//        FXGL.loopBGM("Never Give Up - StayLoose [Arknights Soundtrack] Music Video (320 kbps).mp3");
    }
}