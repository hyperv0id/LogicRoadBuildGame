package org.example.components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.example.GameType;
import org.example.app.GameLevelCtrl;
import org.example.app.VarManager;
import org.example.info.BroadData;

public class MyCloseToPlacePoints extends Component {
    Point2D offset = new Point2D(0,0);
    double maxDistance;

    @Override
    public void onAdded() {
        maxDistance = entity.getHeight()/3;
        entity.getViewComponent().addEventHandler(MouseEvent.MOUSE_RELEASED, e->{
            if(e.getButton() == MouseButton.SECONDARY)return;
            offset = FXGL.getInput().getMousePositionWorld().subtract(entity.getPosition());
            boolean isCloseTo = false;
            Point2D mousePlace = FXGL.getInput().getMousePositionWorld();

            BroadData[][] data = GameLevelCtrl.broadDatas;
            for (int i = 0; i< data.length; i++) {
                for (int j = 0; j< data[0].length; j++){
                    Point2D p2d = data[i][j].getPlace();

                    if (p2d.distance(mousePlace) < maxDistance){
                        entity.setPosition(
                                p2d.subtract(
                                        new Point2D(entity.getWidth()/2,entity.getHeight()/2)));
                        isCloseTo = true;
                        if (entity.getString("lastPos").equals("out")){
                            VarManager.inc(entity.getType(), -1);
                            entity.setProperty("lastPos","in");
                        }
                        // 在鼠标按下时原来的已经置空，不要操作
                        // 设置这次的位置，并将对应位置的类型设为
                        entity.setProperty("row", i);
                        entity.setProperty("col", j);
                        int row=entity.getInt("row"),col=entity.getInt("col");
                        GameLevelCtrl.broadDatas[row][col].setRotation(entity.getRotation());
                        GameLevelCtrl.broadDatas[row][col].setEntityType((GameType) entity.getType());
                        GameLevelCtrl.broadDatas[row][col].setRotation(entity.getRotation());
                    }
                    if (isCloseTo){
                        break;
                    }
                }
            }
        });
    }
}
