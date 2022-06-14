package org.example.components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import javafx.geometry.Point2D;
import javafx.scene.input.*;

import org.example.GameType;
import org.example.app.*;

public class CloseToPlacePoints extends Component {
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
                // 改了逻辑，靠近的地方被占用时返回上次的位置
                EntityCtrl.backToLastPlace(entity, (GameType)entity.getType());
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
