package org.example.app;

import com.almasb.fxgl.dsl.FXGL;

import org.example.info.LevelInfo;

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
        // BottomCtrl.init();

        initStart();
        initEnd();
        initAccessories();
        // 初始化按钮
        initButton();
        initObstacle();
        initStar();
    }

}
