package org.example.components;

import com.almasb.fxgl.entity.component.Component;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.example.app.EntityCtrl;

public class MousePressLosePoint extends Component {
    @Override
    public void onAdded() {
        entity.getViewComponent().addEventHandler(MouseEvent.MOUSE_PRESSED, e->{
            if (e.getButton() == MouseButton.SECONDARY)
                return;
            EntityCtrl.lossPPFocus(entity);
        });
    }
}
