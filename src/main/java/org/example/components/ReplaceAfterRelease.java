package org.example.components;

import java.util.ArrayList;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import org.example.app.BottomCtrl;
import org.example.app.EntityCtrl;
import org.example.app.GameLevelCtrl;
import org.example.app.VarManager;

public class ReplaceAfterRelease extends Component {

    EventHandler<MouseEvent> onRelease = e-> {
        if (e.getButton() == MouseButton.SECONDARY)
            return;
        // 超出棋盘就放回原处
        if (entity.isColliding(BottomCtrl.getEntity())){
            return;
        }
        EntityCtrl.backToCardSlot(entity);
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
