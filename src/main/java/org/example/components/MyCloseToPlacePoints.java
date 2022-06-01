package org.example.components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import javafx.geometry.Point2D;
import javafx.scene.input.*;

import org.example.GameType;
import org.example.app.*;

public class MyCloseToPlacePoints extends Component {
    double maxDistance;

    @Override
    public void onAdded() {
        maxDistance = entity.getHeight()/2;
        entity.getViewComponent().addEventHandler(MouseEvent.MOUSE_RELEASED, e->{
            if(e.getButton() == MouseButton.SECONDARY)return;
            // boolean isCloseTo = false;
            Point2D mousePlace = FXGL.getInput().getMousePositionWorld();

            int[] rowCol = BottomCtrl.getClosestRowCol(mousePlace);
            int x = rowCol[0], y = rowCol[1];
            GameType type = BottomCtrl.getType(x,y);
            if (type!=GameType.NONE){
                // 更改逻辑，靠近的地方被占用时返回上次的位置
                EntityCtrl.backToLastPlace(entity, (GameType)entity.getType());
                // if(type == GameType.Obstacle){
                //     // TODO: 起点终点靠近障碍物时会发生异常，原因是其不存在row、col 
                //     EntityCtrl.backToLastPlace(entity, (GameType)entity.getType());
                // }
                return;
            }

            Point2D nearest = BottomCtrl.getPlacePoint(x,y);
            if(mousePlace.distance(nearest) < maxDistance){
                BottomCtrl.setPosition(entity, x, y);
                EntityCtrl.update(entity, x, y);
            }
        });
    }
}
