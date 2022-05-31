package org.example.components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import javafx.geometry.Point2D;
import javafx.scene.input.*;

import org.example.GameType;
import org.example.app.*;

public class MyCloseToPlacePoints extends Component {
    Point2D offset = new Point2D(0,0);
    double maxDistance;

    @Override
    public void onAdded() {
        maxDistance = entity.getHeight()/2;
        entity.getViewComponent().addEventHandler(MouseEvent.MOUSE_RELEASED, e->{
            if(e.getButton() == MouseButton.SECONDARY)return;
            offset = FXGL.getInput().getMousePositionWorld().subtract(entity.getPosition());
            // boolean isCloseTo = false;
            Point2D mousePlace = FXGL.getInput().getMousePositionWorld();

            Point2D rowCol = BottomCtrl.getClosestRowCol(mousePlace);
            int x = (int)rowCol.getX();
            int y = (int)rowCol.getY();
            GameType type = BottomCtrl.getType(x,y);
            if (type!=GameType.NONE){
                if(type == GameType.Obstacle){
                    EntityCtrl.backToCardSlot(entity);
                }
                return;
            }

            Point2D nearest = BottomCtrl.getPlacePoint(x,y);
            if(mousePlace.distance(nearest) < maxDistance){
                BottomCtrl.setPosition(entity, x, y);

                if (entity.getString("lastPos").equals("out")){
                    VarManager.inc(entity.getType(), -1);
                    entity.setProperty("lastPos","in");
                }
                // 在鼠标按下时原来的已经置空，不要操作
                // 设置这次的位置，并设置对应位置的类型
                entity.setProperty("row", x);
                entity.setProperty("col", y);
                int row=entity.getInt("row"),col=entity.getInt("col");
                BottomCtrl.setRotation(row, col, entity.getRotation());
                BottomCtrl.setType(row, col, (GameType) entity.getType());
                BottomCtrl.setRotation(row, col, entity.getRotation());
            }

//            for (int i = 0; i< BottomCtrl.getRows(); i++) {
//                for (int j = 0; j< BottomCtrl.getCols(); j++){
//                    GameType type = BottomCtrl.getType(i, j);
//                    if(type!=GameType.NONE){
//                        if(type == GameType.Obstacle){
//                            EntityCtrl.backToCardSlot(entity);
//                        }
//                        continue;
//                    }
//                    Point2D p2d = BottomCtrl.getPlacePoint(i,j);
//                    if (p2d.distance(mousePlace) < maxDistance){
//                        BottomCtrl.setPosition(entity, i, j);
//
//                        if (entity.getString("lastPos").equals("out")){
//                            VarManager.inc(entity.getType(), -1);
//                            entity.setProperty("lastPos","in");
//                        }
//                        // 在鼠标按下时原来的已经置空，不要操作
//                        // 设置这次的位置，并设置对应位置的类型
//                        entity.setProperty("row", i);
//                        entity.setProperty("col", j);
//                        int row=entity.getInt("row"),col=entity.getInt("col");
//                        BottomCtrl.setRotation(row, col, entity.getRotation());
//                        BottomCtrl.setType(row, col, (GameType) entity.getType());
//                        BottomCtrl.setRotation(row, col, entity.getRotation());
//                        return;
//                    }
//                }
//            }
        });
    }
}
