package org.example;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameScene;

import java.util.Map;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.LoadingScene;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;

import org.example.app.GameLevelCtrl;
import org.example.ui.MainMenu;
import org.example.ui.MyLoadingScene;

import javafx.scene.paint.Color;
import javafx.scene.text.Text;
public class TestMain extends GameApplication{
    
    public static int chosenLevel = 3;
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
//        gameSettings.setMainMenuEnabled(true);
//
//        gameSettings.getSupportedLanguages().add(Language.ENGLISH);
//        gameSettings.setDefaultLanguage(Language.ENGLISH);
//
//        gameSettings.setSceneFactory(new SceneFactory(){

        /**
         * 基本游戏设置
         */
        gameSettings.setTitle("Logic Road Game");
        gameSettings.setVersion("0.1");
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
        GameLevelCtrl gameLevelCtrl = new GameLevelCtrl("data/levels/levelInfo" + chosenLevel + ".json");
        gameLevelCtrl.init();
//        int l=FXGL.geti("level");
//        System.out.println(l);;
//        getip("gameIsOver").addListener((ob, ov, nv));
    }

    /**
     * @param vars
     */
    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("crossNum",0);
        vars.put("hyperbolaNum",0);
        vars.put("arcNum",0);
        vars.put("gameIsOver", 0);
    }

    /**
     *
     */
    @Override
    protected void initUI() {
        //显示组件的剩余数量
        initComNum();

    }

    @Override

    protected void initPhysics() {
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(GameType.Car,GameType.Star) {
            @Override
            protected void onCollisionBegin(Entity car, Entity star) {
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

    //检查关卡是否结束
    private void checkIsEnd() {

    }

    //选择关卡加载数据
    public static void chooseLevel(int level) {
        FXGL.set("level",level);
        System.out.println(FXGL.geti("level"));
    }
}
