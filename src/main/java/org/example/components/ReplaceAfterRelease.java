package org.example.components;

import com.almasb.fxgl.entity.component.Component;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import org.example.app.BottomCtrl;
import org.example.app.GameLevelCtrl;
import org.example.app.VarManager;

public class ReplaceAfterRelease extends Component {

    EventHandler<MouseEvent> onRelease = e-> {
        if (e.getButton() == MouseButton.SECONDARY)
            return;
        if (entity.isColliding(BottomCtrl.getEntity()) && !entity.isColliding(GameLevelCtrl.getObstacleEntity()))
            return;
        // TODO: 2022/5/22 添加运动动画，不然动作太单一
        entity.setPosition((Point2D) entity.getProperties().getObject("originPlace"));
        entity.setRotation(0);

        String lastPos = entity.getString("lastPos");
        if(lastPos.equals("in")){
            VarManager.inc(entity.getType(),1);
        }
        entity.setProperty("lastPos","out");
        entity.setProperty("row", -1);
        entity.setProperty("col", -1);
    };

    /**
     *
     */
    @Override
    public void onAdded() {
        entity.getViewComponent().addEventHandler(MouseEvent.MOUSE_RELEASED, onRelease);
    }

    @Override
    public boolean isComponentInjectionRequired() {
        return false;
    }
}
