package org.example;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameScene;

import java.util.Map;

import org.example.app.DesignModeCtrl;
import org.example.app.GameLevelCtrl;
import org.example.ui.MainMenu;
import org.example.ui.MyLoadingScene;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.LoadingScene;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;

import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * Main 类，继承自 GameApplication
 */
public class RoadBuilderApp extends GameApplication {
    public static int chosenLevel = 4;

    public static void main(String[] args) {
        launch(args);
    }

    public void initComNum(){
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
    }

    @Override
    protected void initSettings(GameSettings gameSettings) {
        
        //设置游戏主菜单
        // gameSettings.setMainMenuEnabled(true);

        // gameSettings.getSupportedLanguages().add(Language.ENGLISH);
        // gameSettings.setDefaultLanguage(Language.ENGLISH);

        // gameSettings.setSceneFactory(new SceneFactory(){
        /*
         * 基本游戏设置
         */
        gameSettings.setTitle("Logic Road Game");
        gameSettings.setVersion("1.4");
        gameSettings.setWidth(960);
        gameSettings.setHeight(640);
        gameSettings.setAppIcon("ui/icon.png");


        gameSettings.setMainMenuEnabled(true);
        gameSettings.setSceneFactory(new SceneFactory() {
            public LoadingScene newLoadingScene() {
                return new MyLoadingScene();
            }
            public FXGLMenu newMainMenu() {
                return new MainMenu();
            }
        });
    }

    @Override
    protected void initGame() {
        // 背景颜色
        getGameScene().setBackgroundColor(Color.web("#bc646c"));
        // 配件工厂
        FXGL.getGameWorld().addEntityFactory(new AccessoryFactory());
        FXGL.spawn("box");


        // TODO: 2022/5/31 在添加UI后一个根据选择的游戏模式的不同执行不同函数
        if (chosenLevel == 1 || chosenLevel == 2 || chosenLevel == 3) {
            GameLevelCtrl gameLevelCtrl = new GameLevelCtrl("data/levels/levelInfo" + chosenLevel + ".json");
            gameLevelCtrl.init();
        } else if (chosenLevel == 4) {
            DesignModeCtrl designModeCtrl = new DesignModeCtrl("data/levels/DesignInfo.json");
            designModeCtrl.init();
        }
        else {
            return;
        }
        // for (Entity entity : FXGL.getGameWorld().getEntities()) {
        //     entity.getViewComponent().addOnClickHandler(e->{
        //         BottomCtrl.printType();
        //     });
        // }

    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("crossNum",0);
        vars.put("hyperbolaNum",0);
        vars.put("arcNum",0);
        vars.put("stepCount",0);
        vars.put("points",0);
        vars.put("starCount",0);
    }

    @Override
    protected void initUI() {
        //显示组件的剩余数量
        initComNum();
    }

    @Override

    protected void initPhysics() {
        FXGL.getPhysicsWorld().addCollisionHandler(
                new CollisionHandler(GameType.Car, GameType.Star) {
                    @Override
                    protected void onCollisionBegin(Entity car, Entity star) {
                        FXGL.play("star.wav");
                        FXGL.inc("starCount",1);
                        star.removeFromWorld();
                    }
                });
    }

    @Override
    protected void onPreInit() {
        // 预加载资源，如设置游戏初始化音量
        FXGL.getSettings().setGlobalMusicVolume(10);
        FXGL.getSettings().setGlobalSoundVolume(10);

        FXGL.loopBGM("Sound Studio B - あなたのためにさりげなく.mp3");
    }

}