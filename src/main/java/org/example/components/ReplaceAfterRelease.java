package org.example.components;

import com.almasb.fxgl.entity.component.Component;

import org.example.app.BottomCtrl;
import org.example.app.EntityCtrl;

import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

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
